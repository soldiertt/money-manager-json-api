package be.soldier.money.persistence.dao;

import be.soldier.money.common.ConvertUtil;
import be.soldier.money.model.Account;
import be.soldier.money.persistence.jooq.tables.records.AccountRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static be.soldier.money.persistence.jooq.tables.Account.ACCOUNT;
/**
 * Created by soldiertt on 29-01-15.
 */
@Repository("accountDao")
public class AccountDao implements InitializingBean {

    private static final String BANCONTACT_ACCOUNT_NUMBER = "978-9000075-53";

    public static final String ARGENTA_MAIN_ACCOUNT = "BE04 9730 9720 3431";
    public static final String BELFIUS_MAIN_ACCOUNT = "063-1680405-88";

    @Autowired
    private DSLContext create;

    @Override
    public void afterPropertiesSet() throws Exception {
        findOrCreateAccount(ARGENTA_MAIN_ACCOUNT, "Argenta");
        findOrCreateAccount(BELFIUS_MAIN_ACCOUNT, "Belfius");
        findOrCreateAccount("", "Belfius-Bancontact");
        findOrCreateAccount(BANCONTACT_ACCOUNT_NUMBER, "BANCONTACT");
    }

    public List<Account> findAccounts(Boolean own) {

        List<Account> accounts = new ArrayList<Account>();
        Result<AccountRecord> result = create.selectFrom(ACCOUNT).where(ACCOUNT.OWN.equal(ConvertUtil.toByte(own))).fetch();

        for (AccountRecord record : result) {
            Account account = new Account(record.getId(), record.getName(),
                    record.getAccountNumber(), ConvertUtil.toBool(record.getOwn()));
            accounts.add(account);
        }
        return accounts;
    }

    @Transactional
    public Account findOrCreateAccount(String accountNumber, String accountName) {
        AccountRecord accountRecord = create.selectFrom(ACCOUNT).where(ACCOUNT.ACCOUNT_NUMBER.eq(accountNumber)).fetchOne();
        if (accountRecord != null) {
            return new Account(accountRecord.getId(), accountRecord.getName(),
                    accountRecord.getAccountNumber(), ConvertUtil.toBool(accountRecord.getOwn()));
        } else {
            accountRecord = new AccountRecord();
            accountRecord.setAccountNumber(accountNumber);
            accountRecord.setName(accountName);
            accountRecord.setOwn(ConvertUtil.toByte(ARGENTA_MAIN_ACCOUNT.equals(accountNumber) || BELFIUS_MAIN_ACCOUNT.equals(accountNumber)));
            return saveAccount(accountRecord);
        }
    }

    @Transactional
    public Account saveAccount(AccountRecord account) {

        Record record = create.insertInto(ACCOUNT,
                ACCOUNT.NAME,
                ACCOUNT.ACCOUNT_NUMBER,
                ACCOUNT.OWN)
                .values(account.getName(),
                        account.getAccountNumber(),
                        account.getOwn()).returning(ACCOUNT.ID).fetchOne();
        Integer accountId = record.getValue(ACCOUNT.ID);
        return new Account(accountId, account.getName(), account.getAccountNumber(), ConvertUtil.toBool(account.getOwn()));
    }

}

