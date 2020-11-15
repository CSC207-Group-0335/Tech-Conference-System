package MessagingPresenters;

import Schedule.OrgScheduleController;
import Schedule.SpeakerScheduleController;
import Schedule.UserScheduleController;
import UserLogin.Attendee;
import UserLogin.User;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * A class that represents the messenger controller.
 */

public class AttendeeMessengerController implements Observer{ //NOTE, MADE NOT ABSTRACT NOV 14 EARLY MORNING - NATHAN
    private Attendee attendee;
    public CanMessageManager userInfo;
    private ConversationStorage conversationStorage;

    /**
     * A user is required to create an instance of this class.
     * @param attendee the attendee
     */

    public AttendeeMessengerController(Attendee attendee) {
        this.attendee = attendee;
        this.userInfo = new CanMessageManager(attendee);
    }


    public void message(String email, String messageContent){
        if (userInfo.canMessage(email)){
            if (conversationStorage.contains(attendee.getEmail(), email)){
                ConversationManager c = conversationStorage.getConversationManager(attendee.getEmail(), email);
                c.addMessage(email, attendee.getEmail(), LocalDateTime.now(), messageContent);
            }
            else{
                ConversationManager c = conversationStorage.addConversationManager(attendee.getEmail(), email);
                c.addMessage(email, attendee.getEmail(), LocalDateTime.now(), messageContent);
            }
        }
    }

    public ArrayList<Message> viewMessages(String email){
        if (userInfo.canMessage(email)){
            if (conversationStorage.contains(attendee.getEmail(), email)){
                ConversationManager c = conversationStorage.getConversationManager(attendee.getEmail(), email);
                return c.getMessages();
            }
            else{
                ConversationManager c = conversationStorage.addConversationManager(attendee.getEmail(), email);
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
