package be.soldier.money.importation.service;

import be.soldier.money.common.TransactionImportType;
import be.soldier.money.importation.dto.ArgentaTransactionDto;
import be.soldier.money.importation.dto.BelfiusTransactionDto;
import be.soldier.money.model.Transaction;
import be.soldier.money.persistence.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by soldiertt on 13-02-15.
 */
@Service
public class TransactionMapperService {

    @Autowired
    private AccountDao accountDao;

    public Transaction map(ArgentaTransactionDto txDto) {

        Transaction tx = null;
        if (txDto.getType().equals(TransactionImportType.BANCONTACT)
                || txDto.getType().equals(TransactionImportType.RETRAIT)
                || txDto.getType().equals(TransactionImportType.CARBURANT)) {
            tx = new Transaction(null, txDto.getReference(), txDto.getTxDate(), txDto.getAmount(), txDto.getAccountName(), null);
        } else {
            tx = new Transaction(null, txDto.getReference(), txDto.getTxDate(), txDto.getAmount(), txDto.getCommunication(), null);
        }

        if (txDto.getType().equals(TransactionImportType.FOR_ME)) {
            tx.setRecipient(accountDao.findOrCreateAccount(AccountDao.ARGENTA_MAIN_ACCOUNT, "Argenta"));
            tx.setFromAccount(accountDao.findOrCreateAccount(txDto.getRecipient(), txDto.getAccountName()));
        } else {
            tx.setFromAccount(accountDao.findOrCreateAccount(AccountDao.ARGENTA_MAIN_ACCOUNT, "Argenta"));
            tx.setRecipient(accountDao.findOrCreateAccount(txDto.getRecipient(), txDto.getAccountName()));
        }

        return tx;
    }

    public Transaction map(BelfiusTransactionDto txDto) {
        Calendar txDate = Calendar.getInstance();
        txDate.setTime(txDto.getTxDate());
        String reference = txDate.get(Calendar.YEAR) + "-" + txDto.getExtractNum() + "-" + txDto.getTxNum();
        if (txDto.getExtractNum() == null) {
            reference = null;
        }

        Transaction tx = new Transaction(null, reference, txDto.getTxDate(), txDto.getAmount(), txDto.getCommunication(), null);

        tx.setFromAccount(accountDao.findOrCreateAccount(AccountDao.BELFIUS_MAIN_ACCOUNT, "Belfius"));
        tx.setRecipient(accountDao.findOrCreateAccount(txDto.getRecipient(), txDto.getAccountName()));

        return tx;
    }

    public List<Transaction> mapArgentaTxList(List<ArgentaTransactionDto> argentaTxList) {
        List<Transaction> txListOut = new ArrayList<>();
        for (ArgentaTransactionDto argentaTx : argentaTxList) {
            txListOut.add(this.map(argentaTx));
        }
        return txListOut;
    }

    public List<Transaction> mapBelfiusTxList(List<BelfiusTransactionDto> belfiusTxList) {
        List<Transaction> txListOut = new ArrayList<>();
        for (BelfiusTransactionDto belfiusTx : belfiusTxList) {
            txListOut.add(this.map(belfiusTx));
        }
        return txListOut;
    }
}
