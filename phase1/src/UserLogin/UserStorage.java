package UserLogin;
import Schedule.UserScheduleManager;

import java.util.ArrayList;

/**
 * A Use Case class that handles the creation and storage of all users in the database.
 */

public class UserStorage {
    private ArrayList<User> UserList;
    private ArrayList<UserScheduleManager> UserScheduleList;

    public UserStorage() {
        this.UserList = new ArrayList<>();
        this.UserScheduleList = new ArrayList<>();

    }

    public boolean createUser(String usertype, String name, String password, String email) {
        //Create instance of user depending on usertype
        User newuser = createUserOfInstance(usertype, name, password, email);
        if (newuser == null) {
            return false;
        }
        //Add the user to the UserList
        this.UserList.add(newuser);
        //Add the user to UserScheduleList
        UserScheduleManager newuserschedulemanager = new UserScheduleManager(newuser);
        this.UserScheduleList.add(newuserschedulemanager);

        return true;

    }

    private User createUserOfInstance(String usertype, String name, String password, String email){
        User newuser = null;
        switch (usertype) {
            case "Attendee": {
                newuser = new Attendee(name, password, email);
                break;
            }
            case "Organizer": {
                newuser = new Organizer(name, password, email);
                break;
            }
            case "Speaker": {
                newuser = new Speaker(name, password, email);
                break;
            }
        }
        return newuser;
        }

    }



