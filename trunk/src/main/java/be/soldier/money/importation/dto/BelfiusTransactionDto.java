package be.soldier.money.importation.dto;

import com.googlecode.jcsv.annotations.MapToColumn;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by soldiertt on 03-02-15.
 */
public class BelfiusTransactionDto {

    @MapToColumn(column = 1)
    private Date txDate;

    @MapToColumn(column = 2)
    private Long extractNum;

    @MapToColumn(column = 3)
    private Long txNum;

    @MapToColumn(column = 10)
    private BigDecimal amount;

    @MapToColumn(column = 4)
    private String recipient;

    @MapToColumn(column = 5)
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

    public Date getTxDate() {
        return txDate;
    }

    public Long getExtractNum() {
        return extractNum;
    }

    public Long getTxNum() {
        return txNum;
    }
}
