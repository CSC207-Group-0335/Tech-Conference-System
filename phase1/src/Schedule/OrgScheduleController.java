package Schedule;

import UserLogin.User;

import java.util.ArrayList;

public class OrgScheduleController implements Actions{

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

    public boolean requestTalks() {
        return false;
    }
    public boolean addRoom() {

        return false;
    }

    public boolean createSpeaker() {
        return false;
    }
}
