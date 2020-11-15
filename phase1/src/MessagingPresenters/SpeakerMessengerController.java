package MessagingPresenters;

import Schedule.SpeakerScheduleManager;
import UserLogin.Speaker;
import UserLogin.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * A class that represents a speaker message controller.
 */

public class SpeakerMessengerController implements Observer{
    public SpeakerMessageManager userInfo;
    private ConversationStorage conversationStorage;
    private Speaker speaker;


    /**
     * A speaker is required to create an instance of this class.
     *
     * @param speaker the speaker
     */

    public SpeakerMessengerController(Speaker speaker) {
        this.userInfo = new SpeakerMessageManager(speaker);
    }

    private void message(String email, String messageContent){
        ConversationManager c = conversationStorage.getConversationManager(speaker.getEmail(), email);
        c.addMessage(email, speaker.getEmail(), LocalDateTime.now(), messageContent);
    }

    public void messageAllAttendees(String messageContent){
        for (String email: userInfo.getAllAttendees()){
            message(email, messageContent);
        }
    }

    public boolean reply(String email, String messageContent){
        if (userInfo.canReply(email)){
            ConversationManager c = conversationStorage.getConversationManager(speaker.getEmail(), email);
            c.addMessage(email, speaker.getEmail(), LocalDateTime.now(), messageContent);
            return true;
        }
        return false;
    }


    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ConversationStorage) {
            this.conversationStorage = (ConversationStorage) arg;
        }
    }
}
