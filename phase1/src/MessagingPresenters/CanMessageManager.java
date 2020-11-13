package MessagingPresenters;
import UserLogin.*;

import java.util.ArrayList;

/**
 * A class that manages who a user can or cannot message.
 */

public class CanMessageManager {
    private User user;

    /**
     * A user is needed to create an instance of CanMessageManager.
     * @param user the user whose messages will be managed
     */

    public CanMessageManager(User user) {
        this.user = user;
    }

    /**
     * Returns a list of users that this user is allowed to message. Attendees can message all attendees and speakers,
     * organizers can message all users, and speakers can message all attendees.
     * @return the list of users that user can message
     */

    public ArrayList<User> getFriendsList() {
        ArrayList<User> friends = new ArrayList<User>();
        UserStorage allUsers = new UserStorage();

        if (user instanceof Organizer) {
            for (int i = 0; i < allUsers.userList.size(); i++){
                friends.add(allUsers.getUserList().get(i));
            }
        }
        else if (user instanceof Attendee) {
            for (int i = 0; i < allUsers.userList.size(); i++){
                if (allUsers.getUserList().get(i) instanceof Attendee || allUsers.getUserList().get(i)
                        instanceof Speaker){
                    friends.add(allUsers.getUserList().get(i));
                }
            }
        }
        else {
            for (int i = 0; i < allUsers.userList.size(); i++){
                if (allUsers.getUserList().get(i) instanceof Attendee){
                    friends.add(allUsers.getUserList().get(i));
                }
            }
        }
        return friends;
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
            if (friend instanceof Organizer || friend instanceof Speaker) {
                return false;
            }
            else {
                return true;
            }
        }
    }
}
