package MessagingPresenters;
import Schedule.SpeakerScheduleManager;
import UserLogin.*;

import java.util.ArrayList;
import java.util.HashMap;
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

    public CanMessageManager(String email) {
        User user = null;
        for (int i = 0; i < allUsers.userList.size(); i++) {
            if (allUsers.userList.get(i).getEmail().equals(email)) {
                user = allUsers.userList.get(i);
            }
        }

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
                if (!allUsers.getUserList().get(i).getEmail().equals(user.getEmail())) {
                    friends.add(allUsers.getUserList().get(i));
                }
            }
        }
        else if (user instanceof Attendee) {
            for (int i = 0; i < allUsers.userList.size(); i++){
                if (allUsers.getUserList().get(i) instanceof Attendee || allUsers.getUserList().get(i)
                        instanceof Speaker){
                    if (!allUsers.getUserList().get(i).getEmail().equals(user.getEmail())) {
                        friends.add(allUsers.getUserList().get(i));
                    }
                }
            }
        }
        return friends;
    }

    /**
     * Returns true if and only if this user is able to message </friend>.
     * @param friendEmail the other user's email who this user can or cannot message
     * @return a boolean representing whether or not this user can message </friend>
     */

    public boolean canMessage(String friendEmail) {
        for (User friend: this.getFriendsList()){
            if (friend.getEmail().equals(friendEmail)){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a list of all users who are attendees.
     * @return an arraylist containing all attendees
     */

    public ArrayList<User> getAttendees(){
        ArrayList<User> friends = new ArrayList<User>();
        for (int i = 0; i < allUsers.userList.size(); i++){
            if (allUsers.getUserList().get(i) instanceof Attendee){
                friends.add(allUsers.getUserList().get(i));
            }
        }
        return friends;
    }

    /**
     * Returns a list of all users who are speakers.
     * @return an arraylist containing all speakers
     */

    public ArrayList<User> getSpeakers(){
        ArrayList<User> friends = new ArrayList<User>();
        for (int i = 0; i < allUsers.userList.size(); i++){
            if (allUsers.getUserList().get(i) instanceof Speaker){
                friends.add(allUsers.getUserList().get(i));
            }
        }
        return friends;
    }

    /**
     * Updates </allUsers> if and only if </arg> is an instance of UserStorage.
     * @param o an observable parameter
     * @param arg an Object
     */

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof UserStorage) {
            this.allUsers = (UserStorage) arg;
        }
    }
}
