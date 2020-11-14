package UserLogin;

import java.io.*;
import java.util.ArrayList;

public class UsersCSVWriter {
    private String csv;


    public UsersCSVWriter(String csv, ArrayList<User> storage){
        this.csv = csv;


        try (FileWriter csvWriter = new FileWriter(this.csv)) {
            int i = 0;
            while (i < storage.size()) {
                User user = storage.get(i);
                csvWriter.append(user.getType());
                csvWriter.append(",");
                csvWriter.append(user.getName());
                csvWriter.append(",");
                csvWriter.append(user.getPassword());
                csvWriter.append(",");
                csvWriter.append(user.getEmail());
                csvWriter.append("\n");
                i++;
                csvWriter.flush();
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

}


