package Schedule;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {
    private String csv;
    private ArrayList<ArrayList<String>> data;
    private int numParameters;

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
    public ArrayList<ArrayList<String>> getData() {
        return data;
    }
}
