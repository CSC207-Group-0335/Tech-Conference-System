package Schedule;

import UserLogin.User;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class SpeakerScheduleController implements Observer {

    SpeakerScheduleManager speaker;
    TalkManager talkManager;
    SignUpAttendeesManager signUpList;
    public SpeakerScheduleController(SpeakerScheduleManager speaker, TalkManager talkManager,
                                     SignUpAttendeesManager signUpList){
        this.speaker = speaker;
        this.talkManager = talkManager;
        this.signUpList = signUpList;
    }

    public ArrayList<Talk> allSpeakingAt() {

        return speaker.talkList;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
