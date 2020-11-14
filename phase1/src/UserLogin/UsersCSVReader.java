package UserLogin;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class UsersCSVReader {
    private String csv;
    private ArrayList<ArrayList<String>> data;

    public UsersCSVReader(String csv){
        data = new ArrayList<>();
        this.csv = csv;
        String splitter = ",";
        String l;
        try (BufferedReader br = new BufferedReader(new FileReader(this.csv))) {

            while ((l = br.readLine()) != null) {

                String[] user; user = l.split(splitter);

                ArrayList<String> userdata = new ArrayList<>();
                userdata.add(user[0]);
                userdata.add(user[1]);
                userdata.add(user[2]);
                userdata.add(user[3]);
                data.add(userdata);
            }

        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
    public ArrayList<ArrayList<String>> getData() {
        return data;
    }
}


