package Schedule;

import UserLogin.User;

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