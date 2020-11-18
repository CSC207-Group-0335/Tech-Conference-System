package Schedule;

import UserLogin.MainMenuController;
import UserLogin.Speaker;
import UserLogin.User;
import sun.security.tools.keytool.Main;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class SpeakerScheduleController{

    SpeakerScheduleManager speaker;
    TalkManager talkManager;
    MainMenuController mainMenuController;
    Scanner scan;
    SpeakerSchedulePresenter presenter;

    public SpeakerScheduleController(SpeakerScheduleManager speaker, TalkManager talkManager,
                                     MainMenuController mainMenuController, Scanner scanner){
        this.speaker = speaker;
        this.talkManager = talkManager;
        this.mainMenuController = mainMenuController;
        this.scan = scanner;
        this.presenter = new SpeakerSchedulePresenter();
    }


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
