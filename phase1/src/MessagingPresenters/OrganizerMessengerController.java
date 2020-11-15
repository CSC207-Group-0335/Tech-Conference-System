package MessagingPresenters;

import UserLogin.Organizer;
import UserLogin.User;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


/**
 * A class that represents an organizer message controller.
 */

public class OrganizerMessengerController implements Observer {
    private Organizer organizer;
    private CanMessageManager userInfo;
    private ConversationStorage conversationStorage;

    /**
     * An organizer is required to create an instance of this class.
     * @param organizer the organizer
     */

    public OrganizerMessengerController(Organizer organizer) {
        this.userInfo = new CanMessageManager(organizer);
    }

    /**
     * Adds the given user to this organizer's friend list if and only if this organizer can message them.
     * @param friend the given user to be added to the friend list
     */

    public void addToFriendList(User friend) {
        if (userInfo.canMessage(friend)){
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
