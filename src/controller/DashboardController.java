package controller;

import model.Client;
import service.AccountService;
import model.Account;
import view.DashboardView;
import view.AddBeneficiaryView;

import javax.swing.*;

import dao.SavingsAccountDAO;

import java.sql.SQLException;

public class DashboardController {

    private DashboardView dashboardView;
    private Client client;
    private Account account;
    
    public DashboardController(Client client, Account account, String accountType) {
        this.client = client;
        this.account = account;
        dashboardView = new DashboardView(client, account, accountType);
        initController();
    }

    // EventListeners on DASHBOARD buttons
    private void initController() {
        dashboardView.getAddBeneficiaryBtn().addActionListener(e -> openAddBeneficiaryView());
        dashboardView.getDepositBtn().addActionListener(e -> openDepositView());
        dashboardView.getTransferBtn().addActionListener(e -> openTransferView());
        dashboardView.getTransactionHistoryBtn().addActionListener(e -> openTransactionHistoryView());
        dashboardView.getCreateSavingsAccountBtn().addActionListener(e -> openCreateSavingsAccountView());
        dashboardView.getTransferToCheckingAccountBtn().addActionListener(e -> transferToCheckingAccount());
        dashboardView.getSwitchToCheckingAccountBtn().addActionListener(e -> switchToCheckingAccount());
        dashboardView.getLogoutBtn().addActionListener(e -> logout());
    }

    private void openAddBeneficiaryView() {
        try {
            new BeneficiaryController(client, account);
        } catch (SQLException e) {
            dashboardView.showMessage(
                "Error opening Add Beneficiary view:\n",
                "Internal Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void openDepositView() {
    	try {
    		 new TransactionController(client, account, "deposit");
        } catch (SQLException e) {
            dashboardView.showMessage(
                "Error opening Deposit view:\n",
                "Internal Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void openTransferView() {
    	try {
    		new TransactionController(client, account, "transfer");
       } catch (SQLException e) {
           dashboardView.showMessage(
               "Error opening Transfer view:\n",
               "Internal Error",
               JOptionPane.ERROR_MESSAGE
           );
       }
    }

    private void openTransactionHistoryView(){
    	try {
    		new TransactionController(client, account, "history");
       } catch (SQLException e) {
           dashboardView.showMessage(
               "Error opening History view:\n",
               "Internal Error",
               JOptionPane.ERROR_MESSAGE
           );
       }
        
    }

    private void openCreateSavingsAccountView() {
        try {
        	new SavingsAccountController(client, account, dashboardView);
        } catch (SQLException e) {
            dashboardView.showMessage(
                "Error opening Savings Account view:\n",
                "Internal Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void transferToCheckingAccount() {
    	try {
    		SavingsAccountDAO savingsAccountDAO = new SavingsAccountDAO();
    		Account savingsAccount = savingsAccountDAO.getSavingsAccountDataById(account.getIdAccount());
    		new TransactionController(client, savingsAccount, "transfer");
       } catch (SQLException e) {
           dashboardView.showMessage(
               "Error transferring to Checking Account:\n",
               "Internal Error",
               JOptionPane.ERROR_MESSAGE
           );
       }
    }
    
    private void switchToCheckingAccount() {
        try {
            AccountService accountService = new AccountService();
            Account checkingAccount = accountService.getCheckingAccountByClientId(client.getId());

            if (checkingAccount != null) {
                dashboardView.dispose(); // Close current DASHBOARD
                new DashboardController(client, checkingAccount, "CHECKING"); // Reopen with CHECKING account
            } else {
                dashboardView.showMessage("No checking account found for this client.", "Account Not Found", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error switching to Checking Account:\n" + e.getMessage(),
                "Internal Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }



    private void logout() {
        dashboardView.dispose();
        ClientController controller;
		try {
			controller = new ClientController();
			controller.startLoginFlow();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
	                "Error opening login view",
	                "Internal Error",
	                JOptionPane.ERROR_MESSAGE
	            );
		}
        
    }
}
