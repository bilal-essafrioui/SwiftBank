package controller;

import model.Client;

import service.ClientService;
import service.AccountService;

import view.LoginView;
import view.RegisterView;

import util.BankIdentifierGenerator;
import dto.ClientSession;
import exception.AuthenticationException;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;

public class ClientController {

    private RegisterView registerView;
    private LoginView loginView;
    private ClientService clientService;
    private AccountService accountService;

    public ClientController() throws SQLException  {
        this.clientService = new ClientService();
        this.accountService = new AccountService();
    }

    public void startRegisterFlow() {
        registerView = new RegisterView();
        registerView.getRegisterButton().addActionListener(e -> handleRegister());
        registerView.getLoginRedirectButton().addActionListener(e -> {
        	registerView.dispose();
        	startLoginFlow();
        });
    }

    public void startLoginFlow() {
        this.loginView = new LoginView();
        loginView.getLoginButton().addActionListener(e -> handleLogin());
        loginView.getRegisterButton().addActionListener(e -> {
            loginView.dispose();
            startRegisterFlow();
        });
    }

    private void handleRegister() {
    	
        String firstName = registerView.getFirstName();
        String lastName = registerView.getLastName();
        String birthDate = registerView.getBirthDate();
        String cin = registerView.getCIN();
        String email = registerView.getEmail();
        String phone = registerView.getPhone();
        String password = registerView.getPassword();
        String initialBalance = registerView.getInitialBalance();
        Client client = new Client(firstName, lastName, null, cin, email, phone, password);

        try {
            int clientId = clientService.saveClient(client, birthDate);
            String accountNumber = BankIdentifierGenerator.generateAccountNumber();
            String rib = BankIdentifierGenerator.generateRIB();
            accountService.saveAccount(accountNumber, rib, initialBalance, clientId);

            registerView.showMessage(
            	    "Your registration has been completed successfully.\nYour new account number is: " + accountNumber,
            	    "Registration Confirmation",
            	    JOptionPane.INFORMATION_MESSAGE
            	);


            registerView.dispose();
            startLoginFlow();

	        } catch (DateTimeParseException e) {
	            registerView.showMessage("Invalid birth date format. Use yyyy-mm-dd.", "Error", JOptionPane.ERROR_MESSAGE);
	        } catch (IllegalArgumentException e) {
	            registerView.showMessage("Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            registerView.showMessage("Internal Error while saving client!", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }

    
    private void handleLogin() {
    	// Getting credentials
        String accountNumber = loginView.getAccountNumber();
        String password = loginView.getPassword();

        try {
        	// Authenticate the client and store client/account info in a session object
            ClientSession session = clientService.authenticate(accountNumber, password);

            loginView.dispose();
            // Redirect the authenticated client to their DASHBOARD.
            new DashboardController(session.getClient(), session.getAccount(), "CHECKING");

        } catch (AuthenticationException e) {
        	loginView.showMessage(
        		    e.getMessage(),
        		    "Login Failed",
        		    JOptionPane.ERROR_MESSAGE
        	);
        } catch (SQLException e) {
        	loginView.showMessage(
        		    "A system error occurred. Please try again later.",
        		    "Error",
        		    JOptionPane.ERROR_MESSAGE
        	);

        }
    }
}
