package MessagingPresenters;

import UserLogin.Organizer;
import UserLogin.User;

import java.util.ArrayList;


/**
 * A class that represents an organizer message controller.
 */

public class OrganizerMessengerController { // NOV 14: REMOVED PARENT/CHILD CLASS RELATIONSHIP BETWEEN MESSENGER CONTROLLERS; NOT ENOUGH REASON TO INHERIT - JOLIE
    private Organizer organizer;
    private CanMessageManager userInfo;

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

}
