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

    public SpeakerScheduleController(SpeakerScheduleManager speaker, TalkManager talkManager,
                                     MainMenuController mainMenuController){
        this.speaker = speaker;
        this.talkManager = talkManager;
        this.mainMenuController = mainMenuController;
    }

    public void run(){
        SpeakerSchedulePresenter presenter = new SpeakerSchedulePresenter();
        presenter.printHelloMessage(speaker);
        Scanner scan = new Scanner(System.in);
        boolean doContinue = true;
        while(doContinue) {
            int command = scan.nextInt();
            //if they want to register for a talk
            if (command == 1) {
                presenter.printSchedule(speaker, talkManager);
            }
            if (command == 0){
                doContinue = false;
                mainMenuController.runMainMenu(speaker.getSpeaker());
            }
        }
    }
}
