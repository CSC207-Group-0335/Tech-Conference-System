package UserLogin;

import java.io.*;
import java.util.*;

/**
 * Iterates through a list of String prompts
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
    private String csvFile;

    public CSVReader(String csv) throws IOException {
        this.csvFile = csv;
        String csvSplitBy = ",";



    }






        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = split(csvSplitBy);

                System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
