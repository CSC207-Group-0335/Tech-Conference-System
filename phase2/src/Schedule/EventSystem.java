package Schedule;

import Files.JSONReader;
import Files.JSONWriter;
import MessagingPresenters.MessagingSystem;
import UserLogin.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * A gateway class that reads a .csv file for
 * all accounts with their credentials and requests creating talks (from TalkManager)
 */
public class EventSystem extends Observable{
    public OrgScheduleController orgScheduleController;
    public UserScheduleController userScheduleController;
    public SpeakerScheduleController speakerScheduleController;
    public EventManager eventManager;
    public MessagingSystem messagingSystem;
    public ScheduleSystem scheduleSystem;
    public UserManager userManager;
    public RoomManager roomManager;
    public MainMenuController mainMenuController;

    /**
     * creates a new TalkSystem.
     */
    public EventSystem(UserManager userManager, RoomManager roomManager){
        this.eventManager = new EventManager(userManager, roomManager);
        this.userManager = userManager;
        this.roomManager = roomManager;
        this.messagingSystem = new MessagingSystem(userManager, eventManager);
        this.scheduleSystem = new ScheduleSystem(eventManager, userManager);
    }

    /**
     * Instantiates user, speaker, and organizer controllers depending on what instance the user is of.
     * @param userEmail The user.
     * @param scanner The scanner to be used for all controllers.
     */
    public void instantiateControllers(String userEmail, Scanner scanner){
        this.addObserver(mainMenuController);
        if (userManager.emailToType(userEmail).equals("Attendee")){
            this.userScheduleController = new UserScheduleController(userEmail,  eventManager, userManager,
                    mainMenuController, roomManager, scanner);
            setUserScheduleController();
            }
        else if (userManager.emailToType(userEmail).equals("Organizer")){
            this.orgScheduleController = new OrgScheduleController(userEmail, eventManager, userManager,
                    mainMenuController, roomManager, scanner);
            setOrgScheduleController();
        }
        else{
            this.speakerScheduleController = new SpeakerScheduleController(userEmail, eventManager, userManager,
                    mainMenuController, scanner);
            setSpeakerScheduleController();
        }

    }

    /**
     * Adds observers, calls the run methods of messagingSystem and scheduleSystem.
     */
    public void run() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        JSONReader jsonReader = new JSONReader();
        Object obj = jsonReader.readJson("src/Resources/Events.json");
        JSONArray eventList = (JSONArray) obj;
        eventList.forEach(eve -> {
            JSONObject event = (JSONObject) eve; //cast eve as a JSONObject
            //get all of the necessary elements to create an event from the event object
            String eventID = (String) event.get("eventId");
            String title = (String) event.get("title");
            String roomName = (String) event.get("roomName");
            Long capacity = (Long) event.get("capacity");
            int c = capacity.intValue();
            LocalDateTime startTime = LocalDateTime.parse((CharSequence) event.get("startTime"), formatter);
            LocalDateTime endTime = LocalDateTime.parse((CharSequence) event.get("endTime"), formatter);
            boolean vip = (boolean) event.get("vipRestricted");
            ArrayList<String> speakerEmails = (ArrayList<String>) event.get("speakers");
            this.eventManager.createEvent(eventID, title, speakerEmails, roomName, startTime, endTime, c, vip);
        });
        messagingSystem.run();
        scheduleSystem.run();
    }

    /**
     * Method to write the changes to the Events.csv, called in MainMenuController.logout().
     */
    public void save() {
        JSONWriter jsonWriter = new JSONWriter();
        jsonWriter.writeToEvents("src/Resources/Events.json", this.eventManager);
    }



    /**
     * Sets a main menu controller.
     *
     * @param mainMenuController a MainMenuController
     */

    public void setMainMenuController(MainMenuController mainMenuController){
        this.mainMenuController = mainMenuController;
    }

    /**
     * Sets the organizer schedule controller.
     */
    public void setOrgScheduleController(){
        setChanged();
        notifyObservers(orgScheduleController);
    }

    /**
     * Sets the user schedule controller.
     */
    public void setUserScheduleController(){
        setChanged();
        notifyObservers(userScheduleController);
    }

    /**
     * Sets the speaker schedule controller.
     */
    public void setSpeakerScheduleController(){
        setChanged();
        notifyObservers(speakerScheduleController);
    }
}
