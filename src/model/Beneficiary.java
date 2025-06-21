package model;

public class Beneficiary {
    private int accountId;        // id_account (authenticated)
    private int beneficiaryId;    // id_beneficiary (beneficiary account)

    public Beneficiary(int accountId, int beneficiaryId) {
        this.accountId = accountId;
        this.beneficiaryId = beneficiaryId;
    }
    
    // Getters
    public int getAccountId() { return accountId; }
    public int getBeneficiaryId() { return beneficiaryId; }
    
    // Setters 
    public void setAccountId(int accountId) { this.accountId = accountId; }
    public void setBeneficiaryId(int beneficiaryId) { this.beneficiaryId = beneficiaryId;}
}
