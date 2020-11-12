package MessagingPresenters;

import UserLogin.User;

import java.util.ArrayList;

/**
 * A class that represents the messenger controller.
 */

public abstract class MessengerController {
    private User user;
    private CanMessageManager userInfo;

    /**
     * A user is required to create an instance of this class.
     * @param user the user
     */

    public MessengerController(User user) {
        this.user = user;
        this.userInfo = new CanMessageManager(user);
    }

    /**
     * Adds the given user to this user's friend list if and only if this user can message them.
     * @param friend the given user to be added to the friend list
     */

    public void addToFriendList(User friend) {
        if (userInfo.canMessage(friend)){
            userInfo.getFriendsList().add(friend);
        }
    }



}
