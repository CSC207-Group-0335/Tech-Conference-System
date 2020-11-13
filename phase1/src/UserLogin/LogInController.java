package UserLogin;


/**
 * A Controller class that handles the front-end of the login process for a user attempting to login to their account.
 */

public class LogInController {
    //private UserStorage userStorage;
    public LogInManager logInManager;
    public User user;

    public LogInController(User user){
        this.logInManager = new LogInManager(user.getEmail(), user.getPassword());
    }
}

