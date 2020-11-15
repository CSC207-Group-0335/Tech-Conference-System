package MessagingPresenters;

import UserLogin.Speaker;
import UserLogin.User;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * A class that represents a speaker message controller.
 */

public class SpeakerMessengerController implements Observer {
    private CanMessageManager userInfo;
    private ConversationStorage conversationStorage;
    private Speaker speaker;

    /**
     * A speaker is required to create an instance of this class.
     *
     * @param speaker the speaker
     */

    public SpeakerMessengerController(Speaker speaker) {
        this.userInfo = new CanMessageManager(speaker);
    }

    /**
     * Adds the given user to this speaker's friend list if and only if this speaker can message them.
     *
     * @param friend the given user to be added to the friend list
     */

    public void addToFriendList(User friend) {
        if (userInfo.canMessage(friend)) {
            userInfo.getFriendsList().add(friend);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ConversationStorage) {
            this.conversationStorage = (ConversationStorage) arg;
        }
    }
}
