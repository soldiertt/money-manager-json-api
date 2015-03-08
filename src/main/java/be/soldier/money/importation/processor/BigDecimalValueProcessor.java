package be.soldier.money.importation.processor;

import com.googlecode.jcsv.annotations.ValueProcessor;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created by soldiertt on 07-02-15.
 */
public class BigDecimalValueProcessor implements ValueProcessor<BigDecimal> {

    @Override
    public BigDecimal processValue(String s) {
        try {
            Number amount = NumberFormat.getInstance().parse(s);
            return new BigDecimal(amount.toString()).abs();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
