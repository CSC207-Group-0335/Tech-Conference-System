package Schedule;

import Schedule.ScheduleManager;
import Schedule.Talk;
import UserLogin.User;

import java.util.ArrayList;

public class UserScheduleManager extends ScheduleManager {
    User user;
    ArrayList<Talk> talkList;

    public UserScheduleManager(User speaker){
        this.user = speaker;
    }

    public Object getUser() {
        return user;
    }
}