package be.soldier.money.importation.processor;

import com.googlecode.jcsv.annotations.ValueProcessor;

/**
 * Created by soldiertt on 07-02-15.
 */
public class LongValueProcessor implements ValueProcessor<Long> {

    @Override
    public Long processValue(String value) {
        if (value.trim().equals("")) {
            return null;
        } else {
            return Long.valueOf(Long.parseLong(value));
        }
    }
}
