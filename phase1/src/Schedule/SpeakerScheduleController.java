package Schedule;

import UserLogin.MainMenuController;

import java.util.Scanner;

/**
 * A controller class describing the actions an organizer can perform in the program
 */
public class SpeakerScheduleController{
    /**
     * An speaker for the conference.
     */
    SpeakerScheduleManager speaker;
    TalkManager talkManager;
    MainMenuController mainMenuController;
    Scanner scan;
    SpeakerSchedulePresenter presenter;

    /**
     * Creates a new controller for the speaker.
     * @param speaker The speaker.
     * @param talkManager The talKManager.
     * @param mainMenuController The mainMenuController.
     * @param scanner The scanner.
     */
    public SpeakerScheduleController(SpeakerScheduleManager speaker, TalkManager talkManager,
                                     MainMenuController mainMenuController, Scanner scanner){
        this.speaker = speaker;
        this.talkManager = talkManager;
        this.mainMenuController = mainMenuController;
        this.scan = scanner;
        this.presenter = new SpeakerSchedulePresenter();
    }

    /**
     * Lists all the available actions a speaker can perform and choose from, takes their input and outputs a text UI.
     */
    public void run(){
        presenter.printHelloMessage(speaker);
        boolean doContinue = true;
        while(doContinue) {
            String choice = scan.nextLine();
            try {
                int command = Integer.parseInt(choice);
            if (command == 1) {
                if (speaker.getTalkList().size() == 0){
                    presenter.printNoTalks();
                }
                else {
                    presenter.printSchedule(speaker, talkManager);
                }
            }
            else if (command == 0){
                doContinue = false;
                presenter.printGoodbye();
                mainMenuController.runMainMenu(speaker.getSpeaker());
            }
            else{presenter.printTryAgain();}
            }
            catch (NumberFormatException nfe){
                presenter.printTryAgain();;}
        }
    }
}
