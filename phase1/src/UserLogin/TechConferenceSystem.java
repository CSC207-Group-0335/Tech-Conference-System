package UserLogin;

import Schedule.*;

import java.sql.SQLOutput;
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
    public ArrayList<Object> observerList;

    public TechConferenceSystem() {
        this.observerList = new ArrayList<>(); //FAKE FIX
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
        //setChanged();
        //notifyObservers(userList); //Not notifying all observers for some reason...MUST FIX Nov 15
        for (Object obj: this.observerList) {
            this.addObserver((Observer) obj);
            setChanged();
            notifyObservers(userList);
        }
        for (Object obj: this.observerList) {
            this.deleteObserver((Observer) obj);
        }
    }

    public void setUserScheduleMap(HashMap<User, UserScheduleManager> userSchedMap) {
        this.userScheduleMap = userSchedMap;
        //setChanged();
        //notifyObservers(userScheduleMap);
        for (Object obj: this.observerList) {
            this.addObserver((Observer) obj);
            setChanged();
            notifyObservers(userScheduleMap);
        }
        for (Object obj: this.observerList) {
            this.deleteObserver((Observer) obj);
        }
    }

    public void setSpeakerScheduleMap(HashMap<Speaker, SpeakerScheduleManager> speakerSchedMap) {
        this.speakerScheduleMap = speakerSchedMap;
        //setChanged();
        //notifyObservers(speakerScheduleMap); //Not notifying all observers for some reason...MUST FIX Nov 15
        //FAKE FIX
        for (Object obj: this.observerList) {
            this.addObserver((Observer) obj);
            setChanged();
            notifyObservers(speakerSchedMap);
        }
    }

    public void setMainMenuController(){
        setChanged();
        notifyObservers(mainMenuController);
    }

    public void run() {
        //Added all Observers NOV 15
        //this.addObserver(roomSystem.talkSystem.talkManager); FAKE FIX
        this.observerList.add(roomSystem.talkSystem.talkManager); //Fake fix
        //this.addObserver(logInController.logInManager); FAKE FIX
        this.observerList.add(logInController.logInManager); //Fake fix
        System.out.println(observerList);
        //System.out.println(this.countObservers());
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
        setUserStorage();
        setMainMenuController();
        this.addObserver(roomSystem.talkSystem.orgScheduleController);
        roomSystem.run();
        mainMenuController.runMainMenu(this.logInController.user);
        }


    public void saveUserImage(){
        new UsersCSVWriter("phase1/src/Resources/Users.csv",this.userList);

    }
}
