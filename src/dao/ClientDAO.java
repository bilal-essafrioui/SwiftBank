package dao;

import model.Client;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDAO {
	private String query; 
	
	public int save(Client client) throws SQLException {
		
		this.query = "INSERT INTO client (firstname, lastname, birthdate, cin, email, phone_number, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		// try-with-resources ensures automatic closure of Connection and PreparedStatement
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
			
				stmt.setString(1, client.getFirstName());
				stmt.setString(2, client.getLastName());
				stmt.setObject(3, client.getBirthDate());
				stmt.setString(4, client.getCin());
				stmt.setString(5, client.getEmail());
				stmt.setString(6, client.getPhoneNumber());
				stmt.setString(7, client.getPassword());
				
				stmt.executeUpdate();
				
				try (ResultSet rs = stmt.getGeneratedKeys()) {
		            if (rs.next()) {
		                return rs.getInt(1); // return generated client ID
		            } else {
		                throw new SQLException("Creating client failed, no ID obtained.");
		            }
		        }
				
		} catch (SQLException e ) {
			throw new RuntimeException("Error while saving client: ", e);
		}
	}
	
	// Getting client by id 
	 public Client getById(int clientId) throws SQLException {
	        String query = "SELECT * FROM client WHERE id_client = ?";
	        
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(query)) {
	
	            stmt.setInt(1, clientId);
	            
	            try (ResultSet rs = stmt.executeQuery()) {
	  
	                if (rs.next()) {
	                    Client client = new Client(
	                		rs.getString("firstname"), 
							rs.getString("lastname"), 
							rs.getDate("birthdate").toLocalDate(), 
							rs.getString("cin"), 
							rs.getString("email"),
							rs.getString("phone_number"), 
							rs.getString("password")
	                    );
	                    
	                    client.setId(rs.getInt("id_client"));
	                    
	                    return client;
	                } else {
	                    return null;
	                }
	            }
	        }
	    }
	
	
	
	 
			
}
