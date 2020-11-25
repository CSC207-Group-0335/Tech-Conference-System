package MessagingPresenters;
import Schedule.SpeakerScheduleManager;
import UserLogin.*;

import java.time.LocalDateTime;
import java.util.*;

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

    public HashSet<User> getFriendsList() {
        HashSet<User> friends = new HashSet<User>();
        for (int i = 0; i < allUsers.userList.size(); i++){
            if (!allUsers.getUserList().get(i).getEmail().equals(user.getEmail())) {
                friends.add(allUsers.getUserList().get(i));
            }
        }
        return friends;
    }

    public void messageAllAttendees(String messageContent){
        ArrayList<User> attendees = this.getAttendees();
        for (User attendee: attendees){
            messageOne(attendee.getEmail(), messageContent);
        }
    }

    public void messageAllSpeakers(String messageContent){
        ArrayList<User> speakers = this.getSpeakers();
        for (User speaker: speakers){
            messageOne(speaker.getEmail(), messageContent);
        }
    }
}
