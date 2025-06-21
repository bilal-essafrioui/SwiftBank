package service;

import model.Client;
import model.Account;
import util.PasswordHasher;
import util.BirthDateValidator;
import dao.ClientDAO;
import dao.AccountDAO;
import dto.ClientSession;
import java.sql.SQLException;
import exception.AuthenticationException;
import java.time.LocalDate;

public class ClientService {
	
	private final ClientDAO clientDAO;
	private final AccountDAO accountDAO;
	
	public ClientService() throws SQLException {
        this.clientDAO = new ClientDAO();
        this.accountDAO = new AccountDAO();
    }
	
	
	// saving client
	public int saveClient(Client client, String birthDateStr) throws SQLException {
		if (client == null || client.getFirstName().isEmpty() || client.getLastName().isEmpty() || 
				client.getCin().isEmpty() || client.getEmail().isEmpty() || 
				client.getPhoneNumber().isEmpty() || client.getPassword().isEmpty()) 
			throw new IllegalArgumentException("Please fill all fields.");
		
		// Validate and set birth date
        LocalDate birthDate = BirthDateValidator.validate(birthDateStr);
        client.setBirthDate(birthDate);
		
		// Hashing the password
		String hashedPassword = PasswordHasher.hashPassword(client.getPassword()); 
		
		// Set the hashed password back to the client object
		client.setPassword(hashedPassword);
		
		// 
		return clientDAO.save(client);
		
	}
	
	// checking if the client is authenticated 
	public ClientSession authenticate(String accountNumber, String password) throws AuthenticationException, SQLException {
        
		if (accountNumber == null || accountNumber.isEmpty() || password == null || password.isEmpty()) {
            throw new AuthenticationException("Please provide both account number and password.");
        }
        
        Account account = accountDAO.getByAccountNumber(accountNumber);
        if (account == null) {
        	throw new AuthenticationException("The specified account does not exist.");
        }
        
        Client client = clientDAO.getById(account.getIdClient());
        if (client == null) {
            throw new AuthenticationException("No client is associated with this account.");
        }

        if (!PasswordHasher.verifyPassword(password, client.getPassword())) {
            throw new AuthenticationException("Incorrect password.");
        }

        return new ClientSession(client, account); 
    }
	
	
}
