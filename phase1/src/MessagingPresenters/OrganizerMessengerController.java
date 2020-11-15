package MessagingPresenters;

import UserLogin.Attendee;
import UserLogin.Organizer;
import UserLogin.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


/**
 * A class that represents an organizer message controller.
 */

public class OrganizerMessengerController implements Observer {
    private Organizer organizer;
    public CanMessageManager userInfo;
    private ConversationStorage conversationStorage;

    /**
     * An organizer is required to create an instance of this class.
     * @param organizer the organizer
     */

    public OrganizerMessengerController(Organizer organizer) {
        this.userInfo = new CanMessageManager(organizer);
    }

    /**
     * Sends a message containing </messageContent> to a user registered under the email </email>.
     * @param email a String representing the email of the recipient
     * @param messageContent a String representing the content of the message
     */

    public void messageOneUser(String email, String messageContent){
        if (userInfo.canMessage(email)){
            if (conversationStorage.contains(organizer.getEmail(), email)){
                ConversationManager c = conversationStorage.getConversationManager(organizer.getEmail(), email);
                c.addMessage(email, organizer.getEmail(), LocalDateTime.now(), messageContent);
            }
            else{
                ConversationManager c = conversationStorage.addConversationManager(organizer.getEmail(), email);
                c.addMessage(email, organizer.getEmail(), LocalDateTime.now(), messageContent);
            }
        }
    }

    /**
     * Sends a message containing </messageContent> to all attendees.
     * @param messageContent a String representing the content of the message
     */

    public void messageAllAttendees(String messageContent){
        ArrayList<User> attendees = userInfo.getAttendees();
        for (User attendee: attendees){
            messageOneUser(attendee.getEmail(), messageContent);
        }
    }

    /**
     * Sends a message containing </messageContent> to all speakers.
     * @param messageContent a String representing the content of the message
     */

    public void messageAllSpeakers(String messageContent){
        ArrayList<User> speakers = userInfo.getSpeakers();
        for (User speaker: speakers){
            messageOneUser(speaker.getEmail(), messageContent);
        }
    }

    /**
     * Returns an arraylist containing all message history between this organizer and the user registered under the
     * email </email>.
     * @param email a String representing the email of the recipient
     * @return an arraylist containing all messages between this organizer and the user
     */

    public ArrayList<Message> viewMessages(String email){
        if (userInfo.canMessage(email)){
            if (conversationStorage.contains(organizer.getEmail(), email)){
                ConversationManager c = conversationStorage.getConversationManager(organizer.getEmail(), email);
                return c.getMessages();
            }
            else{
                ConversationManager c = conversationStorage.addConversationManager(organizer.getEmail(), email);
                return c.getMessages();
            }
        }
        return null;
    }

    /**
     * Updates </conversationStorage> if and only if </arg> is an instance of ConversationStorage.
     * @param o an observable parameter
     * @param arg an Object
     */

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ConversationStorage) {
            this.conversationStorage = (ConversationStorage) arg;
        }
    }

}
