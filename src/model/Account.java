package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Account {

    private int idAccount;
    private String accountNumber;
    private String rib;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private boolean isDeleted;
    private LocalDateTime deletedAt;
    private String type;
    private int idClient;

    

    // Constructor with parameters
    public Account(String accountNumber, String rib, BigDecimal balance, LocalDateTime createdAt,
                   boolean isDeleted, LocalDateTime deletedAt, String type, int idClient) {
    	this.accountNumber = accountNumber;
        this.rib = rib;
        this.balance = balance;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
        this.type = type;
        this.idClient = idClient;
    }

    // Getters
    public int getIdAccount() { return idAccount; }
    public String getAccountNumber() { return accountNumber; }
    public String getRib() { return rib; }
    public BigDecimal getBalance() { return balance; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isDeleted() { return isDeleted; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public String getType() { return type; }
    public int getIdClient() { return idClient; }

    // Setters
    public void setIdAccount(int idAccount) { this.idAccount = idAccount; }
    public void setRib(String rib) { this.rib = rib; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
    public void setType(String type) { this.type = type; }
    public void setIdClient(int idClient) { this.idClient = idClient; }
}
