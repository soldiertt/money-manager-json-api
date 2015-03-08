package be.soldier.money.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by soldiertt on 29-01-15.
 */
public class Occurence {

    private BigDecimal total;

    private List<Transaction> transactions;

    public Occurence() {
        this.transactions = new ArrayList<Transaction>();
        this.total = new BigDecimal(0.0);
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void addTransaction(Transaction tx) {
        this.transactions.add(tx);
        this.total = this.total.add(tx.getAmount());
    }

    public void deleteTransactionById(Long txId) {
        Transaction txToDel = null;
        for (Transaction tx : transactions) {
            if (tx.getId().equals(txId)) {
                txToDel = tx;
                break;
            }
        }
        if (txToDel != null) {
            this.total = this.total.subtract(txToDel.getAmount());
            this.transactions.remove(txToDel);
        }
    }
}
