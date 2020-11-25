package Schedule;

import UserLogin.User;

import java.util.ArrayList;

/**
 * Manages a User
 */
public class UserScheduleManager extends ScheduleManager {
    User user;
    /**
     * The list of talks the user is attending.
     */
    ArrayList<Event> eventList;

    /**
     * Creates a UserScheduleManager with the specified user.
     * @param user The user.
     */
    public UserScheduleManager(User user){
        this.user = user;
    }

    /**
     * Gets the user.
     * @return A user representing the user of UserScheduleManager.
     */
    public User getUser() {
        return user;
    }
}