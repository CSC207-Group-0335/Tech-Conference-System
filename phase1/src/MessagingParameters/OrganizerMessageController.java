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

    public OrganizerMessageController(Organizer user, ArrayList<User> friendList) {
        super(user, friendList);
        this.userInfo = new CanMessageManager(user);
    }

    public void addToFriendList(User friend) {
        if (userInfo.canMessage(friend)){
            friendList.add(friend);
        }
    }

}
