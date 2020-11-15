package MessagingPresenters;

import Schedule.SpeakerScheduleManager;
import UserLogin.Speaker;
import UserLogin.User;

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


    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ConversationStorage) {
            this.conversationStorage = (ConversationStorage) arg;
        }
    }
}
