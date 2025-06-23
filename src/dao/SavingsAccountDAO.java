package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Account;
import model.SavingsAccount;
import util.DBConnection;

public class SavingsAccountDAO {

    private final Connection connection;

    public SavingsAccountDAO() throws SQLException {
        this.connection = DBConnection.getConnection();
    }

    public SavingsAccount getSavingsAccountById(int idAccount) throws SQLException {
        String query = "SELECT id_saving_account, id_account FROM SAVING_ACCOUNT WHERE id_account = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idAccount);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idSavingAccount = rs.getInt("id_saving_account");
                    return new SavingsAccount(idSavingAccount, idAccount);
                } else {
                    return null; 
                }
            }
        }
    }
    
    public Account getSavingsAccountDataById(int idSavingAccount) throws SQLException {
        String query = "SELECT id_account, num_account, rib, balance, created_at, is_deleted, deleted_at, type, id_client " +
                       "FROM ACCOUNT WHERE id_account = ? AND type = 'SAVINGS'";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idSavingAccount);
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
    
    public void save(int idAccount, int idSavingsAccount) throws SQLException {
        
        // Insert into SAVING_ACCOUNT table
        String query = "INSERT INTO SAVING_ACCOUNT (id_saving_account, id_account) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idSavingsAccount);
            stmt.setInt(2, idAccount);
            stmt.executeUpdate();
        }
    }
}

