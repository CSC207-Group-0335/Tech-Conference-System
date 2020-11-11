package MessagingParameters;

import UserLogin.Organizer;
import UserLogin.User;

import java.util.ArrayList;


/**
 * A class that represents an organizer message controller.
 */

public class OrganizerMessageController extends MessengerController {
    private Organizer user;
    private ArrayList<User> friendList;
    private CanMessageManager userInfo;

    /**
     * An organizer and a friend list is required to create an instance of this class.
     * @param user the organizer
     * @param friendList a list of users whom this organizer can message
     */

    public OrganizerMessageController(Organizer user, ArrayList<User> friendList) {
        super(user, friendList);
        this.userInfo = new CanMessageManager(user);
    }

    /**
     * Adds the given user to this organizer's friend list if and only if this organizer can message them.
     * @param friend the given user to be added to the friend list
     */

    public void addToFriendList(User friend) {
        if (userInfo.canMessage(friend)){
            friendList.add(friend);
        }
    }

}
