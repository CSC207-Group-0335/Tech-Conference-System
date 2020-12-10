package Schedule;

import Files.JSONReader;
import Files.JSONWriter;
import UserLogin.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

/**
 * Gateway Class that reads a .csv file for all user schedules and adds events to the appropriate user schedule manager
 * and speaker schedule manager for for every user/speaker.
 */
public class ScheduleSystem{
    EventManager eventManager;
    UserManager userManager;

    /**
     * Creates a new ScheduleSystem with the specified EventManager.
     * @param eventManager The EventManager.
     */
    public ScheduleSystem(EventManager eventManager, UserManager userManager){
        this.eventManager = eventManager;
        this.userManager = userManager;
    }

    /**
     * Reads the cvs and creates the userScheduleMap.
     */
    public void run() throws Exception {
        JSONReader jsonReader = new JSONReader();
        Object obj = jsonReader.readJson("src/Resources/Registration.json");
        JSONArray regList = (JSONArray) obj;
        regList.forEach(reg -> {
            JSONObject regist = (JSONObject) reg; //cast reg as a JSONObject
            String email = (String) regist.get("email");
            ArrayList<String> eventList = (ArrayList<String>) regist.get("eventList");
            if (userManager.emailToType(email).equals("Organizer") ||
                    userManager.emailToType(email).equals("Attendee")){
                for (String eventID: eventList) {
                    if (eventManager.exists(eventID)) {
                        userManager.addEvent(email, eventID);
                    }
                }
            }
        });
        }

    /**
     * Method to write the changes to the Registration.csv, called in MainMenuController.logout().
     */
    public void save(){
        JSONWriter jsonWriter = new JSONWriter();
        jsonWriter.writeToRegistration("src/Resources/Registration.json", userManager);
    }
}
