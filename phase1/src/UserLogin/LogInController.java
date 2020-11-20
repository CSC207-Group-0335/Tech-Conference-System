package UserLogin;


import MessagingPresenters.MessagingSystem;
import Schedule.TalkSystem;

import java.util.Observable;
import java.util.Scanner;

/**
 * A Controller class that handles the front-end of the login process for a user attempting to login to their account.
 * Prompt the user for their credentials, and once they give it, call loginmanager to get the user entity that is
 * associated with the provided information.
 */

public class LogInController extends Observable {
    public LogInManager logInManager;
    public User user;
    public LogInPresenter presenter;
    public MainMenuController mainMenuController;
    public TalkSystem talkSystem;
    public MessagingSystem messagingSystem;
    public Scanner scanner;

    /**
     * A constructor for a LogInController
     * @param mainMenuController the MainMenuController that is instantiated with this LogInController
     * @param talkSystem the TalkSystem that interacts with is instantiated LogInController
     * @param messagingSystem the MessagingSystem that interacts with is instantiated LogInController
     */

    public LogInController(MainMenuController mainMenuController, TalkSystem talkSystem,
                           MessagingSystem messagingSystem){
        this.talkSystem = talkSystem;
        this.messagingSystem = messagingSystem;
        this.logInManager = new LogInManager();
        this.presenter = new LogInPresenter();
        this.mainMenuController = mainMenuController;
        this.scanner = new Scanner(System.in);


    }

    /**
     * A method used to run the login procedure by prompting the user for their credentials and verifying the
     * information entered to login the correct user if it exists.
     */

    public void runLogIn(){
        boolean check = true;
        while (check){

            Scanner in = scanner;
            presenter.printLoginInfo(1); //Ask for email
            String email = in.nextLine();
            presenter.printLoginInfo(2); //Ask for password
            String password = in.nextLine();

            if (this.logInManager.login(email, password)){
                check = false;
                this.user = this.logInManager.findUser(email);
                setUser(this.user); //set the user
                this.talkSystem.instantiateControllers(this.user, scanner); //Instantiate controllers for the found user
                this.talkSystem.user = user;
                this.messagingSystem.instantiateControllers(this.user, scanner);
                presenter.printLoginInfo(3); //Login Successful
            }
            else{
                presenter.printLoginInfo(4); //Something went wrong
            }
        }
    }

    /**
     * Method to notify the observers of the User that has logged in, which will alter the program depending on the
     * type of the user and teh specific users details.
     * @param user the User object that has logged in.
     */
    public void setUser(User user) {
        setChanged();
        notifyObservers(user);
    }

}

