package Schedule;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TalkCSVReader {
    private String csv;
    private ArrayList<ArrayList<String>> data;

    public TalkCSVReader(String csv){
        data = new ArrayList<>();
        this.csv = csv;
        String splitter = ",";
        String l;
        try (BufferedReader br = new BufferedReader(new FileReader(this.csv))) {

            while ((l = br.readLine()) != null) {

                String[] talk; talk = l.split(splitter);

                ArrayList<String> talkdata = new ArrayList<>();
                talkdata.add(talk[0]);
                talkdata.add(talk[1]);
                talkdata.add(talk[2]);
                talkdata.add(talk[3]);
                talkdata.add(talk[4]);
                data.add(talkdata);
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
