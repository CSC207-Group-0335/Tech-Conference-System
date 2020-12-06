package Schedule;

import Files.CSVReader;
import Files.CSVWriter;
import Files.JSONWriter;
import MessagingPresenters.MessagingSystem;
import UserLogin.*;

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
    public String userEmail;
    public UserManager userManager;
    public RoomStorage roomStorage;
    public MainMenuController mainMenuController;

    /**
     * creates a new TalkSystem.
     */
    public EventSystem(UserManager userManager, RoomStorage roomStorage){
        this.eventManager = new EventManager(userManager, roomStorage);
        this.userManager = userManager;
        this.roomStorage = roomStorage;
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
                    mainMenuController, roomStorage, scanner);
            setUserScheduleController();
            }
        else if (userManager.emailToType(userEmail).equals("Organizer")){
            this.orgScheduleController = new OrgScheduleController(userEmail, eventManager, userManager,
                    mainMenuController, roomStorage, scanner);
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
    public void run() {
        CSVReader fileReader = new CSVReader("src/Resources/Events.csv");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for(ArrayList<String> talkData: fileReader.getData()){
            //FAKE FIX
            int capacity = 2;
            String[] speakersArray = talkData.get(2).substring(1,talkData.get(2).length()-1).split("/");
            ArrayList<String> speakers = new ArrayList<String>(Arrays.asList(speakersArray));
            this.eventManager.createEvent(talkData.get(0), talkData.get(1), speakers,
                    talkData.get(3), LocalDateTime.parse(talkData.get(4), formatter),
                    LocalDateTime.parse(talkData.get(5), formatter), capacity, talkData.get(6)
                    );
        }
        messagingSystem.run();
        scheduleSystem.run();
    }

    /**
     * Method to write the changes to the Events.csv, called in MainMenuController.logout().
     */
    public void save() {
        CSVWriter csvWriter = new CSVWriter();
        csvWriter.writeToEvents("src/Resources/Events.csv", eventManager);
        JSONWriter jsonWriter = new JSONWriter();
        jsonWriter.writeToEvents("src/Resources/Events.json", this.eventManager);
    }

    public void setEmail(String userEmail){
        this.userEmail = userEmail;
    }

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
