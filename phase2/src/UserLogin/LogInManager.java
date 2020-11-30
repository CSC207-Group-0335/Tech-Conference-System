package UserLogin;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * A Use Case class that handles the back-end of the login process for a user attempting to login to their account.
 */

public class LogInManager{
    public ArrayList<User> userList;

    /**
     * A constructor for a LogInManager, which initializes a new UserList.
     */

    public LogInManager(UserStorage userStorage) {
        this.userList = userStorage.getUserList();
    }

    /**
     * Method to find the user in the UserStorage associated with this.email
     * @return the user associated with the email, or null if no such user is found. Currently attempting to
     * use an Optional Parameter in order to accomplish this task, instead of a null value.
     */
    public User findUser(String email) {
        //iterate through userList and check the email associated with each user to see if there is a match.
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getEmail().equals(email)) {
                return userList.get(i); //return the user associated with this email.
            }
        }
        return null; //If we have reached the end of the list and there is no match, return null.
    }

    /**
     * Public method used to login the user based on this.email and this.password.
     * @return a boolean value representing whether or not the login was successful.
     */

    public boolean login(String email, String password) {
        //find the user in UserStorage using the provided email
        User user = findUser(email);
        if (user != null) {
            //A user has been found, now check the password
            return user.getPassword().equals(password);
        } else {
            return false;
        }
    }
}