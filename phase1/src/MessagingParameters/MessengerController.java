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

    /**
     * A user and a friend list is required to create an instance of this class.
     * @param user the user
     * @param friendList a list of users whom this user can message
     */

    public MessengerController(User user, ArrayList<User> friendList) {
        this.user = user;
        this.friendList = friendList;
        this.userInfo = new CanMessageManager(user);
    }

    /**
     * Adds the given user to this user's friend list if and only if this user can message them.
     * @param friend the given user to be added to the friend list
     */

    public void addToFriendList(User friend) {
        if (userInfo.canMessage(friend)){
            friendList.add(friend);
        }
    }



}
