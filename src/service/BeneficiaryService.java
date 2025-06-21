package service;

import dao.BeneficiaryDAO;
import dao.AccountDAO;
import model.Account;
import java.sql.SQLException;
import java.util.List;

public class BeneficiaryService {
	private BeneficiaryDAO beneficiaryDAO;
	private AccountDAO accountDAO;

    public BeneficiaryService() throws SQLException {
        this.beneficiaryDAO = new BeneficiaryDAO();
        this.accountDAO = new AccountDAO();
    }

    public boolean addBeneficiary(int accountId, String firstName, String lastName, String rib) throws SQLException {
    	if (rib == null || rib.isEmpty()) {
    		throw new IllegalArgumentException("Please Fill all fields");
    	}
    	
    	Account account = accountDAO.getByRib(rib);
    	if (account == null) {
            throw new IllegalArgumentException("Beneficiary account with this RIB does not exist.");
        }

        int beneficiaryId = account.getIdAccount();
        if (accountId == beneficiaryId) {
            throw new IllegalArgumentException("An account cannot be its own beneficiary.");
        }
        
        return beneficiaryDAO.save(accountId, beneficiaryId);
    }
    
    public List<String[]> loadBeneficiaries(int accountId) throws SQLException {
        return beneficiaryDAO.getBeneficiariesByAccountId(accountId);
    }
}

