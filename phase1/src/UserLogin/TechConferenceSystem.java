package UserLogin;

import MessagingPresenters.CanMessageManager;
import Schedule.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * A Gateway class that interacts with an external file (.csv) that stores a collection of all accounts currently
 * registered in the database.
 */

public class TechConferenceSystem extends Observable {
    //Variables
    public UserStorage userStorage;
    public ArrayList<User> userList;
    public HashMap<User, UserScheduleManager> userScheduleMap;
    public  HashMap<Speaker, SpeakerScheduleManager> speakerScheduleMap;
    public LogInController logInController;
    public RoomSystem roomSystem;
    public MainMenuController mainMenuController;

    public TechConferenceSystem() {
        this.userStorage = new UserStorage();
        this.userList = new ArrayList<>();
        this.userScheduleMap = new HashMap<User, UserScheduleManager>();
        this.logInController = new LogInController();
        this.addObserver(logInController.logInManager);
        this.roomSystem = new RoomSystem();
        this.addObserver(roomSystem.talkSystem.talkManager);
        this.logInController.addObserver(roomSystem.talkSystem);
        this.mainMenuController = new MainMenuController();
        this.logInController.addObserver(mainMenuController); //Added MainMenu Controller to Observers for LIC
        this.addObserver(roomSystem.talkSystem.messagingSystem.attendeeMessengerController.userInfo);
        this.addObserver(roomSystem.talkSystem.messagingSystem.organizerMessengerController.userInfo);
        this.addObserver(roomSystem.talkSystem.messagingSystem.speakerMessengerController.userInfo);
    }

    public void setUserList(ArrayList<User> userlst) {
        this.userList = userlst;
        setChanged();
        notifyObservers(userList);
    }

    public void setUserScheduleMap(HashMap<User, UserScheduleManager> userSchedMap) {
        this.userScheduleMap = userSchedMap;
        setChanged();
        notifyObservers(userScheduleMap);
    }

    public void setSpeakerScheduleMap(HashMap<Speaker, SpeakerScheduleManager> speakerSchedMap) {
        this.speakerScheduleMap = speakerSchedMap;
        setChanged();
        notifyObservers(speakerScheduleMap);
    }

    //Edit this method to read from .csv file and creates an updated version of UserStorage
    //public void setUserStorage(String usertype, String name, String password, String email) {
        //this.userStorage.createUser(usertype, name, password, email);
        //setUserList(this.userStorage.getUserList());
        //setUserScheduleMap(this.userStorage.getUserScheduleMap());
    //}
    public void run() {
        UsersCSVReader file = new UsersCSVReader("Users.csv");
        for(ArrayList<String> user: file.getData()){
            this.userStorage.createUser(user.get(0), user.get(1), user.get(2), user.get(3));
        }
        setUserList(this.userStorage.userList);
        setUserList();
        setUserScheduleMap(this.userStorage.userScheduleMap);
        setSpeakerScheduleMap(this.userStorage.speakerScheduleMap);
        }


    public void saveUserImage(){
        new UsersCSVWriter("Users.csv",this.userList);

    }

    public void setUserList(){
        setChanged();
        notifyObservers(this.userList);
    }
}
