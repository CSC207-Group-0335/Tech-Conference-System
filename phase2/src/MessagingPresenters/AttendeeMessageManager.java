package MessagingPresenters;

import Schedule.SpeakerScheduleManager;
import UserLogin.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * A class that manages messaging.
 */

public class AttendeeMessageManager extends MessageManager {
    /**
     * A user is needed to create an instance of AttendeeMessageManager.
     *
     * @param user the user whose messages will be managed
     */

    public AttendeeMessageManager(String email) {
        super(email);
    }

    /**
     * Returns a list of users that this user is allowed to message. Attendees can message all attendees and speakers,
     * organizers can message all users, and speakers can message all attendees.
     *
     * @return the list of users that user can message
     */

    public HashSet<User> getFriendsList() {
        HashSet<User> friends = new HashSet<User>();
        for (int i = 0; i < allUsers.userList.size(); i++) {
            if (allUsers.getUserList().get(i) instanceof Attendee || allUsers.getUserList().get(i) instanceof Speaker) {
                if (!allUsers.getUserList().get(i).getEmail().equals(user.getEmail())) {
                    friends.add(allUsers.getUserList().get(i));
                }
            }
        }
        return friends;
    }
}
