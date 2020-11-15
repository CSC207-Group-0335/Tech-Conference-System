package Schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

public class ScheduleSystem implements Observer {

    public void run(){
        CSVReader fileReader = new CSVReader("Registration.csv");
        for(ArrayList<String> scheduleData: fileReader.getData()){
            UUID userID = UUID.fromString(scheduleData.get(0)); //find what type of user they are
            //find their appropriate schedule manager
            // add the talks to their schedulemanager
        }
    }
    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof User){
    }
}
