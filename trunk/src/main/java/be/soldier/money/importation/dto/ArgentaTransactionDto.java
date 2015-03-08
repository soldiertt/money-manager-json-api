package be.soldier.money.importation.dto;

import be.soldier.money.common.TransactionImportType;
import com.googlecode.jcsv.annotations.MapToColumn;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by soldiertt on 03-02-15.
 */
public class ArgentaTransactionDto {

    @MapToColumn(column = 0)
    private Date txDate;

    @MapToColumn(column = 1)
    private String reference;

    @MapToColumn(column = 2)
    private TransactionImportType type;

    @MapToColumn(column = 3)
    private BigDecimal amount;

    @MapToColumn(column = 6)
    private String recipient;

    @MapToColumn(column = 7)
    private String accountName;

    @MapToColumn(column = 8)
    private String communication;

    public String getAccountName() {
        return accountName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCommunication() {
        return communication;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getReference() {
        return reference;
    }

    public Date getTxDate() {
        return txDate;
    }

    public TransactionImportType getType() {
        return type;
    }
}
