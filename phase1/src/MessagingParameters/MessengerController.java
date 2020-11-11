package MessagingParameters;

import UserLogin.User;

import java.util.ArrayList;

/**
 * A class that represents the messenger controller.
 */

public abstract class MessengerController {
    private User user;
    private ArrayList<User> friendList;
    private CanMessageManager userInfo;

    public MessengerController(User user, ArrayList<User> friendList) {
        this.user = user;
        this.friendList = friendList;
        this.userInfo = new CanMessageManager(user);
    }

    public void addToFriendList(User friend) {
        if (userInfo.canMessage(friend)){
            friendList.add(friend);
        }
    }



}
