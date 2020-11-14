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

    public CSVReader(String csv, int numParameters){
        data = new ArrayList<>();
        this.numParameters = numParameters;
        this.csv = csv;
        String splitter = ",";
        String l;
        try (BufferedReader br = new BufferedReader(new FileReader(this.csv))) {

            while ((l = br.readLine()) != null) {

                String[] column; column = l.split(splitter);

                ArrayList<String> rowdata = new ArrayList<>();
                for(int i = 0; i < this.numParameters; i++){
                rowdata.add(column[i]);
                }
                data.add(rowdata);
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
