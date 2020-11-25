package MessagingPresenters;

import Schedule.TalkSystem;
import UserLogin.Attendee;
import UserLogin.Speaker;
import UserLogin.User;
import UserLogin.UserStorage;

import java.time.LocalDateTime;
import java.util.*;

public abstract class MessageManager implements Observer {
    public User user;
    public UserStorage allUsers;
    public ConversationStorage conversationStorage;
    public HashSet<User> friendsList;

    public MessageManager(String email) {
        User user = null;
        for (int i = 0; i < allUsers.userList.size(); i++) {
            if (allUsers.userList.get(i).getEmail().equals(email)) {
                user = allUsers.userList.get(i);
            }
        }

        this.user = user;
        this.friendsList = getFriendsList();
    }

    public abstract HashSet<User> getFriendsList();

    /**
     * Returns true if and only if this user is able to message </friend>.
     *
     * @param friendEmail the other user's email who this user can or cannot message
     * @return a boolean representing whether or not this user can message </friend>
     */

    public boolean canMessage(String friendEmail) {
        for (User friend : this.friendsList) {
            if (friend.getEmail().equals(friendEmail)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a list of all users who are attendees.
     *
     * @return an arraylist containing all attendees
     */

    public ArrayList<User> getAttendees() {
        ArrayList<User> attendees = new ArrayList<User>();
        for (int i = 0; i < allUsers.userList.size(); i++) {
            if (allUsers.getUserList().get(i) instanceof Attendee) {
                attendees.add(allUsers.getUserList().get(i));
            }
        }
        return attendees;
    }

    /**
     * Returns a list of all users who are speakers.
     *
     * @return an arraylist containing all speakers
     */

    public ArrayList<User> getSpeakers() {
        ArrayList<User> speakers = new ArrayList<User>();
        for (int i = 0; i < allUsers.userList.size(); i++) {
            if (allUsers.getUserList().get(i) instanceof Speaker) {
                speakers.add(allUsers.getUserList().get(i));
            }
        }
        return speakers;
    }

    public void messageOne(String email, String messageContent) {
        if (this.canMessage(email)) {
            if (conversationStorage.contains(user.getEmail(), email)) {
                ConversationManager c = conversationStorage.getConversationManager(user.getEmail(), email);
                c.addMessage(email, user.getEmail(), LocalDateTime.now(), messageContent);
            } else {
                ConversationManager c = conversationStorage.addConversationManager(user.getEmail(), email);
                c.addMessage(email, user.getEmail(), LocalDateTime.now(), messageContent);
            }
        }
    }

    public ArrayList<Message> viewMessages(String email) {
        if (this.canMessage(email)) {
            if (conversationStorage.contains(user.getEmail(), email)) {
                ConversationManager c = conversationStorage.getConversationManager(user.getEmail(), email);
                return c.getMessages();
            } else {
                ConversationManager c = conversationStorage.addConversationManager(user.getEmail(), email);
                return c.getMessages();
            }
        }
        return null;
    }

    public ArrayList<String> getRecipients() {
        ArrayList<String> emails = new ArrayList<>();
        ArrayList<ConversationManager> managers = conversationStorage.getConversationManagers();
        for (ConversationManager manager : managers) {
            if (manager.getParticipants().contains(user.getEmail())) {
                ArrayList<String> participants = new ArrayList<>(manager.getParticipants());
                participants.remove(user.getEmail());
                emails.add(participants.get(0));
            }
        }
        return emails;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof UserStorage) {
            this.allUsers = (UserStorage) arg;
        } else if (arg instanceof ConversationStorage) {
            this.conversationStorage = (ConversationStorage) arg;
        }
    }
}
