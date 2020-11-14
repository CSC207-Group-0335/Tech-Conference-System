package UserLogin;


import java.util.Observable;
import java.util.Scanner;

/**
 * A Controller class that handles the front-end of the login process for a user attempting to login to their account.
 * Prompt the user for their credentials, and once they give it, call loginmanager to get the user entity that is
 * associated with the provided information.
 */

public class LogInController extends Observable {
    //private UserStorage userStorage;
    public LogInManager logInManager;
    public User user;

    public LogInController(){
        //we need a method like set-user that returns a user object based on the info entered and then we instantiate
        //the user parameter with this User object. Since LogInController is now Observable, we need to set observers
        //that will rely on the information that is provided in this User (ex. UserScheduleManager would need to know
        //the type of the User to properly display the correct type of schedule) - Nathan, NOV 13
        //this.logInManager = new LogInManager(user.getEmail(), user.getPassword());
        this.logInManager = null;

    }
    public boolean runLogIn(){
        boolean check = true;
        while (check){

            Scanner in = new Scanner(System.in);
            System.out.println("Enter you email: ");
            String email = in.nextLine();
            System.out.println("Enter your password: ");
            String password = in.nextLine();
            in.close();

            this.logInManager = new LogInManager(email, password);
            if (this.logInManager.login()){

            }


        }




    }
}

