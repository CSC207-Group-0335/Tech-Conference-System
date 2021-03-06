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
     * Returns a list of all events the speaker will be participating in.
     *
     * @return an ArrayList containing all events in which the speaker is a part of
     */

    public ArrayList<Event> getSpeakerEvents() {
        ArrayList<String> eventIDs = userManager.emailToEventList(user.getEmail());
        ArrayList<Event> eventList = new ArrayList<Event>();
        for (String eventID: eventIDs){
            eventList.add(eventManager.getEvent(eventID));
        }
        return eventList;
    }

    /**
     * Returns a set of the emails of all attendees signed up for this speaker's events.
     *
     * @return a HashSet containing Strings representing the emails of all attendees signed up for this speaker's events
     */

    public HashSet<User> getFriendsList() {
        HashSet<User> emails = new HashSet<>();
        for (Event event : getSpeakerEvents()) {
            for (String email: event.getUsersSignedUp()){
                emails.add(userManager.emailToUser(email));
            }
        }

        for (ConversationManager c: conversationStorage.getConversationManagers()){
            if (c.getParticipants().contains(user.getEmail())){
                if (c.getParticipants().get(0).equals(user.getEmail())){
                    emails.add(userManager.emailToUser(c.getParticipants().get(1)));
                }else{
                    emails.add(userManager.emailToUser(c.getParticipants().get(0)));
                }
            }
        }
        return emails;
    }

    /**
     * Returns a set of the emails of all attendees signed up for this speaker's events.
     *
     * @return a HashSet containing Strings representing the emails of all attendees signed up for this speaker's events
     */

    public HashSet<User> getAttendeesList() {
        HashSet<User> emails = new HashSet<>();
        for (Event event : getSpeakerEvents()) {
            for (String email: event.getUsersSignedUp()){
                emails.add(userManager.emailToUser(email));
            }
        }
        return emails;
    }

    /**
     * Returns a set of the emails of all attendees signed up for this speaker's events.
     *
     * @return a HashSet containing Strings representing the emails of all attendees signed up for this speaker's events
     */

    public ArrayList<User> getAttendeesOfEvent(Event event) {
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
        for (User user : getAttendeesList()) {
            message(user.getEmail(), messageContent, true);
        }
    }
}
