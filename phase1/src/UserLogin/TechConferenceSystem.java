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
    public Map<User, UserScheduleManager> userScheduleMap;
    public  Map<Speaker, SpeakerScheduleManager> speakerScheduleMap;
    public LogInController logInController;
    public RoomSystem roomSystem;

    public TechConferenceSystem() {
        this.userStorage = new UserStorage();
        this.userList = new ArrayList<>();
        this.userScheduleMap = new Map<User, UserScheduleManager>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public UserScheduleManager get(Object key) {
                return null;
            }

            @Override
            public UserScheduleManager put(User key, UserScheduleManager value) {
                return null;
            }

            @Override
            public UserScheduleManager remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends User, ? extends UserScheduleManager> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<User> keySet() {
                return null;
            }

            @Override
            public Collection<UserScheduleManager> values() {
                return null;
            }

            @Override
            public Set<Entry<User, UserScheduleManager>> entrySet() {
                return null;
            }

            @Override
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }
        };
        this.logInController = new LogInController(); //NOV 13, CHANGED LogInController
        this.addObserver(logInController.logInManager);
        this.roomSystem = new RoomSystem();
        this.addObserver(roomSystem.talkSystem.talkManager);
    }

    public void setUserList(ArrayList<User> userlst) {
        this.userList = userlst;
        setChanged();
        notifyObservers(userList);
    }

    public void setUserScheduleMap(Map<User, UserScheduleManager> userSchedMap) {
        this.userScheduleMap = userSchedMap;
        setChanged();
        notifyObservers(userScheduleMap);
    }

    public void setSpeakerScheduleMap(Map<Speaker, SpeakerScheduleManager> speakerSchedMap) {
        this.speakerScheduleMap = speakerSchedMap;
        setChanged();
        notifyObservers(speakerScheduleMap);
    }

    //Edit this method to read from .csv file and creates an updated version of UserStorage
    public void setUserStorage(String usertype, String name, String password, String email) {
        this.userStorage.createUser(usertype, name, password, email);
        setUserList(this.userStorage.getUserList());
        setUserScheduleMap(this.userStorage.getUserScheduleMap());
    }

}
