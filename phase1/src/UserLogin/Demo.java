package UserLogin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Demo {

    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("Resources/Users.csv");
        System.out.println(f.exists());
        //BufferedReader br = new BufferedReader(new FileReader("Users.csv"));
        //TechConferenceSystem tCS = new TechConferenceSystem();
        //tCS.run();
    }
}
