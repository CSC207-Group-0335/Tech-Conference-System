package MessagingPresenters;

import UserLogin.Organizer;
import UserLogin.User;

import java.util.ArrayList;


/**
 * A class that represents an organizer message controller.
 */

public class OrganizerMessengerController extends MessengerController {
    private CanMessageManager userInfo;

    /**
     * An organizer is required to create an instance of this class.
     * @param user the organizer
     */

    public OrganizerMessengerController(Organizer user) {
        super(user);
        this.userInfo = new CanMessageManager(user);
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

}
