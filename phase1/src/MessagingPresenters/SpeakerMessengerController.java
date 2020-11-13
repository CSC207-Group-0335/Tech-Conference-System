package MessagingPresenters;

import UserLogin.Speaker;
import UserLogin.User;

import java.util.ArrayList;

/**
 * A class that represents a speaker message controller.
 */

public class SpeakerMessengerController extends MessengerController {
    private CanMessageManager userInfo;

    /**
     * A speaker is required to create an instance of this class.
     * @param user the speaker
     */

    public SpeakerMessengerController(Speaker user) {
        super(user);
        this.userInfo = new CanMessageManager(user);
    }

    /**
     * Adds the given user to this speaker's friend list if and only if this speaker can message them.
     * @param friend the given user to be added to the friend list
     */

    public void addToFriendList(User friend) {
        if (userInfo.canMessage(friend)){
            userInfo.getFriendsList().add(friend);
        }
    }
}
