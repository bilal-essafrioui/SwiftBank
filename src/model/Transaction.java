package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private int idTransaction;
    private String type;
    private BigDecimal amount;
    private String description;
    private LocalDateTime transactionDate;
    private String status;
    private int accountId;
    private Integer recipientId; // Nullable for DEPOSIT/WITHDRAWAL

    public Transaction(String type, BigDecimal amount, String description,
                       LocalDateTime transactionDate, String status, int accountId, Integer recipientId) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.status = status;
        this.accountId = accountId;
        this.recipientId = recipientId;
    }

    // Getters and setters
    public int getIdTransaction() { return idTransaction; }
    public void setIdTransaction(int idTransaction) { this.idTransaction = idTransaction; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public Integer getRecipientId() { return recipientId; }
    public void setRecipientId(Integer recipientId) { this.recipientId = recipientId; }
}