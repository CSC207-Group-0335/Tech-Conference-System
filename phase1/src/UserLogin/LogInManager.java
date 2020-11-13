package UserLogin;

import Schedule.UserScheduleManager;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * A Use Case class that handles the back-end of the login process for a user attempting to login to their account.
 */

public class LogInManager implements Observer {
    //Variables
    private String email;
    private String password;
    private User usertype;
    public ArrayList<User> userList;
    //public ArrayList<UserScheduleManager> userScheduleManagerList;

    /**
     * A user will login with their email and password and the LogInManager will process the login attempt.
     */

    public LogInManager() {
        //this.email = email;
        //this.password = password;
        this.usertype = null;
        this.userList = new ArrayList<User>();
        //this.userScheduleManagerList = new ArrayList<UserScheduleManager>();
    }

    //public User findUser(String email) {
    //}

    //public boolean login(User user) {
        //Update the UserList
        //check if the user is in UserStorage
        //return userStorage.UserList.contains(user);
   // }


    @Override
    public void update(Observable o, Object arg) {
        //check the type of arg (if it is a map, we do not update it here since it is referring to the
        // UserScheduleMap/SpeakerScheduleMap.
        if (arg instanceof ArrayList) {
            //We know that arg refers to the UserList
            this.userList = (ArrayList<User>) arg;
        }

    }
}
