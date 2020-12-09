package Schedule;

import UserLogin.MainMenuController;
import UserLogin.UserManager;

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
    UserManager userManager;
    ValidatorController validatorController;

    /**
     * Creates a new controller for the speaker.
     * @param speakerEmail The speaker's email.
     * @param eventManager The talKManager.
     * @param mainMenuController The mainMenuController.
     * @param scanner The scanner.
     */
    public SpeakerScheduleController(String speakerEmail, EventManager eventManager, UserManager userManager,
                                     MainMenuController mainMenuController, Scanner scanner){
        this.speakerEmail = speakerEmail;
        this.eventManager = eventManager;
        this.mainMenuController = mainMenuController;
        this.scan = scanner;
        this.userManager = userManager;
        this.presenter = new SpeakerSchedulePresenter();
        this.validatorController = new ValidatorController();
    }

    /**
     * Lists all the available actions a speaker can perform and choose from, takes their input and outputs a text UI.
     */
    //Nathan: Changed presenter to match clean architecture NOV 28.
    public void run(){
        presenter.printHelloMessage(userManager.emailToName(speakerEmail));
        boolean doContinue = true;
        while(doContinue) {
            Integer command = validatorController.userIntInputValidation("scheduling", "command",
                    scan);
            if (command == null){
                continue;
            }
            switch (command){
                case 1:
                    if (userManager.emailToTalkList(speakerEmail).size()==0){ //ask in meeting tmr
                        presenter.printNoTalks();
                    }
                    else {
                        presenter.printSchedule(userManager.emailToTalkList(speakerEmail), eventManager);
                    }
                    break;
                case 0:
                    doContinue = false;
                    mainMenuController.runMainMenu(this.speakerEmail);
                    break;

            }
        }
    }
}
