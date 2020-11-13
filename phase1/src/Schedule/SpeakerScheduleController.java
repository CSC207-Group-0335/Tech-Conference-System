package Schedule;

import UserLogin.User;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class SpeakerScheduleController implements Actions, Observer {

    SpeakerScheduleManager speaker;
    TalkManager talkManager;
    SignUpAttendeesManager signUpList;
    public SpeakerScheduleController(SpeakerScheduleManager speaker, TalkManager talkManager,
                                     SignUpAttendeesManager signUpList){

        this.speaker = speaker;
        this.talkManager = talkManager;
        this.signUpList = signUpList;
    }

    @Override
    public boolean signUp(Talk talk) {
        return false;
    }

    @Override
    public boolean cancelRegistration(Talk talk) {
        return false;
    }

    @Override
    public ArrayList<User> allAttending() {
        return null;
    }

    @Override
    public ArrayList<Talk> allRegistered() {
        return null;
    }
    public ArrayList<Talk> allSpeaking() {
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
