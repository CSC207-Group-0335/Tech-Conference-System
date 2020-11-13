package UserLogin;

import Schedule.UserScheduleManager;

import java.lang.reflect.Array;
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
    public ArrayList<UserScheduleManager> userScheduleManagerList;

    /**
     * A user will login with their email and password and the LogInManager will process the login attempt.
     */

    public LogInManager() {
        //this.email = email;
        //this.password = password;
        this.usertype = null;
        this.userList = new ArrayList<User>();
        this.userScheduleManagerList = new ArrayList<UserScheduleManager>();
    }

    /**
     * Based on the given email, look into the database for the associated User object.
     * @return the User object associated with that email.
     */
    //public User findUser(String email) {
    //}

    //public boolean login(User user) {
        //Update the UserList
        //check if the user is in UserStorage
        //return userStorage.UserList.contains(user);
   // }


    @Override
    public void update(Observable o, Object arg) {
        //First, check if the argument is empty
        //If the argument is empty, then it is the first User object that is being put in the UserStorage
        ArrayList argum = (ArrayList) arg;
        if (arg == null) {
            this.userList = (ArrayList<User>) arg;
        }
        else {
            if (argum[0].isInstanceOf(User)) {
                this.userList = (ArrayList<User>) arg;
            }
            else if (arg instanceof ArrayList<UserScheduleManager>) {
                this.userScheduleManagerList = (ArrayList<UserScheduleManager>) arg;
            }
        }


    }
}
