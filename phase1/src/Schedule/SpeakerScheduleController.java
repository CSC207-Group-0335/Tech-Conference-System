package Schedule;

import UserLogin.MainMenuController;
import UserLogin.User;
import sun.security.tools.keytool.Main;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class SpeakerScheduleController implements Observer {

    SpeakerScheduleManager speaker;
    TalkManager talkManager;
    SignUpAttendeesManager signUpList;
    MainMenuController mainMenuController;

    public SpeakerScheduleController(SpeakerScheduleManager speaker, TalkManager talkManager,
                                     MainMenuController mainMenuController){
        this.speaker = speaker;
        this.talkManager = talkManager;
        this.mainMenuController = mainMenuController;
    }

    public ArrayList<Talk> allSpeakingAt() {
        return speaker.talkList;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
