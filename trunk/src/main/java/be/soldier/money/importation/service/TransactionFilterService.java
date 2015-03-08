package be.soldier.money.importation.service;

import be.soldier.money.model.Transaction;
import be.soldier.money.model.comparator.TransactionDateComparator;
import be.soldier.money.persistence.dao.TransactionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by soldiertt on 13-02-15.
 */
@Service
public class TransactionFilterService {

    @Autowired
    private TransactionDao txDao;

    public List<Transaction> filterToImport(List<Transaction> txList, Integer maxItems) {

        List<Transaction> txListOut = new ArrayList<>();

        for (Transaction tx : txList) {
            if (tx.getReference() != null && txDao.findTransactionByRef(tx.getReference()) == null) {
                txListOut.add(tx);
            }
        }

        Collections.sort(txListOut, new TransactionDateComparator());

        if (txListOut.size() > maxItems) {
            txListOut = txListOut.subList(0, maxItems);
        }

        return txListOut;
    }

}
