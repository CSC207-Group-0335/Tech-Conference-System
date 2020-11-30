package MessagingPresenters;
import UserLogin.Attendee;
import UserLogin.Speaker;
import UserLogin.User;
import UserLogin.UserStorage;

import java.time.LocalDateTime;
import java.util.*;

public abstract class MessageManager {
    public User user;
    public UserStorage userStorage;
    public ConversationStorage conversationStorage;
    public HashSet<User> friendsList;

    /***
     * An email is required to create an instance of MessageManager.
     *
     * @param email a String representing an email address
     */

    public MessageManager(String email, UserStorage userStorage) {
        this.userStorage = userStorage;
        User user = null;
        for (int i = 0; i < userStorage.userList.size(); i++) {
            if (userStorage.userList.get(i).getEmail().equals(email)) {
                user = userStorage.userList.get(i);
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
        ArrayList<User> attendees = new ArrayList<>();
        for (int i = 0; i < userStorage.userList.size(); i++) {
            if (userStorage.getUserList().get(i) instanceof Attendee) {
                attendees.add(userStorage.getUserList().get(i));
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
        ArrayList<User> speakers = new ArrayList<>();
        for (int i = 0; i < userStorage.userList.size(); i++) {
            if (userStorage.getUserList().get(i) instanceof Speaker) {
                speakers.add(userStorage.getUserList().get(i));
            }
        }
        return speakers;
    }

    /**
     * Returns true if and only if there exists a conversation between this user and another user with email </email>.
     *
     * @param email a String representing the email of the recipient
     * @return a boolean representing whether or not there is a conversation between these two users
     */

    private Boolean containsConversationWith(String email) {
        if (conversationStorage.contains(user.getEmail(), email)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Sends a message to a user.
     *
     * @param email a String representing the recipient's email address
     * @param messageContent a String containing the content of the message
     */

    public void messageOne(String email, String messageContent) {
        if (this.canMessage(email)) {
            if (containsConversationWith(email)) {
                ConversationManager c = conversationStorage.getConversationManager(user.getEmail(), email);
                c.addMessage(email, user.getEmail(), LocalDateTime.now(), messageContent);
            } else {
                ConversationManager c = conversationStorage.addConversationManager(user.getEmail(), email);
                c.addMessage(email, user.getEmail(), LocalDateTime.now(), messageContent);
            }
        }
    }

    /**
     * Deletes a message.
     *
     * @param email a String representing the recipient's email address
     * @param index an Integer representing the index of the message to be deleted
     */

    public void deleteMessage(String email, Integer index) {
        if (containsConversationWith(email)) {
            ConversationManager c = conversationStorage.getConversationManager(user.getEmail(), email);
            c.deleteMessage(index);
        }
    }

    /**
     * Marks a message as read or unread.
     *
     * @param email a String representing the recipient's email address
     * @param index an Integer representing the index of the message to be deleted
     */

    public void toggleRead(String email, Integer index) {
        if (containsConversationWith(email)) {
            ConversationManager c = conversationStorage.getConversationManager(user.getEmail(), email);
            c.toggleRead(index);
        }
    }

    /**
     * Archives a conversation.
     *
     * @param email a String representing the recipient's email address
     */

    public void archiveConversationWith(String email) {
        conversationStorage.archiveConversationWith(user.getEmail(), email);
    }

    /**
     * Returns all the messages sent between this user and the recipient registered under </email>.
     *
     * @param email a String representing the email of the recipient
     * @return an ArrayList containing all messages sent between these two users
     */

    public ArrayList<Message> viewMessages(String email) {
        if (this.canMessage(email)) {
            if (containsConversationWith(email)) {
                ConversationManager c = conversationStorage.getConversationManager(user.getEmail(), email);
                return c.getMessages();
            } else {
                ConversationManager c = conversationStorage.addConversationManager(user.getEmail(), email);
                return c.getMessages();
            }
        }
        return null;
    }

    /**
     * Returns the emails of all recipients.
     *
     * @return an ArrayList containing all the email addresses of the recipients
     */

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

    /**
     * Sends a message to multiple users.
     *
     * @param emails an ArrayList containing emails of the recipients
     * @param messageContent a String representing the content of the message
     */

    public void messageGroup(ArrayList<String> emails, String messageContent) {
        for (String email: emails) {
            messageOne(email, messageContent);
        }
    }
}
