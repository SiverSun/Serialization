import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class ClientLog {
    List<String> logFile = new ArrayList<>();

    public void log(int productNum, int amount) {
        logFile.add((productNum + 1) + "," + (amount + 1));
    }

    public void exportAsCSV(File txtFile) throws Exception {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(txtFile, true), ',', CSVWriter.NO_QUOTE_CHARACTER)) {
            logFile.forEach(s -> csvWriter.writeNext(s.split(",")));
        }
    }
}

