package controller;

import model.Account;
import model.Client;
import service.AccountService;
import service.BeneficiaryService;
import service.TransactionService;
import view.transactionViews.DepositView;
import view.transactionViews.TransferView;
import view.transactionViews.TransactionHistoryView;

import javax.swing.*;

import java.sql.SQLException;
import java.util.List;

public class TransactionController {

    private Client client;
    private Account account;
    private TransferView transferView;
    private DepositView depositView;
    private TransactionHistoryView transactionHistoryView;
    private TransactionService transactionService;

    public TransactionController(Client client, Account account, String type) throws SQLException {
        this.client = client;
        this.account = account;
        this.transactionService = new TransactionService();

        if ("deposit".equalsIgnoreCase(type)) {
            depositView = new DepositView();
            initDepositController();
            depositView.setVisible(true);
        } else if ("history".equalsIgnoreCase(type)) {
            transactionHistoryView = new TransactionHistoryView();
            initHistoryController();
            transactionHistoryView.setVisible(true);
        } else {
            BeneficiaryService beneficiaryService = new BeneficiaryService();
            List<String[]> beneficiaries = beneficiaryService.loadBeneficiaries(account.getIdAccount());
            transferView = new TransferView(beneficiaries);
            initTransferController();
            transferView.setVisible(true);
        }
    }

    private void initDepositController() {
        depositView.getDepositButton().addActionListener(e -> submitDeposit());
    }

    private void initTransferController() {
        transferView.getSubmitButton().addActionListener(e -> submitTransfer());
        transferView.getCancelButton().addActionListener(e -> transferView.dispose());
    }
    
    private void initHistoryController() {
        transactionHistoryView.getTypeComboBox().addActionListener(e -> loadTransactionHistory());
    }
    
    public void submitDeposit() {
        try {
        	String amountStr = depositView.getAmount();
        	String description = depositView.getDescription();
            transactionService.deposit(account.getIdAccount(), amountStr, description);
            depositView.showMessage("Deposit successful: " + amountStr, "Success", JOptionPane.INFORMATION_MESSAGE);
            
            depositView.dispose();
            
            // getting the account with new balance
            AccountService accountService = new AccountService();
            Account checkingAccount = accountService.getCheckingAccountByClientId(client.getId());
            
            // 
            new DashboardController(client, checkingAccount, "CHECKING");
            
        } catch (IllegalArgumentException e) {
        	depositView.showMessage(e.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException | SQLException e) {
        	depositView.showMessage("Deposit failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void submitTransfer() {
        try {
            String selectedBeneficiary = transferView.getSelectedBeneficiary();
            String amountStr = transferView.getAmount();
            String description = transferView.getDescription();

            if (selectedBeneficiary == null || selectedBeneficiary.isEmpty() || amountStr == null || amountStr.isEmpty()) {
                JOptionPane.showMessageDialog(transferView, "Please enter all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calling Transaction Service
            transactionService.transfer(
                account.getIdAccount(),
                selectedBeneficiary,
                account.getBalance(),
                amountStr,
                description
            );

            JOptionPane.showMessageDialog(transferView, "Transfer submitted successfully!");
            transferView.dispose();
            
            // getting the account with new balance
            AccountService accountService = new AccountService();
            Account checkingAccount = accountService.getCheckingAccountByClientId(client.getId());
            
            // 
            new DashboardController(client, checkingAccount, "CHECKING");

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(transferView, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(transferView, "An error occurred while processing the transfer. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
	
	
	private void loadTransactionHistory() {
        String selectedType = (String) transactionHistoryView.getTypeComboBox().getSelectedItem();
        try {
            transactionHistoryView.updateTableData(
                transactionService.getTransactionHistory(account.getIdAccount(), selectedType),
                account.getIdAccount()
            );
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(transactionHistoryView, "Failed to load transaction history: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
