package service;

import dao.TransactionDAO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class TransactionService {

    private TransactionDAO transactionDAO;

    public TransactionService() {
        this.transactionDAO = new TransactionDAO();
    }


    public void transfer(int idAccount, String selectedBeneficiary, BigDecimal senderBalance ,String amountStr, String description) throws SQLException {
     
        int idBeneficiary;
        BigDecimal amount;

        try {
        	idBeneficiary = Integer.parseInt(selectedBeneficiary.split(" - ")[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid recipient account ID");
        }

        try {
            amount = new BigDecimal(amountStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        if (senderBalance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance ");
        }


        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        transactionDAO.performTransfer(idAccount, idBeneficiary, amount, description);
    }
    
    
    public void deposit(int accountId, String amountStr, String description) {
        if (amountStr == null || amountStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Amount is required.");
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(amountStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format.");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }

        try {
            transactionDAO.performDeposit(accountId, amount, description);
            throw new IllegalArgumentException("Deposit successful: " + amount);
        } catch (SQLException e) {
            throw new RuntimeException("Deposit failed: " + e.getMessage(), e);
        }
    }
    
    // 
    public List<String[]> getTransactionHistory(int idAccount, String type) {
        try {	
            if (type == null || type.equalsIgnoreCase("ALL")) {
                return transactionDAO.getTransactionHistoryByAccount(idAccount);
            } else if (type.equalsIgnoreCase("DEPOSIT") || type.equalsIgnoreCase("TRANSFER") || type.equalsIgnoreCase("WITHDRAWAL")) {
                return transactionDAO.getTransactionHistoryByAccountAndType(idAccount, type.toUpperCase());
            } else {
                throw new IllegalArgumentException("Invalid transaction type: " + type);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve transaction history: " + e.getMessage(), e);
        }
    }
    
}
