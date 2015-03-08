package be.soldier.money.importation.reader;

import be.soldier.money.common.TransactionImportType;
import be.soldier.money.importation.dto.ArgentaTransactionDto;
import be.soldier.money.importation.processor.BigDecimalValueProcessor;
import be.soldier.money.importation.processor.TransactionImportTypeValueProcessor;
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
public class ArgentaCsvReader {

    public static final String CSV_FILENAME_START = "BE04973097203431";

    public List<ArgentaTransactionDto> read(Reader reader) {

        ValueProcessorProvider provider = new ValueProcessorProvider();
        provider.removeValueProcessor(Date.class);
        provider.registerValueProcessor(Date.class, new DateProcessor(new SimpleDateFormat("dd-MM-yy")));
        provider.registerValueProcessor(BigDecimal.class, new BigDecimalValueProcessor());
        provider.registerValueProcessor(TransactionImportType.class, new TransactionImportTypeValueProcessor());

        CSVEntryParser<ArgentaTransactionDto> entryParser = new AnnotationEntryParser<ArgentaTransactionDto>(ArgentaTransactionDto.class, provider);
        CSVReader<ArgentaTransactionDto> csvTxReader = new CSVReaderBuilder<ArgentaTransactionDto>(reader).entryParser(entryParser).build();

        List<ArgentaTransactionDto> txOut = null;
        try {
            txOut = csvTxReader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return txOut;
    }
}
