package model;

public class SavingsAccount {
    private int idSavingAccount;
    private int idAccount;

    // Constructors
    public SavingsAccount() {}

    public SavingsAccount(int idSavingAccount, int idAccount) {
        this.idSavingAccount = idSavingAccount;
        this.idAccount = idAccount;
    }

    // Getters and Setters
    public int getIdSavingAccount() {
        return idSavingAccount;
    }

    public void setIdSavingAccount(int idSavingAccount) {
        this.idSavingAccount = idSavingAccount;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }
}
