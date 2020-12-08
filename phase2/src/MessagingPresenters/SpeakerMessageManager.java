package MessagingPresenters;

import Schedule.*;
import UserLogin.*;

import java.util.*;

public class SpeakerMessageManager extends MessageManager{
    /**
     * A user is needed to create an instance of SpeakerMessageManager.
     *
     * @param speakerEmail the email of speaker whose messages will be managed
     */

    public SpeakerMessageManager(String speakerEmail, UserManager userManager, ConversationStorage conversationStorage, EventManager eventManager) {
        super(speakerEmail, userManager, conversationStorage, eventManager);
    }


    /**
     * Returns a list of all talks the speaker will be participating in.
     *
     * @return an ArrayList containing all talks in which the speaker is a part of
     */

    public ArrayList<Event> getSpeakerTalks() {
        ArrayList<String> eventIDs = userManager.emailToTalkList(user.getEmail());
        ArrayList<Event> eventList = new ArrayList<Event>();
        for (String eventID: eventIDs){
            eventList.add(eventManager.getEvent(eventID));
        }
        return eventList;
    }

    /**
     * Returns a set of the emails of all attendees signed up for this speaker's talks.
     *
     * @return a HashSet containing Strings representing the emails of all attendees signed up for this speaker's talks
     */

    public HashSet<User> getFriendsList() {
        HashSet<User> emails = new HashSet<>();
        for (Event event : getSpeakerTalks()) {
            for (String email: event.getUsersSignedUp()){
                emails.add(userManager.emailToUser(email));
            }
        }
        return emails;
    }

    /**
     * Returns a set of the emails of all attendees signed up for this speaker's talks.
     *
     * @return a HashSet containing Strings representing the emails of all attendees signed up for this speaker's talks
     */

    public ArrayList<User> getAttendeesOfTalk(Event event) {
        ArrayList<User> emails = new ArrayList<>();
        for (String email: event.getUsersSignedUp()){
            emails.add(userManager.emailToUser(email));
        }
        return emails;
    }

    /**
     * Sends a message containing </messageContent> to all attendees.
     *
     * @param messageContent a String representing the content of the message
     */

    public void messageAllAttendees(String messageContent) {
        for (User user : getFriendsList()) {
            message(user.getEmail(), messageContent);
        }
    }

    // message attendees of one talk functions needs to be added
    public void messageAllAttendeesOfTalk(String messageContent, String talkID){
        Event event = eventManager.getEvent(talkID);
        ArrayList<User> attendees = getAttendeesOfTalk(event);
        for (User attendee: attendees){
            message(user.getEmail(), messageContent);
        }

    }
}
