package MessagingParameters;

import UserLogin.Speaker;
import UserLogin.User;

import java.util.ArrayList;

/**
 * A class that represents a speaker message controller.
 */

public class SpeakerMessageController extends MessengerController{
    private Speaker user;
    private ArrayList<User> friendList;
    private CanMessageManager userInfo;

    public SpeakerMessageController(Speaker user, ArrayList<User> friendList) {
        super(user, friendList);
        this.userInfo = new CanMessageManager(user);
    }

    public void addToFriendList(User friend) {
        if (userInfo.canMessage(friend)){
            friendList.add(friend);
        }
    }
}
