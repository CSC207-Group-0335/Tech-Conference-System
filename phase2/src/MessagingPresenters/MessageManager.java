package MessagingPresenters;
import Schedule.Event;
import Schedule.EventManager;
import UserLogin.*;

import java.time.LocalDateTime;
import java.util.*;

public abstract class MessageManager {
    public User user;
    public UserManager userManager;
    public ConversationStorage conversationStorage;
    public HashSet<User> friendsList;
    public EventManager eventManager;

    /***
     * An email is required to create an instance of MessageManager.
     *
     * @param email a String representing an email address
     */

    public MessageManager(String email, UserManager userManager, ConversationStorage conversationStorage, EventManager eventManager) {
        this.userManager = userManager;
        User user = null;
        for (int i = 0; i < userManager.userList.size(); i++) {
            if (userManager.userList.get(i).getEmail().equals(email)) {
                user = userManager.userList.get(i);
            }
        }
        this.user = user;
        this.conversationStorage = conversationStorage;
        this.eventManager = eventManager;
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
        for (Event event: eventManager.eventList){
            if (friendEmail.equals(event.getEventId())){
                if (eventManager.getEvent(friendEmail).getSpeakers().contains(user.getEmail()) ||
                        eventManager.getEvent(friendEmail).getUsersSignedUp().contains(user.getEmail()) ||
                user instanceof Organizer){
                    return true;
                }
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
        for (int i = 0; i < userManager.userList.size(); i++) {
            if (userManager.getUserList().get(i) instanceof Attendee) {
                attendees.add(userManager.getUserList().get(i));
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
        for (int i = 0; i < userManager.userList.size(); i++) {
            if (userManager.getUserList().get(i) instanceof Speaker) {
                speakers.add(userManager.getUserList().get(i));
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
     * Returns true if this message has a status.
     * @param email a String representing an email
     * @param index an int representing an index
     * @param status a String representing a status
     * @return
     */

    public Boolean hasMessageStatus(String email, int index, String status, Boolean viewingArchived){
        Boolean returnStatus = null;
        if (containsConversationWith(email)){
            ConversationManager c = conversationStorage.getConversationManager(user.getEmail(), email);
            returnStatus = c.hasStatus(user.getEmail(), index, status, viewingArchived);
        }
        return returnStatus;
    }

    public void swapMessageStatus(String email, int index, String originalStatus, String newStatus, Boolean viewingArchived){
        if (containsConversationWith(email)){
            ConversationManager c = conversationStorage.getConversationManager(user.getEmail(), email);
            c.swapStatus(user.getEmail(), index, originalStatus, newStatus, viewingArchived);
        }
    }

    /**
     * Sends a message to a user.
     *  @param recipient a String representing the recipient's email address
     * @param messageContent a String containing the content of the message
     * @param individual
     */

    public void message(String recipient, String messageContent, Boolean individual) {
        if (this.canMessage(recipient)) {
            ArrayList<String> senderStatuses = new ArrayList<>();
            senderStatuses.add("Read");
            senderStatuses.add("Unarchived");
            ArrayList<String> recipientStatuses = new ArrayList<>();
            recipientStatuses.add("Unread");
            recipientStatuses.add("Unarchived");
            if (individual){
                if (containsConversationWith(recipient)) {
                    ConversationManager c = conversationStorage.getConversationManager(user.getEmail(), recipient);
                    c.addMessage(recipient, user.getEmail(), LocalDateTime.now(), messageContent, senderStatuses, recipientStatuses);
                } else {
                    ConversationManager c = conversationStorage.addConversationManager(user.getEmail(), recipient);
                    c.addMessage(recipient, user.getEmail(), LocalDateTime.now(), messageContent, senderStatuses, recipientStatuses);
                }
            }else{
                if (conversationStorage.contains(recipient)){
                    GroupChatManager g = conversationStorage.getGroupChatManager(recipient);
                    g.addMessage(user.getEmail(), LocalDateTime.now(), messageContent);
                } else {
                    GroupChatManager g = conversationStorage.addGroupChatManager(recipient);
                    g.addMessage(user.getEmail(), LocalDateTime.now(), messageContent);
                }
            }
        }
    }

    /**
     * Deletes a message.
     *
     * @param email a String representing the recipient's email address
     * @param index an Integer representing the index of the message to be deleted
     */

    public void deleteMessage(String email, Integer index, Boolean viewingArchived) {
        if (containsConversationWith(email)) {
            ConversationManager c = conversationStorage.getConversationManager(user.getEmail(), email);
            c.deleteMessage(user.getEmail(), index, viewingArchived);
        }
    }

    /**
     * Returns all the messages sent between this user and the recipient registered under </email>.
     *
     * @param email a String representing the email of the recipient
     * @return an ArrayList containing all messages sent between these two users
     */

    public ArrayList<Message> getUnarchivedMessages(String email) {
        if (this.canMessage(email)) {
            if (containsConversationWith(email)) {
                ConversationManager c = conversationStorage.getConversationManager(user.getEmail(), email);
                return c.getUnarchivedMessages(user.getEmail());
            } else {
                ConversationManager c = conversationStorage.addConversationManager(user.getEmail(), email);
                return c.getUnarchivedMessages(user.getEmail());
            }
        }
        return null;
    }

    /**
     * Returns a list of archived messages.
     * @param email a String representing an email
     * @return an ArrayList containing archived messages
     */

    public ArrayList<Message> getArchivedMessages(String email) {
        if (this.canMessage(email)) {
            if (containsConversationWith(email)) {
                ConversationManager c = conversationStorage.getConversationManager(user.getEmail(), email);
                return c.getArchivedMessages(user.getEmail());
            } else {
                ConversationManager c = conversationStorage.addConversationManager(user.getEmail(), email);
                return c.getArchivedMessages(user.getEmail());
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
     * Method that returns an ArrayList of all EventID's
     * @return ArrayList of Strings.
     */
    public ArrayList<String> getEventIDs() {
        ArrayList<String> eventIDs = new ArrayList<>();
        for (Event event : eventManager.eventList) {
            if (event.getSpeakers().contains(user.getEmail()) ||
                    event.getUsersSignedUp().contains(user.getEmail()) ||
                    user instanceof Organizer) {
                eventIDs.add(eventManager.toStringEvent(event.getEventId()));
            }
        }
        return eventIDs;
    }

    /**
     * Method that returns an ArrayList of all EventID's
     * @return ArrayList of Strings.
     */
    public ArrayList<String> getIDs() {
        ArrayList<String> eventIDs = new ArrayList<>();
        for (Event event : eventManager.eventList) {
            if (event.getSpeakers().contains(user.getEmail()) ||
                    event.getUsersSignedUp().contains(user.getEmail()) ||
                    user instanceof Organizer) {
                eventIDs.add(event.getEventId());
            }
        }
        return eventIDs;
    }

    /**
     * Returns ArrayList of string representations of group chat messages by eventID
     * @param eventID The String eventID.
     * @return ArrayList of Strings.
     */
    public ArrayList<String> getGroupChatMessages(String eventID) {
        ArrayList<String> messages = new ArrayList<>();
        if (conversationStorage.getGroupChatManager(eventID) == null){
            conversationStorage.addGroupChatManager(eventID);
        }
        if (conversationStorage.getGroupChatManager(eventID) != null) {
            for (GroupChatMessage message: conversationStorage.getGroupChatManager(eventID).getMessages()) {
                messages.add(message.getSenderEmail()+": "+message.getMessageContent()+"\t"+message.getTimestamp().toString());
            }
        }
        return messages;
    }

    /**
     * Method to get formatted messages.
     * @param viewingArchivedMessages Boolean that tells if we are searching archived or unarchived messages.
     * @param recipientEmail The String recipients' email.
     * @return ArrayList of String formatted messages.
     */
    public ArrayList<String> getFormattedMessages(Boolean viewingArchivedMessages, String recipientEmail) {
        ArrayList<Message> messages;
        if (viewingArchivedMessages) {
            messages = getArchivedMessages(recipientEmail);
        }
        else {
            messages = getUnarchivedMessages(recipientEmail);
        }
        ArrayList<String> outputMessages = new ArrayList<>();
        for (int i = 1; i <= messages.size(); i++) {
            Message message = messages.get(i - 1);
            String readStatement = "Read by: ";
            Boolean hasSenderRead = message.hasStatus(message.getSenderEmail(), "Read");
            Boolean hasRecipientRead = message.hasStatus(message.getRecipientEmail(), "Read");
            if (hasSenderRead) {
                readStatement += message.getSenderEmail();
                if (hasRecipientRead) {
                    readStatement += " and " + message.getRecipientEmail();
                }
            }
            else if (hasRecipientRead) {
                readStatement += message.getRecipientEmail();
            }
            else {
                readStatement += "none";
            }
            outputMessages.add(i + " - " + message.getSenderEmail() + ": " + message.getMessageContent() + " - " + readStatement);
        }
        return outputMessages;
    }

    /**
     * Method to return a message by index.
     * @param viewingArchivedMessages Boolean that tells if we are searching archived or unarchived messages.
     * @param index The int index.
     * @param recipientEmail The String recipient email.
     * @return Message object.
     */
    public Message getMessageAtIndex(Boolean viewingArchivedMessages, int index, String recipientEmail) {
        if (viewingArchivedMessages) {
            return getArchivedMessages(recipientEmail).get(index);
        }
        else {
            return getUnarchivedMessages(recipientEmail).get(index);
        }
    }

    /**
     * Method to return the content of a message by index.
     * @param viewingArchivedMessages Boolean that tells if we are searching archived or unarchived messages.
     * @param index The int index.
     * @param recipientEmail The String recipient email.
     * @return String content.
     */
    public String getContentOfMessageAtIndex(Boolean viewingArchivedMessages, int index, String recipientEmail) {
        return getMessageAtIndex(viewingArchivedMessages, index, recipientEmail).getMessageContent();
    }

    /**
     * Method to return the read status of a message by index.
     * @param viewingArchivedMessages Boolean that tells if we are searching archived or unarchived messages.
     * @param index The int index.
     * @param recipientEmail The string recipient email.
     * @param senderEmail The string sender email.
     * @return Boolean.
     */
    public Boolean getReadStatusOfMessageAtIndex(Boolean viewingArchivedMessages, int index, String recipientEmail, String senderEmail) {
        return getMessageAtIndex(viewingArchivedMessages, index, recipientEmail).hasStatus(senderEmail, "Read");
    }
}
