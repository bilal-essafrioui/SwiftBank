package controller;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Account;
import model.Client;
import service.SavingsAccountService;
import view.CreateSavingsAccountView;
import view.DashboardView;

public class SavingsAccountController {
	 private Client client;
	 private Account account;
	 private SavingsAccountService savingsAccountService;
	 private DashboardView dashboardView;
	 
	 public SavingsAccountController(Client client, Account account, DashboardView dashboardView) throws SQLException {
		 this.client = client;
	     this.account = account;
	     this.dashboardView = dashboardView;
	     savingsAccountService = new SavingsAccountService();
	     submitSwitchSavingsAccount();
	 }
	 
	 private void submitSwitchSavingsAccount() {
		    try {
		        Account savingsAccount = savingsAccountService.getSavingsAccount(account.getIdAccount());
		        if (savingsAccount == null) {
		            int option = dashboardView.showConfirmDialog(
		                "You do not have a savings account. Would you like to create one ?",
		                "No Savings Account"
		            );

		            if (option == JOptionPane.YES_OPTION) {
		                CreateSavingsAccountView createSavingsAccountView = new CreateSavingsAccountView();
		                
		                // if the create button has been clicked 
		                createSavingsAccountView.getCreateButton().addActionListener(e -> {
		                    String balanceStr = createSavingsAccountView.getBalanceInput();
		                    String description = createSavingsAccountView.getDescriptionInput();
		                    try {
		                        savingsAccountService.createSavingsAccount(
		                            account.getIdAccount(),
		                            account.getBalance(),
		                            balanceStr,
		                            description
		                        );
		                        createSavingsAccountView.dispose();
		                        dashboardView.dispose();
		                        
		                        // Fetch the newly created savings account from the DB
		                        Account newlyCreatedSavings = savingsAccountService.getSavingsAccount(account.getIdAccount());
		                        
		                        // Redirect the client to the controller that loads the Savings Account DASHBOARD
		                        new DashboardController(client, newlyCreatedSavings, "SAVINGS");
		          
		                    } catch (IllegalArgumentException ex) {
		                        createSavingsAccountView.showMessage("Input error: " + ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
		                    } catch (SQLException ex) {
		                        createSavingsAccountView.showMessage("Error creating savings account: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
		                    }
		                });
		                
		                // if the cancel button has been clicked 
		                createSavingsAccountView.getCancelButton().addActionListener(e -> {
		                	createSavingsAccountView.dispose();
		                });
		            }
		        } else {
		        	// Redirect the client to the controller that loads the Savings Account DASHBOARD
		        	dashboardView.dispose();
		        	new DashboardController(client, savingsAccount, "SAVINGS");
		        }
		    } catch (SQLException e) {
		        dashboardView.showMessage("Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		    }
		}
}
