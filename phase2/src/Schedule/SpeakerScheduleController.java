package Schedule;

import UserLogin.MainMenuController;
import UserLogin.UserStorage;

import java.util.Scanner;

/**
 * A controller class describing the actions an organizer can perform in the program
 */
public class SpeakerScheduleController{
    /**
     * An speaker for the conference.
     */
    String speakerEmail;
    EventManager eventManager;
    MainMenuController mainMenuController;
    Scanner scan;
    SpeakerSchedulePresenter presenter;
    UserStorage userStorage;

    /**
     * Creates a new controller for the speaker.
     * @param speakerEmail The speaker's email.
     * @param eventManager The talKManager.
     * @param mainMenuController The mainMenuController.
     * @param scanner The scanner.
     */
    public SpeakerScheduleController(String speakerEmail, EventManager eventManager, UserStorage userStorage,
                                     MainMenuController mainMenuController, Scanner scanner){
        this.speakerEmail = speakerEmail;
        this.eventManager = eventManager;
        this.mainMenuController = mainMenuController;
        this.scan = scanner;
        this.userStorage = userStorage;
        this.presenter = new SpeakerSchedulePresenter();
    }

    /**
     * Lists all the available actions a speaker can perform and choose from, takes their input and outputs a text UI.
     */
    //Nathan: Changed presenter to match clean architecture NOV 28.
    public void run(){
        presenter.printHelloMessage(userStorage.emailToName(speakerEmail));
        boolean doContinue = true;
        while(doContinue) {
            String choice = scan.nextLine();
            try {
                int command = Integer.parseInt(choice);
            if (command == 1) {
                if (userStorage.emailToTalkList(speakerEmail).size()==0){ //ask in meeting tmr
                    presenter.printNoTalks();
                }
                else {
                    presenter.printSchedule(userStorage.emailToTalkList(speakerEmail), eventManager);
                }
            }
            else if (command == 0){
                doContinue = false;
                presenter.printGoodbye();
                mainMenuController.runMainMenu(speakerEmail);
            }
            else{presenter.printTryAgain();}
            }
            catch (NumberFormatException nfe){
                presenter.printTryAgain();;}
        }
    }
}
