package be.soldier.money.common;

import be.soldier.money.model.Account;
import be.soldier.money.model.MoneyLabel;
import org.jooq.Record;

import java.math.BigDecimal;

import static be.soldier.money.persistence.jooq.tables.Account.ACCOUNT;
import static be.soldier.money.persistence.jooq.tables.Label.LABEL;

/**
 * Created by soldiertt on 07-02-15.
 */
public class LabelUtil {

    public static MoneyLabel makeMoneyLabel(Record record) {

        LabelType labelType = LabelType.valueOf(record.getValue(LABEL.TYPE));
        MoneyLabel out = new MoneyLabel(record.getValue(LABEL.ID),record.getValue(LABEL.NAME),
                record.getValue(LABEL.YEAR), ConvertUtil.toBool(record.getValue(LABEL.FIXED)),
                ConvertUtil.toBool(record.getValue(LABEL.INCOME)), labelType, getDefaultAmount(record));
        out.setDefaultRecipient(getDefaultRecipient(record));
        return out;
    }

    private static BigDecimal getDefaultAmount(Record record) {
        BigDecimal defaultAmount = new BigDecimal(0.0);
        if (record.getValue(LABEL.DEFAULT_AMOUNT) != null) {
            defaultAmount =  new BigDecimal(record.getValue(LABEL.DEFAULT_AMOUNT).toString());
        }
        return defaultAmount;
    }

    private static Account getDefaultRecipient(Record record) {
        if (record.getValue(LABEL.DEFAULT_RECIPIENT_ID) != null) {
            return new Account(record.getValue(ACCOUNT.ID), record.getValue(ACCOUNT.NAME),
                    record.getValue(ACCOUNT.ACCOUNT_NUMBER), ConvertUtil.toBool(record.getValue(ACCOUNT.OWN)));
        } else {
            return null;
        }
    }
}
