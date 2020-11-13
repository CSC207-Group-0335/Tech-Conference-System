package Schedule;

import UserLogin.User;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class OrgScheduleController implements Actions, Observer {

    UserScheduleManager organizer;

    public OrgScheduleController(UserScheduleManager organizer){
        this.organizer = organizer;
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

    public boolean requestTalks() {
        return false;
    }
    public boolean addRoom() {

        return false;
    }

    public boolean createSpeaker() {
        return false;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
