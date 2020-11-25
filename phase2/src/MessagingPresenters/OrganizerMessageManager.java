package MessagingPresenters;
import Schedule.SpeakerScheduleManager;
import UserLogin.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * A class that manages messaging.
 */

public class OrganizerMessageManager extends MessageManager {
    /**
     * A user is needed to create an instance of OrganizerMessageManager.
     * @param user the user whose messages will be managed
     */

    public OrganizerMessageManager(String email) {
        super(email);
    }

    /**
     * Returns a list of users that this user is allowed to message. Attendees can message all attendees and speakers,
     * organizers can message all users, and speakers can message all attendees.
     * @return the list of users that user can message
     */

    public ArrayList<User> getFriendsList() {
        ArrayList<User> friends = new ArrayList<User>();
        for (int i = 0; i < allUsers.userList.size(); i++){
            if (!allUsers.getUserList().get(i).getEmail().equals(user.getEmail())) {
                friends.add(allUsers.getUserList().get(i));
            }
        }
        return friends;
    }
}
