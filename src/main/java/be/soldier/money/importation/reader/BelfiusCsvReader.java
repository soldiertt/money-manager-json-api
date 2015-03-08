package be.soldier.money.importation.reader;

import be.soldier.money.importation.dto.BelfiusTransactionDto;
import be.soldier.money.importation.processor.BigDecimalValueProcessor;
import be.soldier.money.importation.processor.LongValueProcessor;
import com.googlecode.jcsv.annotations.internal.ValueProcessorProvider;
import com.googlecode.jcsv.annotations.processors.DateProcessor;
import com.googlecode.jcsv.reader.CSVEntryParser;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.AnnotationEntryParser;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by soldiertt on 13-02-15.
 */
public class BelfiusCsvReader {

    public static final String CSV_FILENAME_START = "BE56 0631 9804 0588";

    public List<BelfiusTransactionDto> read(Reader reader) {

        ValueProcessorProvider provider = new ValueProcessorProvider();
        provider.removeValueProcessor(Date.class);
        provider.registerValueProcessor(Date.class, new DateProcessor(new SimpleDateFormat("dd/MM/yyyy")));
        provider.registerValueProcessor(BigDecimal.class, new BigDecimalValueProcessor());
        provider.removeValueProcessor(Long.class);
        provider.registerValueProcessor(Long.class, new LongValueProcessor());

        CSVEntryParser<BelfiusTransactionDto> entryParser = new AnnotationEntryParser<BelfiusTransactionDto>(BelfiusTransactionDto.class, provider);
        CSVReader<BelfiusTransactionDto> csvTxReader = new CSVReaderBuilder<BelfiusTransactionDto>(reader).entryParser(entryParser).build();

        List<BelfiusTransactionDto> txOut = null;
        try {
            txOut = csvTxReader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return txOut;
    }
}
