package be.soldier.money.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by soldiertt on 29-01-15.
 */
public class Transaction {

    private Integer id;

    private String reference;

    private Date date;

    private BigDecimal amount;

    private Account fromAccount;

    private Account recipient;

    private String communication;

    private Date dateCompta;

    public Transaction(Integer id, String reference, Date date, BigDecimal amount, String communication, Date dateCompta) {
        this.id = id;
        this.reference = reference;
        this.date = date;
        this.amount = amount;
        this.communication = communication;
        this.dateCompta = dateCompta;
    }

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getRecipient() {
        return recipient;
    }

    public void setRecipient(Account recipient) {
        this.recipient = recipient;
    }

    public String getCommunication() {
        return communication;
    }

    public Date getDateCompta() {
        return dateCompta;
    }

    public String getReference() {
        return reference;
    }
}
