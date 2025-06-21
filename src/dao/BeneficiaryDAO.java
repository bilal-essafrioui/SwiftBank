package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBConnection;

public class BeneficiaryDAO {
	
	private Connection conn;
	
	public BeneficiaryDAO() throws SQLException {
		this.conn = DBConnection.getConnection();
	}
	
	// Saving a Beneficiary
	public boolean save(int accountId, int beneficiaryId) throws SQLException {
	    String query = "INSERT INTO BENEFICIARY (id_account, id_beneficiary) VALUES (?, ?)";

	    try (PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setInt(1, accountId);
	        stmt.setInt(2, beneficiaryId);

	        return stmt.executeUpdate() > 0;

	    }
	}
	
	// Get Beneficiaries
	public List<String[]> getBeneficiariesByAccountId(int accountId) throws SQLException {
	    /*String query = """
	        SELECT DISTINCT a.id_account AS id_beneficiary, c.firstname, c.lastname, a.rib
			FROM BENEFICIARY b
			JOIN ACCOUNT a ON a.id_account = b.id_beneficiary
			JOIN CLIENT c ON a.id_client = c.id_client
			WHERE b.id_account = ? OR b.id_beneficiary = ?

	    """;*/
		String query = """
			    SELECT DISTINCT
			        CASE 
			            WHEN b.id_account = ? THEN b.id_beneficiary
			            ELSE b.id_account
			        END AS id_beneficiary,
			        c.firstname,
			        c.lastname,
			        a.rib
			    FROM BENEFICIARY b
			    JOIN ACCOUNT a ON a.id_account = CASE 
			                                       WHEN b.id_account = ? THEN b.id_beneficiary
			                                       ELSE b.id_account
				                                 END
			    JOIN CLIENT c ON a.id_client = c.id_client
			    WHERE b.id_account = ? OR b.id_beneficiary = ?
			""";

	    List<String[]> beneficiaries = new ArrayList<>();

	    try (PreparedStatement stmt = conn.prepareStatement(query)) {
	    	stmt.setInt(1, accountId);
	        stmt.setInt(2, accountId);
	        stmt.setInt(3, accountId);
	        stmt.setInt(4, accountId);
	        var rs = stmt.executeQuery();

	        while (rs.next()) {
	            String[] data = {
	            	String.valueOf(rs.getInt("id_beneficiary")),
	                rs.getString("firstname"),
	                rs.getString("lastname"),
	                rs.getString("rib")
	            };
	            beneficiaries.add(data);
	        }

	    }

	    return beneficiaries;
	}
}
