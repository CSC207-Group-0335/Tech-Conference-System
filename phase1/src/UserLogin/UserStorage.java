package UserLogin;
import Schedule.UserScheduleManager;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A Use Case class that handles the creation and storage of all users in the database.
 */

public class UserStorage {
    //made these public (NOV 12) - Nathan
    public ArrayList<User> UserList;
    public Array UserScheduleList; //CHANGED FOR TESTING PURPOSES

    /**
     * Each user in UserStorage has an associated instance of UserScheduleManager.
     */

    public UserStorage() {
        this.UserList = new ArrayList<>();
        //this.UserScheduleList = new ArrayList<>();

    }

    /**
     * creates a new user object and places it in UserList. Generates an associated UserScheduleManager with the new
     * user as the argument.
     * @return true if a new user is created, false otherwise.
     */

    public boolean createUser(String usertype, String name, String password, String email) {
        //Create instance of user depending on usertype
        User newuser = createUserOfInstance(usertype, name, password, email);
        if (newuser == null) {
            return false;
        }

        //NOV 12 NOTE by Nathan...We need to check that this users email is unique or else do not create a new user.
        //I could try to do this is a helper method.

        //Add the user to the UserList
        this.UserList.add(newuser);
        //Add the user to UserScheduleList
        //UserScheduleManager newuserschedulemanager = new UserScheduleManager(newuser);
        //this.UserScheduleList.add(newuserschedulemanager);

        return true;

    }

    public ArrayList<User> getUserList() {
        return UserList;
    }

   // public ArrayList<UserScheduleManager> getUserScheduleList() {
    //    return UserScheduleList;
    //}

    /**
     * Used to help create a new user object. A new user is created based on the type that is specified in the
     * usertype parameter.
     * @return a new user object (note that it could be null)
     */

    private User createUserOfInstance(String usertype, String name, String password, String email){
        User newuser = null;

        //I think here would be a good place to see if the email is valid/has not been used before.
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



