package MessagingPresenters;

import UserLogin.*;
import java.util.*;

/**
 * A class that manages messaging.
 */

public class OrganizerMessageManager extends MessageManager {
    /**
     * A user is needed to create an instance of OrganizerMessageManager.
     */

    public OrganizerMessageManager(String email, UserManager userManager, ConversationStorage conversationStorage) {

        super(email, userManager, conversationStorage);
    }

    /**
     * Returns a list of users that this user is allowed to message. Attendees can message all attendees and speakers,
     * organizers can message all users, and speakers can message all attendees.
     *
     * @return the list of users that user can message
     */

    public HashSet<User> getFriendsList() {
        HashSet<User> friends = new HashSet<>();
        for (int i = 0; i < userManager.userList.size(); i++) {
            if (!userManager.getUserList().get(i).getEmail().equals(user.getEmail())) {
                friends.add(userManager.getUserList().get(i));
            }
        }
        return friends;
    }

    /**
     * Messages all attendees.
     *
     * @param messageContent a String representing the content of the message
     */

    public void messageAllAttendees(String messageContent) {
        ArrayList<User> attendees = this.getAttendees();
        for (User attendee : attendees) {
            message(attendee.getEmail(), messageContent);
        }
    }

    /**
     * Messages all speakers.
     *
     * @param messageContent a String representing the content of the message
     */

    public void messageAllSpeakers(String messageContent) {
        ArrayList<User> speakers = this.getSpeakers();
        for (User speaker : speakers) {
            message(speaker.getEmail(), messageContent);
        }
    }
}
