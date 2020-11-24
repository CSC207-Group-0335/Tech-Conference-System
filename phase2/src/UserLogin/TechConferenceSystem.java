package UserLogin;

import Files.CSVReader;
import Files.CSVWriter;
import Schedule.RoomSystem;
import Schedule.SpeakerScheduleManager;
import Schedule.UserScheduleManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

/**
 * A Gateway class that interacts with an external file (.csv) that stores a collection of all accounts currently
 * registered in the database.
 */

public class TechConferenceSystem extends Observable {

    public UserStorage userStorage;
    public ArrayList<User> userList;
    public HashMap<User, UserScheduleManager> userScheduleMap;
    public HashMap<Speaker, SpeakerScheduleManager> speakerScheduleMap;
    public LogInController logInController;
    public RoomSystem roomSystem;
    public MainMenuController mainMenuController;

    /**
     * A constructor for the TechConferenceSystem that contains an instance of everything needed to run the program,
     * and everything that should be "common" to all instances of TechConferenceSystem.
     */

    public TechConferenceSystem() {
        this.userStorage = new UserStorage();
        this.userList = new ArrayList<>();
        this.userScheduleMap = new HashMap<User, UserScheduleManager>();
        this.roomSystem = new RoomSystem();
        this.logInController = new LogInController(this.mainMenuController, this.roomSystem.talkSystem,
                this.roomSystem.talkSystem.messagingSystem);
        this.mainMenuController = new MainMenuController(logInController.scanner, roomSystem,
                roomSystem.talkSystem, roomSystem.talkSystem.messagingSystem, roomSystem.talkSystem.scheduleSystem,
                this);
    }

    /**
     * Sets the UserStorage and updates the observers.
     */

    public void setUserStorage(){
        setChanged();
        notifyObservers(this.userStorage);
        if (roomSystem.talkSystem.messagingSystem.speakerMessengerController !=null) {
            notifyObservers(roomSystem.talkSystem.messagingSystem.speakerMessengerController.userInfo);
        }
        if (roomSystem.talkSystem.messagingSystem.attendeeMessengerController !=null) {
            notifyObservers(roomSystem.talkSystem.messagingSystem.attendeeMessengerController.userInfo);
        }
        if (roomSystem.talkSystem.messagingSystem.organizerMessengerController !=null) {
            notifyObservers(roomSystem.talkSystem.messagingSystem.organizerMessengerController.userInfo);
        }
    }

    /**
     * Sets the UserList and updates the observers.
     */

    public void setUserList(ArrayList<User> userlst) {
        this.userList = userlst;
        setChanged();
        notifyObservers(userList);
        }

    /**
     * Sets the UserScheduleMap and updates the observers.
     * @param userSchedMap a given UserScheduleMap.
     */

    public void setUserScheduleMap(HashMap<User, UserScheduleManager> userSchedMap) {
        this.userScheduleMap = userSchedMap;
        setChanged();
        notifyObservers(userScheduleMap);
        }

    /**
     * Sets the SpeakerScheduleMap and updates the observers.
     * @param speakerSchedMap a given SpeakerScheduleMap.
     */

    public void setSpeakerScheduleMap(HashMap<Speaker, SpeakerScheduleManager> speakerSchedMap) {
        this.speakerScheduleMap = speakerSchedMap;
        setChanged();
        notifyObservers(speakerSchedMap);
        }

    /**
     * Sets the MainMenuController and updates the observers.
     */

    public void setMainMenuController(){
        setChanged();
        notifyObservers(mainMenuController);
    }

    /**
     * The main run method for the entire program. Observers are initialized so that everything is running over the
     * same systems. The above set methods are called to update everything and notify all observers.
     * The system is run starting with Login, which, if successful, prompts a main menu, which the user can navigate
     * to prompt the different screens and do specific actions. The program will quit when the user logs out, and
     * update any files with possible changes taht were made during the active session.
     */

    public void run() {
        this.addObserver(roomSystem.talkSystem);
        this.addObserver(roomSystem.talkSystem.talkManager);
        this.addObserver(logInController.logInManager);
        this.addObserver(roomSystem.talkSystem.scheduleSystem);
        this.addObserver(roomSystem.talkSystem.messagingSystem);
        this.setMainMenuController();
        this.logInController.addObserver(roomSystem.talkSystem);
        this.logInController.addObserver(mainMenuController); //Added MainMenu Controller to Observers for LIC

        CSVReader file = new CSVReader("phase1/src/Resources/Users.csv");
        for(ArrayList<String> user: file.getData()){
            this.userStorage.createUser(user.get(0), user.get(1), user.get(2), user.get(3));
        }
        setUserList(this.userStorage.userList);
        setUserScheduleMap(this.userStorage.userScheduleMap);
        setSpeakerScheduleMap(this.userStorage.speakerScheduleMap);
        logInController.runLogIn();
        if (roomSystem.talkSystem.messagingSystem.speakerMessengerController !=null) {
            this.addObserver(roomSystem.talkSystem.messagingSystem.speakerMessengerController.userInfo);
        }
        if (roomSystem.talkSystem.messagingSystem.attendeeMessengerController !=null) {
            this.addObserver(roomSystem.talkSystem.messagingSystem.attendeeMessengerController.userInfo);
        }
        if (roomSystem.talkSystem.messagingSystem.organizerMessengerController !=null) {
            this.addObserver(roomSystem.talkSystem.messagingSystem.organizerMessengerController.userInfo);
        }
        if (roomSystem.talkSystem.messagingSystem.speakerMessengerController !=null) {
            setSpeakerScheduleMap(this.userStorage.speakerScheduleMap);
        }
        if (roomSystem.talkSystem.orgScheduleController !=null) {
            this.addObserver(roomSystem.talkSystem.orgScheduleController);
        }
        setUserStorage();
        setMainMenuController();
        roomSystem.run();
        mainMenuController.runMainMenu(this.logInController.user);
        }

    /**
     * Method to write the changes to the RoomFile, called in MainMenuController.logout().
     */
    public void save() {
        CSVWriter csvWriter = new CSVWriter();
        csvWriter.writeToUsers("phase1/src/Resources/Users.csv", this.userStorage.getUserList());
    }
}
