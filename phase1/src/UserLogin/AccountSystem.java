package UserLogin;

import Schedule.ScheduleManager;
import Schedule.UserScheduleManager;

import java.util.ArrayList;
import java.util.Observable;

/**
 * A Gateway class that interacts with an external file (.csv) that stores a collection of all accounts currently
 * registered in the database.
 */

public class AccountSystem extends Observable {
    //Variables
    public UserStorage userStorage;
    private ArrayList<User> userList;
    public ArrayList<UserScheduleManager> userScheduleList;

    public AccountSystem() {
        this.userStorage = new UserStorage();
        this.userList = new ArrayList<>();
        this.userScheduleList = new ArrayList<>();

    }

    public void setUserList(ArrayList<User> userlst) {
        this.userList = userlst;
        setChanged();
        notifyObservers(userList);
    }

    public void setUserScheduleList(ArrayList<UserScheduleManager> userSchedlist) {
        this.userScheduleList = userSchedlist;
        setChanged();
        notifyObservers(userScheduleList);
    }

    public void setUserStorage(String usertype, String name, String password, String email) {
        this.userStorage.createUser(usertype, name, password, email);
        setUserList(this.userStorage.getUserList());
        setUserScheduleList(this.userStorage.getUserScheduleList());
    }

}
