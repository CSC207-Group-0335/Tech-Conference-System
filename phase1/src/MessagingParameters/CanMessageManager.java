package MessagingParameters;
import UserLogin.Attendee;
import UserLogin.Organizer;
import UserLogin.Speaker;
import UserLogin.User;
import java.util.ArrayList;

/**
 * A class that manages who a user can or cannot message.
 */

public class CanMessageManager {
    private User user;
    private ArrayList<User> friendsList;

    /**
     * A user is needed to create an instance of CanMessageManager.
     * @param user the user whose messages will be managed
     */

    public CanMessageManager(User user) {
        this.user = user;
    }

    /**
     * Returns a list of users that this user is allowed to message.
     * @return the list of users that user can message
     */

    public ArrayList<User> getFriendsList() {
        if (user instanceof Organizer) {

        }
        else if (user instanceof Attendee) {

        }
        else {

        }
        return friendsList;
    }

    /**
     * Returns true if and only if this user is able to message </friend>.
     * @param friend the other user who this user can or cannot message
     * @return a boolean representing whether or not this user can message </friend>
     */

    public boolean canMessage(User friend) {
        if (this.user instanceof Attendee) {
            if (friend instanceof Attendee || friend instanceof Speaker) {
                return true;
            } else {
                return false;
            }
        } else if (this.user instanceof Organizer) {
            return true;
        } else {
            if (friend instanceof Organizer) {
                return false;
            }
            else {
                return true;
            }
        }
    }
}
