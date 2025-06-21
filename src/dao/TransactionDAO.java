package dao;

import util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
	
	private String query;

	// Deduct from sender using existing connection
	public boolean deductSenderBalance(Connection conn, int idAccount, BigDecimal amount) throws SQLException {
	    query = "UPDATE ACCOUNT SET balance = balance - ? WHERE id_account = ? AND balance >= ?";
	    try (PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setBigDecimal(1, amount);
	        stmt.setInt(2, idAccount);
	        stmt.setBigDecimal(3, amount);
	        return stmt.executeUpdate() > 0;
	    }
	}

	// Credit recipient using existing connection
	public boolean creditRecipientBalance(Connection conn, int idRecipient, BigDecimal amount) throws SQLException {
	    query = "UPDATE ACCOUNT SET balance = balance + ? WHERE id_account = ?";
	    try (PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setBigDecimal(1, amount);
	        stmt.setInt(2, idRecipient);
	        return stmt.executeUpdate() > 0;
	    }
	}

	// Perform transfer with full transaction control
	public void performTransfer(int idAccount, int idRecipient, BigDecimal amount, String description) throws SQLException {
	    String insertQuery  = "INSERT INTO `TRANSACTION` (type, amount, description, id_account, id_recipient, status) VALUES (?, ?, ?, ?, ?, ?)";

	    Connection conn = null;

	    try {
	        conn = DBConnection.getConnection();
	        conn.setAutoCommit(false);

	        if (!deductSenderBalance(conn, idAccount, amount)) {
	            conn.rollback();
	            throw new SQLException("Insufficient funds or deduction failed.");
	        }

	        if (!creditRecipientBalance(conn, idRecipient, amount)) {
	            conn.rollback();
	            throw new SQLException("Credit to recipient failed.");
	        }

	        try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
	            stmt.setString(1, "TRANSFER");
	            stmt.setBigDecimal(2, amount);
	            stmt.setString(3, description);
	            stmt.setInt(4, idAccount);
	            stmt.setInt(5, idRecipient);
	            stmt.setString(6, "COMPLETED");
	            stmt.executeUpdate();
	        }

	        conn.commit();

	    } catch (SQLException e) {
	        if (conn != null) conn.rollback();
	        throw new SQLException("Error during transfer operation", e);
	    } finally {
	        if (conn != null) conn.close();
	    }
	}
	
	// 
	public void performDeposit(int accountId, BigDecimal amount, String description) throws SQLException {
	    String query = "INSERT INTO `TRANSACTION` (type, amount, description, id_account, id_recipient, status) VALUES (?, ?, ?, ?, ?, ?)";

	    Connection conn = null;

	    try {
	        conn = DBConnection.getConnection();
	        conn.setAutoCommit(false);

	        // Mise à jour du solde du compte
	        if (!creditRecipientBalance(conn, accountId, amount)) {
	            conn.rollback();
	            throw new SQLException("Credit to account failed.");
	        }

	        // Insertion de la transaction dans la table TRANSACTION
	        try (PreparedStatement stmt = conn.prepareStatement(query)) {
	            stmt.setString(1, "DEPOSIT");
	            stmt.setBigDecimal(2, amount);
	            stmt.setString(3, description);
	            stmt.setInt(4, accountId);
	            stmt.setNull(5, java.sql.Types.INTEGER);  // id_recipient = NULL
	            stmt.setString(6, "COMPLETED");
	            stmt.executeUpdate();
	        }

	        conn.commit();

	    } catch (SQLException e) {
	        if (conn != null) conn.rollback();
	        throw new SQLException("Error during deposit operation", e);
	    } finally {
	        if (conn != null) conn.close();
	    }
	}
	
	// Get full transaction history by account ID
	public List<String[]> getTransactionHistoryByAccount(int idAccount) throws SQLException {
		query = """
			    SELECT
				    t.transaction_date,
				    t.type,
				    t.amount,
				    t.status,
				    t.description,
				    CONCAT(c.firstname, ' ', c.lastname) AS full_name,
				    a.rib,
				    t.id_account,
					t.id_recipient
				FROM TRANSACTION t
				LEFT JOIN ACCOUNT a ON 
				    (t.type = 'TRANSFER' AND (
				        (t.id_account = ? AND t.id_recipient = a.id_account) OR
				        (t.id_recipient = ? AND t.id_account = a.id_account)
				    ))
				LEFT JOIN CLIENT c ON a.id_client = c.id_client
				WHERE t.id_account = ? OR t.id_recipient = ?
				ORDER BY t.transaction_date DESC
			""";


	    List<String[]> history = new ArrayList<>();

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	    	stmt.setInt(1, idAccount);
	    	stmt.setInt(2, idAccount);
	    	stmt.setInt(3, idAccount);
	    	stmt.setInt(4, idAccount);

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                String[] row = new String[9];
	                row[0] = rs.getString("transaction_date");
	                row[1] = rs.getString("type");
	                row[2] = rs.getString("amount");
	                row[3] = rs.getString("status");
	                row[4] = rs.getString("description");
	                row[5] = rs.getString("full_name");
	                row[6] = rs.getString("rib");
	                row[7] = String.valueOf(rs.getInt("id_account"));
	                row[8] = String.valueOf(rs.getInt("id_recipient"));
	                history.add(row);
	            }
	        }
	    }

	    return history;
	}
	
	public List<String[]> getTransactionHistoryByAccountAndType(int idAccount, String type) throws SQLException {
	    String query = """
	        SELECT
	            t.transaction_date,
	            t.type,
	            t.amount,
	            t.status,
	            t.description,
	            CONCAT(c.firstname, ' ', c.lastname) AS full_name,
	            a.rib,
	            t.id_account,
				t.id_recipient
	        FROM TRANSACTION t
	        INNER JOIN ACCOUNT a ON 
	            (t.type = 'TRANSFER' AND (
	                (t.id_account = ? AND t.id_recipient = a.id_account) OR
	                (t.id_recipient = ? AND t.id_account = a.id_account)
	            ))
	            OR (t.type != 'TRANSFER' AND t.id_account = a.id_account)
	        INNER JOIN CLIENT c ON a.id_client = c.id_client
	        WHERE (t.id_account = ? OR t.id_recipient = ?)
	        AND (t.type = ?)
	        ORDER BY t.transaction_date DESC
	    """;

	    List<String[]> history = new ArrayList<>();

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setInt(1, idAccount); // join condition for TRANSFER
	        stmt.setInt(2, idAccount); // join condition for TRANSFER
	        stmt.setInt(3, idAccount); // WHERE t.id_account
	        stmt.setInt(4, idAccount); // WHERE t.id_recipient
	        stmt.setString(5, type);   // type filter

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                String[] row = new String[9];
	                row[0] = rs.getString("transaction_date");
	                row[1] = rs.getString("type");
	                row[2] = rs.getString("amount");
	                row[3] = rs.getString("status");
	                row[4] = rs.getString("description");
	                row[5] = rs.getString("full_name");
	                row[6] = rs.getString("rib");
	                row[7] = String.valueOf(rs.getInt("id_account"));
	                row[8] = String.valueOf(rs.getInt("id_recipient"));
	                history.add(row);
	            }
	        }
	    }

	    return history;
	}

}
