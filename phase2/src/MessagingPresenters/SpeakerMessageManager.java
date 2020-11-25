package MessagingPresenters;

import Schedule.Event;
import Schedule.SignUpAttendeesManager;
import Schedule.SpeakerScheduleManager;
import Schedule.TalkSystem;
import UserLogin.*;

import java.util.*;

public class SpeakerMessageManager extends MessageManager implements Observer {
    private HashMap<Speaker, SpeakerScheduleManager> speakerScheduleManagerHashMap;
    private HashMap<Event, SignUpAttendeesManager> signUpMap;

    /**
     * A user is needed to create an instance of SpeakerMessageManager.
     *
     * @param speakerEmail the email of speaker whose messages will be managed
     */

    public SpeakerMessageManager(String speakerEmail) {
        super(speakerEmail);
    }


    /**
     * Returns a list of all talks the speaker will be participating in.
     *
     * @return an ArrayList containing all talks in which the speaker is a part of
     */

    public ArrayList<Event> getSpeakerTalks() {
        return speakerScheduleManagerHashMap.get((Speaker) user).getTalkList();
    }

    /**
     * Returns a set of the emails of all attendees signed up for this speaker's talks.
     *
     * @return a HashSet containing Strings representing the emails of all attendees signed up for this speaker's talks
     */

    public HashSet<User> getFriendsList() {
        HashSet<User> emails = new HashSet<>();
        for (Event event : getSpeakerTalks()) {
            emails.addAll(signUpMap.get(event).userList);
        }
        return emails;
    }

    /**
     * Returns a set of the emails of all attendees signed up for this speaker's talks.
     *
     * @return a HashSet containing Strings representing the emails of all attendees signed up for this speaker's talks
     */

    public ArrayList<User> getAttendeesOfTalk(Event event) {
        return new ArrayList<>(signUpMap.get(event).userList);
    }


    /**
     * Sends a message containing </messageContent> to all attendees.
     *
     * @param messageContent a String representing the content of the message
     */

    public void messageAllAttendees(String messageContent) {
        for (User user : getFriendsList()) {
            messageOne(user.getEmail(), messageContent);
        }
    }

    /**
     * Updates </allUsers> if and only if </arg> is an instance of UserStorage, </signUpMap> if and only if </arg> is
     * an instance of TalkSystem, </speakerScheduleManagerHashMap> if </arg> is an instance of HashMap, and
     * </conversationStorage> if and only if </arg> is an instance of ConversationStorage.
     *
     * @param o   an observable parameter
     * @param arg an Object
     */

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof UserStorage) {
            this.userStorage = (UserStorage) arg;
        } else if (arg instanceof HashMap) {
            if (o instanceof TalkSystem) {
                this.signUpMap = (HashMap) arg;
            } else {
                this.speakerScheduleManagerHashMap = (HashMap) arg;
            }
        } else if (arg instanceof ConversationStorage) {
            this.conversationStorage = (ConversationStorage) arg;
        }
    }
}
