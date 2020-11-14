package MessagingPresenters;

import UserLogin.Attendee;
import UserLogin.User;

import java.util.ArrayList;

/**
 * A class that represents the messenger controller.
 */

public class AttendeeMessengerController { //NOTE, MADE NOT ABSTRACT NOV 14 EARLY MORNING - NATHAN
    private Attendee attendee;
    private CanMessageManager userInfo;

    /**
     * A user is required to create an instance of this class.
     * @param attendee the attendee
     */

    public AttendeeMessengerController(Attendee attendee) {
        this.attendee = attendee;
        this.userInfo = new CanMessageManager(attendee);
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
