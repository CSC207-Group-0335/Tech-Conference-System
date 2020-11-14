package UserLogin;

import Schedule.UserScheduleManager;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

/**
 * A Use Case class that handles the back-end of the login process for a user attempting to login to their account.
 */

public class LogInManager implements Observer {
    private String email;
    private String password;
    private User usertype;
    public ArrayList<User> userList;

    /**
     * A user will login with their email and password and the LogInManager will process the login attempt.
     */

    public LogInManager(String email, String password) {
        this.email = email;
        this.password = password;
        this.usertype = null;
        this.userList = new ArrayList<User>();
    }

    /**
     * Method to find the user in the UserStorage associated with this.email
     * @return the user associated with the email, or null if no such user is found. Currently attempting to
     * use an Optional Parameter in order to accomplish this task, instead of a null value.
     */
    public User findUser() {
        //iterate through userList and check the email associated with each user to see if there is a match.
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getEmail().equals(this.email)) {
                return userList.get(i); //return the user associated with this email.
            }
        }
        return null; //If we have reached the end of the list and there is no match, return null.
    }

    /**
     * Public method used to login the user based on this.email and this.password.
     * @return a boolean value representing whether or not the login was successful.
     */

    public boolean login() {
        //find the user in UserStorage using the provided email
        User user = findUser();
        if (user != null) {
            //A user has been found, now check the password

            //NOTE should we return a string that says "Incorrect password" vs "User not found" in order to
            //differentiate the problem if the result is false?

            return user.getPassword().equals(this.password);
        } else {
            return false;
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        //check the type of arg (if it is a map, we do not update it here since it is referring to the
        // UserScheduleMap/SpeakerScheduleMap).
        if (arg instanceof ArrayList) {
            //We know that arg refers to the UserList
            this.userList = (ArrayList<User>) arg;
        }

    }
}