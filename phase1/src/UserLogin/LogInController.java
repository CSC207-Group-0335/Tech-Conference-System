package UserLogin;


import MessagingPresenters.MessagingSystem;
import Schedule.TalkSystem;

import java.util.Observable;
import java.util.Observer;
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
    public LogInPresenter presenter;
    public MainMenuController mainMenuController; //Daniel: added a parameter for Mainmenu and call its run method from run login
    public TalkSystem talkSystem;
    public MessagingSystem messagingSystem;
    public Scanner scanner;

    public LogInController(MainMenuController mainMenuController, TalkSystem talkSystem,
                           MessagingSystem messagingSystem){
        this.talkSystem = talkSystem;
        this.messagingSystem = messagingSystem;
        this.logInManager = new LogInManager();
        this.presenter = new LogInPresenter();
        this.mainMenuController = mainMenuController;
        this.scanner = new Scanner(System.in);


    }
    //Made method void NOV 15
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
                this.talkSystem.instantiateControllers(this.user); //Instantiate controllers for the found user
                this.messagingSystem.instantiateControllers(this.user);
                presenter.printLoginInfo(3); //Login Successful
            }
            else{
                presenter.printLoginInfo(4); //Something went wrong
            }
        }
    }

    //Method to notify the observers of the user that has logged in
    public void setUser(User user) {
        setChanged();
        notifyObservers(user);
    }

}

