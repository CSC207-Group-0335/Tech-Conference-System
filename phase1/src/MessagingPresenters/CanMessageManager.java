package MessagingPresenters;
import UserLogin.*;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * A class that manages who a user can or cannot message.
 */

public class CanMessageManager implements Observer{
    private User user;
    private UserStorage allUsers;

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
     * @param friendemail the other user's email who this user can or cannot message
     * @return a boolean representing whether or not this user can message </friend>
     */

    public boolean canMessage(String friendemail) {
        for (User friend: this.getFriendsList()){
            if (friend.getEmail().equals(friendemail)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<User> getAttendees(){
        ArrayList<User> friends = new ArrayList<User>();
        for (int i = 0; i < allUsers.userList.size(); i++){
            if (allUsers.getUserList().get(i) instanceof Attendee){
                friends.add(allUsers.getUserList().get(i));
            }
        }
        return friends;
    }

    public ArrayList<User> getSpeakers(){
        ArrayList<User> friends = new ArrayList<User>();
        for (int i = 0; i < allUsers.userList.size(); i++){
            if (allUsers.getUserList().get(i) instanceof Speaker){
                friends.add(allUsers.getUserList().get(i));
            }
        }
        return friends;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof UserStorage) {
            this.allUsers = (UserStorage) arg;
        }
    }
}
