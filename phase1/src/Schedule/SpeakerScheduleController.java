package Schedule;

import UserLogin.User;

import java.util.ArrayList;

public class SpeakerScheduleController implements Actions{

    SpeakerScheduleManager speaker;
    public SpeakerScheduleController(SpeakerScheduleManager speaker){
        this.speaker = speaker;
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
}
