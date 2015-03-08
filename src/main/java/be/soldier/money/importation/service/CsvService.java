package be.soldier.money.importation.service;

import be.soldier.money.importation.dto.ArgentaTransactionDto;
import be.soldier.money.importation.dto.BelfiusTransactionDto;
import be.soldier.money.importation.reader.ArgentaCsvReader;
import be.soldier.money.importation.reader.BelfiusCsvReader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by soldiertt on 07-02-15.
 */
@Service
public class CsvService {

    public List<ArgentaTransactionDto> readArgentaTransactions() {

        ArgentaCsvReader argentaReader = new ArgentaCsvReader();

        try {
            File folder = new File("F:\\Downloads");

            File[] listOfFiles = folder.listFiles((File dir, String name) -> {
                    return name.endsWith(".csv") && name.startsWith(ArgentaCsvReader.CSV_FILENAME_START);
                }
            );

            List<ArgentaTransactionDto> argentTxList = new ArrayList<>();
            for (File csvFile : listOfFiles) {
                Reader reader = new FileReader(csvFile);
                argentTxList.addAll(argentaReader.read(reader));
            }

            return argentTxList;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<BelfiusTransactionDto> readBelfiusTransactions() {

        BelfiusCsvReader belfiusReader = new BelfiusCsvReader();

        try {
            File folder = new File("F:\\Downloads");

            File[] listOfFiles = folder.listFiles((File dir, String name) -> {
                    return name.endsWith(".csv") && name.startsWith(BelfiusCsvReader.CSV_FILENAME_START);
                }
            );

            List<BelfiusTransactionDto> belfiusTxList = new ArrayList<>();
            for (File csvFile : listOfFiles) {
                Reader reader = new FileReader(csvFile);
                belfiusTxList.addAll(belfiusReader.read(reader));
            }

            return belfiusTxList;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
