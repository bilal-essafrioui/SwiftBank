package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Account;
import util.DBConnection;

public class AccountDAO {
	
	private Connection conn;
	
	public AccountDAO() throws SQLException  {
        this.conn = DBConnection.getConnection();
    }
	
	// getting account by accountNumber 
	public Account getByAccountNumber(String accountNumber) throws SQLException {
        String query = "SELECT * FROM account WHERE num_account = ? AND type = 'CHECKING'";
        
        try ( PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, accountNumber);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                	Account account = new Account(
                			rs.getString("num_account"),
                		    rs.getString("rib"),
                		    rs.getBigDecimal("balance"),                	  
                		    rs.getTimestamp("created_at").toLocalDateTime(),  
                		    rs.getBoolean("is_deleted"),
                		    rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null,
                		    rs.getString("type"),
                		    rs.getInt("id_client")
                	);
                    
                    account.setIdAccount(rs.getInt("id_account"));
                    
                    return account;
                } else {
                    return null;
                }
            }
        }
    }

	
	// getting account by Rib
	public Account getByRib(String accountRib) throws SQLException {
        String query = "SELECT * FROM account WHERE rib = ? AND type = 'CHECKING'";
        
        try ( PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, accountRib);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                	Account account = new Account(
                			rs.getString("num_account"),
                		    rs.getString("rib"),
                		    rs.getBigDecimal("balance"),                
                		    rs.getTimestamp("created_at").toLocalDateTime(),  
                		    rs.getBoolean("is_deleted"),
                		    rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null,
                		    rs.getString("type"),
                		    rs.getInt("id_client")
                	);
                    
                    account.setIdAccount(rs.getInt("id_account"));
                    
                    return account;
                } else {
                    return null;
                }
            }
        }
    }
	
	public Account getCheckingAccountByClientId(int clientId) throws SQLException {
	    String query = "SELECT * FROM account WHERE id_client = ? AND type = 'CHECKING' AND is_deleted = FALSE";

	    try (PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setInt(1, clientId);

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                Account account = new Account(
	                    rs.getString("num_account"),
	                    rs.getString("rib"),
	                    rs.getBigDecimal("balance"),
	                    rs.getTimestamp("created_at").toLocalDateTime(),
	                    rs.getBoolean("is_deleted"),
	                    rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null,
	                    rs.getString("type"),
	                    rs.getInt("id_client")
	                );

	                account.setIdAccount(rs.getInt("id_account"));
	                return account;
	            } else {
	                return null;
	            }
	        }
	    }
	}

	
	// 
	public int save(String accountNumber, String rib, BigDecimal balance, String type, int clientId) throws SQLException {
	    String query = "INSERT INTO ACCOUNT (num_account, rib, balance, type, id_client) VALUES (?, ?, ?, ?, ?)";

	    try ( PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
	        
	        stmt.setString(1, accountNumber);
	        stmt.setString(2, rib);
	        stmt.setBigDecimal(3, balance);
	        stmt.setString(4, type);
	        stmt.setInt(5, clientId);

	        stmt.executeUpdate();

	        try (ResultSet rs = stmt.getGeneratedKeys()) {
	            if (rs.next()) {
	                return rs.getInt(1);
	            } else {
	                throw new SQLException("Creating account failed.");
	            }
	        }
	    }
	}
}
