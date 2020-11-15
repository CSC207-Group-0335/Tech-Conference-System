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

public class SpeakerMessengerController implements Observer {
    private CanMessageManager userInfo;
    private ConversationStorage conversationStorage;
    private Speaker speaker;
    private HashMap<Speaker, SpeakerScheduleManager> speakerScheduleManagerHashMap;

    /**
     * A speaker is required to create an instance of this class.
     *
     * @param speaker the speaker
     */

    public SpeakerMessengerController(Speaker speaker) {
        this.userInfo = new CanMessageManager(speaker);
    }


    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ConversationStorage) {
            this.conversationStorage = (ConversationStorage) arg;
        }
        else if (arg instanceof HashMap){
            this.speakerScheduleManagerHashMap = (HashMap) arg;
        }
    }
}
