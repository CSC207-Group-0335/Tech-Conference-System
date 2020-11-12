package Schedule;
import UserLogin.*;
import java.util.ArrayList;

public interface Actions {
    boolean signUp(Talk talk);
    boolean cancelRegistration(Talk talk);
    ArrayList<User> allAttending();
    ArrayList<Talk> allRegistered();
}
