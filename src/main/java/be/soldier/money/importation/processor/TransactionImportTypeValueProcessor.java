package be.soldier.money.importation.processor;

import be.soldier.money.common.TransactionImportType;
import com.googlecode.jcsv.annotations.ValueProcessor;

/**
 * Created by soldiertt on 07-02-15.
 */
public class TransactionImportTypeValueProcessor implements ValueProcessor<TransactionImportType> {

    @Override
    public TransactionImportType processValue(String s) {
        try {
            return TransactionImportType.fromValue(s);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }
}
