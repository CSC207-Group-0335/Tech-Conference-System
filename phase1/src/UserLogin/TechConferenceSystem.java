package UserLogin;

import Schedule.*;

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
    public HashMap<Speaker, SpeakerScheduleManager> speakerScheduleMap;
    public LogInController logInController;
    public RoomSystem roomSystem;
    public MainMenuController mainMenuController;

    public TechConferenceSystem() {
        this.userStorage = new UserStorage();
        this.userList = new ArrayList<>();
        this.userScheduleMap = new HashMap<User, UserScheduleManager>();
        this.mainMenuController = new MainMenuController();
        this.roomSystem = new RoomSystem();
        this.logInController = new LogInController(this.mainMenuController, this.roomSystem.talkSystem,
                this.roomSystem.talkSystem.messagingSystem);
    }

    public void setUserStorage(){
        setChanged();
        notifyObservers(userStorage);
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

    public void SetMainMenuController(){
        setChanged();
        notifyObservers(mainMenuController);
    }

    public void run() {
        //Added all Observers NOV 15
        this.addObserver(logInController.logInManager);
        this.addObserver(roomSystem.talkSystem.talkManager);
        this.logInController.addObserver(roomSystem);
        this.logInController.addObserver(roomSystem.talkSystem);
        this.logInController.addObserver(mainMenuController); //Added MainMenu Controller to Observers for LIC

        CSVReader file = new CSVReader("phase1/src/Resources/Users.csv"); //Changed to CSV reader Nov 15
        for(ArrayList<String> user: file.getData()){
            this.userStorage.createUser(user.get(0), user.get(1), user.get(2), user.get(3));
        }
        setUserList(this.userStorage.userList);
        setUserScheduleMap(this.userStorage.userScheduleMap);
        setSpeakerScheduleMap(this.userStorage.speakerScheduleMap);


        logInController.runLogIn();
        this.addObserver(roomSystem.talkSystem.orgScheduleController);
        roomSystem.run();
        mainMenuController.runMainMenu(this.logInController.user);
        }


    public void saveUserImage(){
        new UsersCSVWriter("phase1/src/Resources/Users.csv",this.userList);

    }

    public void setUserList(){
        setChanged();
        notifyObservers(this.userList);
    }
}
