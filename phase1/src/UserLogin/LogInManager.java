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

    /**
     * A user will login with their email and password and the LogInManager will process the login attempt.
     */

    public LogInManager() {
        //this.email = email;
        //this.password = password;
        this.usertype = null;
        this.userList = new ArrayList<User>();
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
        if (arg instanceof ArrayList) {
            this.userList = (ArrayList<User>) arg;
        }

    }
}
