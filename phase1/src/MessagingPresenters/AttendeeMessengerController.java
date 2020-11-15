package MessagingPresenters;

import Schedule.OrgScheduleController;
import Schedule.SpeakerScheduleController;
import Schedule.UserScheduleController;
import UserLogin.Attendee;
import UserLogin.User;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * A class that represents the messenger controller.
 */

public class AttendeeMessengerController implements Observer{ //NOTE, MADE NOT ABSTRACT NOV 14 EARLY MORNING - NATHAN
    private Attendee attendee;
    private CanMessageManager userInfo;
    private ConversationStorage conversationStorage;

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

    public void addToFriendList(String email) {
        if (userInfo.canMessage(email)){
            userInfo.getFriendsList().add(email);
        }
    }

    public void message(String email){
        if (userInfo.canMessage(email)){
            if (conversationStorage.contains(attendee.getEmail(), email)){

            }
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ConversationStorage) {
            this.conversationStorage = (ConversationStorage) arg;
        }
    }

}
