package service;

import dao.AccountDAO;
import model.Account;

import java.math.BigDecimal;
import java.sql.SQLException;

public class AccountService {

    private AccountDAO accountDAO;

    public AccountService() throws SQLException  {
        this.accountDAO = new AccountDAO();
    }

    public int saveAccount(String accountNumber, String rib, String initialBalance, int clientId) throws SQLException {
        if (accountNumber == null || accountNumber.isEmpty() ||
            rib == null || rib.isEmpty() || initialBalance.isEmpty() ||
            clientId <= 0) {
            throw new IllegalArgumentException("A Missing or invalid data for creating the account.");
        }
        
        BigDecimal balance;
        try {
            balance = new BigDecimal(initialBalance);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid initial balance format.");
        }
        
        return accountDAO.save(accountNumber, rib, balance, "CHECKING", clientId);
    }
    
    public Account getCheckingAccountByClientId(int clientId) throws SQLException {
        return accountDAO.getCheckingAccountByClientId(clientId);
    }

}
