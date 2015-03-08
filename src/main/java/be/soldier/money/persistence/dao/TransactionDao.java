package be.soldier.money.persistence.dao;

import be.soldier.money.model.Account;
import be.soldier.money.model.MoneyLabel;
import be.soldier.money.model.Occurence;
import be.soldier.money.model.Transaction;
import be.soldier.money.persistence.jooq.tables.records.TxRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static be.soldier.money.persistence.jooq.tables.Account.ACCOUNT;
import static be.soldier.money.persistence.jooq.tables.Tx.TX;
/**
 * Created by soldiertt on 29-01-15.
 */
@Repository("transactionDao")
public class TransactionDao {

    @Autowired
    private DSLContext create;

    @Autowired
    private LabelDao labelDao;

    public List<Occurence> findOccurencesByLabelId(Integer labelId) {

        List<Occurence> occurences = new ArrayList<Occurence>();

        MoneyLabel label = labelDao.getLabelById(labelId);

        for (int i = 1; i <= label.getOccSize(); i++) {
            occurences.add(findOccurenceByLabelIdAndIndex(labelId, i));
        }

        return occurences;
    }

    public Occurence findOccurenceByLabelIdAndIndex(Integer labelId, Integer index) {

        Occurence occurence = new Occurence();

        be.soldier.money.persistence.jooq.tables.Account FROM_ACCOUNT = ACCOUNT.as("from");
        be.soldier.money.persistence.jooq.tables.Account TO_ACCOUNT = ACCOUNT.as("to");

        Result<?> result = create.select().from(TX)
                .join(FROM_ACCOUNT).on(FROM_ACCOUNT.ID.equal(TX.FROM_ACCOUNT_ID))
                .join(TO_ACCOUNT).on(TO_ACCOUNT.ID.equal(TX.RECIPIENT_ID))
                .where(TX.LABEL_ID.equal(labelId))
                .and(TX.LABEL_INDEX.equal(index))
                .fetch();

        for (Record record : result) {
            Transaction tx = new Transaction(record.getValue(TX.ID), record.getValue(TX.REFERENCE), record.getValue(TX.DATE),
                    new BigDecimal(record.getValue(TX.AMOUNT)), record.getValue(TX.COMMUNICATION),
                    record.getValue(TX.DATE_COMPTA));
            Account fromAccount = new Account(record.getValue(FROM_ACCOUNT.ID), record.getValue(FROM_ACCOUNT.NAME),
                    record.getValue(FROM_ACCOUNT.ACCOUNT_NUMBER), record.getValue(TO_ACCOUNT.OWN) != 0);
            tx.setFromAccount(fromAccount);
            Account toAccount = new Account(record.getValue(TO_ACCOUNT.ID), record.getValue(TO_ACCOUNT.NAME),
                    record.getValue(TO_ACCOUNT.ACCOUNT_NUMBER), record.getValue(TO_ACCOUNT.OWN) != 0);
            tx.setRecipient(toAccount);
            occurence.addTransaction(tx);
        }
        return occurence;
    }

    @Transactional
    public void save(TxRecord tx) {

        create.insertInto(TX,
                TX.REFERENCE,
                TX.AMOUNT,
                TX.DATE,
                TX.FROM_ACCOUNT_ID,
                TX.RECIPIENT_ID,
                TX.COMMUNICATION,
                TX.DATE_COMPTA,
                TX.LABEL_ID,
                TX.LABEL_INDEX)
                .values(tx.getReference(),
                        tx.getAmount(),
                        tx.getDate(),
                        tx.getFromAccountId(),
                        tx.getRecipientId(),
                        tx.getCommunication(),
                        tx.getDateCompta(),
                        tx.getLabelId(),
                        tx.getLabelIndex()).execute();

    }

    public TxRecord findTransactionByRef(String reference) {
        return create.selectFrom(TX).where(TX.REFERENCE.equal(reference)).fetchOne();
    }

    @Transactional
    public void deleteTransaction(Integer txId) {
        create.delete(TX).where(TX.ID.equal(txId)).execute();
    }
}
