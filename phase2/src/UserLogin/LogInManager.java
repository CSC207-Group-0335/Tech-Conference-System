package UserLogin;

import java.util.Observable;
import java.util.Observer;

/**
 * A Use Case class that handles the back-end of the login process for a user attempting to login to their account.
 */

public class LogInManager implements Observer {
    public UserStorage userStorage;

    /**
     * A constructor for a LogInManager, which initializes a new UserList.
     */

    public LogInManager() {
        this.userStorage = new UserStorage();
    }

    /**
     * Public method used to login the user based on this.email and this.password.
     * @return a boolean value representing whether or not the login was successful.
     */

    public boolean login(String email, String password) {
        //find the user in UserStorage using the provided email
        User user = userStorage.emailToUser(email);
        if (user != null) {
            //A user has been found, now check the password
            return user.getPassword().equals(password);
        } else {
            return false;
        }
    }

    /**
     * A method used by the Observable Design Pattern to update variables in this Observer class based on changes made
     * in linked Observable classes. This one updates the UserList of this class.
     * @param o the Observable class where the change is made and this function is called.
     * @param arg the argument that is being updated.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof UserStorage) {
            this.userStorage = (UserStorage) arg;
        }

    }
}