package Schedule;

import Schedule.ScheduleManager;
import Schedule.Talk;
import UserLogin.User;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;

public class UserScheduleManager extends ScheduleManager {
    User user;
    ArrayList<Talk> talkList;

    public UserScheduleManager(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}