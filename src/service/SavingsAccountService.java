package service;

import dao.AccountDAO;
import dao.BeneficiaryDAO;
import dao.SavingsAccountDAO;
import dao.TransactionDAO;
import model.Account;
import model.SavingsAccount;
import util.BankIdentifierGenerator;
import java.math.BigDecimal;
import java.sql.SQLException;

public class SavingsAccountService {

    private final SavingsAccountDAO savingsAccountDAO;

    public SavingsAccountService() throws SQLException {
        this.savingsAccountDAO = new SavingsAccountDAO();
    }

    public Account getSavingsAccount(int idAccount) throws SQLException {
        SavingsAccount minimal = savingsAccountDAO.getSavingsAccountById(idAccount);
        if (minimal == null) {
            return null;
        }
        return savingsAccountDAO.getSavingsAccountDataById(minimal.getIdSavingAccount());
    }
    
    
    public void createSavingsAccount(int idAccount, BigDecimal checkingAccountBalance ,String balanceStr, String description) throws SQLException {
    	
    	// Casting balanceStr
    	BigDecimal balance;
    	try {
            balance = new BigDecimal(balanceStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid balance amount: " + balanceStr);
        }
    	
    	if (balance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Balance must be positive.");
        }
    	
    	if (checkingAccountBalance.compareTo(balance) >= 0) {
    		// generate account and RIB numbers 
        	String accountNumber =  BankIdentifierGenerator.generateAccountNumber();
        	String rib = BankIdentifierGenerator.generateRIB();
        	
        	// Creating the savings account in ACCOUNT table
            AccountDAO accountDAO = new AccountDAO();
            int idSavingsAccount = accountDAO.save(accountNumber, rib, BigDecimal.ZERO, "SAVINGS", idAccount);
        	
        	// Creating Savings Account
        	savingsAccountDAO.save(idAccount, idSavingsAccount);
        	
        	// save transfer transaction
        	TransactionDAO transactionDAO = new TransactionDAO();
        	transactionDAO.performTransfer(idAccount, idSavingsAccount, balance, description);
        	
        	// add it as a Beneficiary of the checking account
        	BeneficiaryDAO beneficiaryDAO = new BeneficiaryDAO();
        	beneficiaryDAO.save(idAccount, idSavingsAccount);
    	} else {
    		throw new SQLException("Insufficient funds in checking account to create savings account.");
    	}
    }
    
    
}
