package be.soldier.money.model.comparator;

import be.soldier.money.model.Transaction;

import java.util.Comparator;

/**
 * Created by soldiertt on 08-02-15.
 */
public class TransactionDateComparator implements Comparator<Transaction> {
    @Override
    public int compare(Transaction o1, Transaction o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
