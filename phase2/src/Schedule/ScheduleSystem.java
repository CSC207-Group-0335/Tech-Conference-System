package Schedule;

import Files.CSVReader;
import Files.CSVWriter;
import UserLogin.*;

import java.util.*;

/**
 * Gateway Class that reads a .csv file for all user schedules and adds talks to the appropriate user schedule manager
 * and speaker schedule manager for for every user/speaker.
 */
public class ScheduleSystem{
    UserStorage storage;
    EventManager eventManager;
    UserStorage userStorage;

    /**
     * Creates a new ScheduleSystem with the specified talkManager.
     * @param eventManager The talkManager.
     */
    public ScheduleSystem(EventManager eventManager, UserStorage userStorage){
        this.eventManager = eventManager;
        this.userStorage = userStorage;
    }

    /**
     * Reads the cvs and creates the userScheduleMap.
     */
    public void run(){
        CSVReader fileReader = new CSVReader("src/Resources/Registration.csv");
        for(ArrayList<String> scheduleData: fileReader.getData()){
            String email = scheduleData.get(0);
            User user = findUser(email);
            if (user instanceof Organizer || user instanceof Attendee){
            for(int i =1; i< scheduleData.size(); i++){
                String id = scheduleData.get(i);
                Event event = findTalk(id);
                if (event != null) {
                    userSchedule.addTalk(event);
                }
            }}}
        }

    /**
     * Method to write the changes to the Registration.csv, called in MainMenuController.logout().
     */
    public void save() {
        CSVWriter csvWriter = new CSVWriter();
        csvWriter.writeToRegistration("phase1/src/Resources/Registration.csv", this.getUserScheduleMap());
    }
}
