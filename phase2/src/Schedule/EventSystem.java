package Schedule;

import Files.CSVReader;
import Files.CSVWriter;
import MessagingPresenters.MessagingSystem;
import UserLogin.*;

import java.lang.reflect.Array;
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
    public UserStorage userStorage;
    public RoomStorage roomStorage;
    public MainMenuController mainMenuController;

    /**
     * creates a new TalkSystem.
     */
    public EventSystem(UserStorage userStorage, RoomStorage roomStorage, MainMenuController mainMenuController){
        this.eventManager = new EventManager(userStorage, roomStorage);
        this.userStorage = userStorage;
        this.roomStorage = roomStorage;
        this.messagingSystem = new MessagingSystem(userStorage, mainMenuController, eventManager);
        this.scheduleSystem = new ScheduleSystem(eventManager, userStorage);
        this.mainMenuController = mainMenuController;
    }

    /**
     * Instantiates user, speaker, and organizer controllers depending on what instance the user is of.
     * @param userEmail The user.
     * @param scanner The scanner to be used for all controllers.
     */
    public void instantiateControllers(String userEmail, Scanner scanner){
        this.addObserver(mainMenuController);
        if (userStorage.emailToType(userEmail).equals("Attendee")){
            this.userScheduleController = new UserScheduleController(userEmail,  eventManager, userStorage,
                    mainMenuController, roomStorage, scanner);
            setUserScheduleController();
            }
        else if (userStorage.emailToType(userEmail).equals("Organizer")){
            this.orgScheduleController = new OrgScheduleController(userEmail, eventManager, userStorage,
                    mainMenuController, roomStorage, scanner);
            this.addObserver(orgScheduleController);
            setOrgScheduleController();
        }
        else{
            this.speakerScheduleController = new SpeakerScheduleController(userEmail, eventManager, userStorage,
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
            String[] speakersArray = talkData.get(2).substring(1,talkData.get(2).length()-1).split("/");
            ArrayList<String> speakers = new ArrayList<String>(Arrays.asList(speakersArray));
            this.eventManager.createEvent(talkData.get(0), talkData.get(1), speakers,
                    talkData.get(3), LocalDateTime.parse(talkData.get(4), formatter),
                    LocalDateTime.parse(talkData.get(5), formatter), talkData.get(6)
                    );
        }
        setTalkManager();
        messagingSystem.run();
        scheduleSystem.run();
    }

    /**
     * Method to write the changes to the Events.csv, called in MainMenuController.logout().
     */
    public void save() {
        CSVWriter csvWriter = new CSVWriter();
        csvWriter.writeToEvents("src/Resources/Events.csv", eventManager);
    }

    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }

    /**
     * Sets talk manager.
     */
    public void setTalkManager() {
        setChanged();
        notifyObservers(eventManager);
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
