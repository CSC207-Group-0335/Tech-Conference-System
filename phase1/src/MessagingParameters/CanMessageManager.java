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

    public CanMessageManager(User user) {
        this.user = user;
    }

    public ArrayList<User> getFriendsList() {
        if (user instanceof Organizer) {

        }
        else if (user instanceof Attendee) {

        }
        else {

        }
        return friendsList;
    }

    public boolean canMessage(User user) {
        if (this.user instanceof Attendee) {
            if (user instanceof Attendee || user instanceof Speaker) {
                return true;
            } else {
                return false;
            }
        } else if (this.user instanceof Organizer) {
            return true;
        } else {
            if (user instanceof Organizer) {
                return false;
            }
            else {
                return true;
            }
        }
    }
}
