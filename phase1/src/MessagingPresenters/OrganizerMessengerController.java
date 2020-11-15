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

    public void messageAllAttendees(String messageContent){
        ArrayList<User> attendees = userInfo.getAttendees();
        for (User attendee: attendees){
            messageOneUser(attendee.getEmail(), messageContent);
        }
    }

    public void messageAllSpeakers(String messageContent){
        ArrayList<User> speakers = userInfo.getSpeakers();
        for (User speaker: speakers){
            messageOneUser(speaker.getEmail(), messageContent);
        }
    }



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


    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ConversationStorage) {
            this.conversationStorage = (ConversationStorage) arg;
        }
    }

}
