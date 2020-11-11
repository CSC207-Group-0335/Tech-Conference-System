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

    /**
     * A speaker and a friend list is required to create an instance of this class.
     * @param user the speaker
     * @param friendList a list of users whom this speaker can message
     */

    public SpeakerMessageController(Speaker user, ArrayList<User> friendList) {
        super(user, friendList);
        this.userInfo = new CanMessageManager(user);
    }

    /**
     * Adds the given user to this speaker's friend list if and only if this speaker can message them.
     * @param friend the given user to be added to the friend list
     */

    public void addToFriendList(User friend) {
        if (userInfo.canMessage(friend)){
            friendList.add(friend);
        }
    }
}
