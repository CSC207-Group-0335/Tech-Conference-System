package Files;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A general Gateway class that handles reading from the assorted files in the program
 */

public class CSVReader {
    private String csv;
    private ArrayList<ArrayList<String>> data;
    private int numParameters;

    /**
     * Constructor used to read the contents of the file (usually a csv file).
     * @param csv the file that is being read from.
     */

    public CSVReader(String csv){
        data = new ArrayList<>();
        this.numParameters = numParameters;
        this.csv = csv;
        String splitter = ",";
        String l;
        try (BufferedReader br = new BufferedReader(new FileReader(this.csv))) {

            while ((l = br.readLine()) != null) {

                String[] column; column = l.split(splitter);

                ArrayList<String> rowData = new ArrayList<>();
                for(String columnData:column){
                rowData.add(columnData);
                }
                data.add(rowData);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * a getter method for the data that has been updated in the constructor.
     * @return an arraylist of Strings representing the lines in the file that has been read from.
     */
    public ArrayList<ArrayList<String>> getData() {
        return data;
    }
}
