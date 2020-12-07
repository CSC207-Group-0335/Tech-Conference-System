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
    UserManager storage;
    EventManager eventManager;
    UserManager userManager;

    /**
     * Creates a new ScheduleSystem with the specified talkManager.
     * @param eventManager The talkManager.
     */
    public ScheduleSystem(EventManager eventManager, UserManager userManager){
        this.eventManager = eventManager;
        this.userManager = userManager;
    }

    /**
     * Reads the cvs and creates the userScheduleMap.
     */
    public void run(){
        CSVReader fileReader = new CSVReader("src/Resources/Registration.csv");
        for(ArrayList<String> scheduleData: fileReader.getData()){
            String email = scheduleData.get(0);
            if (userManager.emailToType(email).equals("Organizer") ||
                    userManager.emailToType(email).equals("Attendee")){
            for(int i =1; i< scheduleData.size(); i++){
                String id = scheduleData.get(i);
                if (eventManager.exists(id)) {
                    userManager.addEvent(email, id);
                }
            }}}
        }

    /**
     * Method to write the changes to the Registration.csv, called in MainMenuController.logout().
     */
    public void save() {
        CSVWriter csvWriter = new CSVWriter();
        csvWriter.writeToRegistration("src/Resources/Registration.csv", userManager);
    }
}
