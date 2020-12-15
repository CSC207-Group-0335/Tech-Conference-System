package UserLogin;

import MessagingPresenters.MessagingSystem;
import Schedule.EventSystem;

import java.util.Observable;
import java.util.Scanner;

/**
 * A Controller class that handles the front-end of the login process for a user attempting to login to their account.
 * Prompt the user for their credentials, and once they give it, call loginmanager to get the user entity that is
 * associated with the provided information.
 */

public class LogInController extends Observable {
    public LogInManager logInManager;
    private String email;
    public LogInPresenter presenter;
    public MainMenuController mainMenuController;
    public EventSystem eventSystem;
    public MessagingSystem messagingSystem;
    public Scanner scanner;

    /**
     * A constructor for a LogInController
     * @param mainMenuController the MainMenuController that is instantiated with this LogInController
     * @param eventSystem the EventSystem that interacts with is instantiated LogInController
     * @param messagingSystem the MessagingSystem that interacts with is instantiated LogInController
     */

    public LogInController(MainMenuController mainMenuController, EventSystem eventSystem,
                           MessagingSystem messagingSystem, UserManager userManager){
        this.scanner = new Scanner(System.in);
        this.eventSystem = eventSystem;
        this.messagingSystem = messagingSystem;
        this.logInManager = new LogInManager(userManager);
        this.presenter = new LogInPresenter();
        this.mainMenuController = mainMenuController;
        mainMenuController.setScanner(scanner);
        mainMenuController.setLogInController(this);
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
                this.email = email;



                this.messagingSystem.setEmail(this.email);
                this.eventSystem.instantiateControllers(this.email, scanner); //Instantiate controllers for the found user
                this.messagingSystem.instantiateControllers(this.email, scanner);
                presenter.printLoginInfo(3); //Login Successful
            }
            else{
                presenter.printLoginInfo(4); //Something went wrong
            }
        }
    }

    /**
     * Getter for the users email
     * @return the email parameter
     */

    public String getEmail() {
        return this.email;
    }
}

