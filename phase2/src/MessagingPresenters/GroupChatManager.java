package MessagingPresenters;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A class that represents a group chat manager.
 */

public class GroupChatManager {
    private String eventID;
    private ArrayList<Message> messages;

    /**
     * An event ID is required to create an instance of GroupChatManager
     */

    public GroupChatManager(String eventID) {
        this.eventID = eventID;
        this.messages = new ArrayList<>();
    }

    /**
     * Adds a message.
     *
     * @param senderEmail a String representing the email of the sender
     * @param timestamp a LocalDateTime object representing the time at which this message was sent
     * @param messageContent a String representing the content of this message
     */

    public void addMessage(String senderEmail, LocalDateTime timestamp, String messageContent, ArrayList<String> senderStatuses, ArrayList<String> recipientStatuses) {
        if (this.eventID.equals(eventID) /*add way to check for participant*/) {
            Message message = new Message(eventID, senderEmail, timestamp, messageContent, senderStatuses, recipientStatuses);
            this.messages.add(message);
        }
    }

    /**
     * Returns a list of messages sent between the users registered under the emails </sender> and </recipient>.
     *
     * @return an arraylist containing all messages sent between these two users
     */

    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     * Returns the event ID.
     *
     * @return a String representing the event ID
     */

    public String getEventID() {
        return eventID;
    }

    /**
     * Return ArrayList of messsageIDs in this chat.
     * @return ArrayList of Strings.
     */
    public ArrayList<String> getMessageIDs() {
        ArrayList<String> messageIDs = new ArrayList<String>();
        for (Message message: messages) {
            messageIDs.add(message.getMessageID());
        }
        return messageIDs;
    }

    /**
     * Returns sender by messageID.
     * @param messageID String messageID.
     * @return String email.
     */
    public String getSenderOfMessageWithID(String messageID) {
        String sender = null;
        for (Message message: messages) {
            if (message.getMessageID().equals(messageID)) {
                sender = message.getSenderEmail();
            }
        }
        return sender;
    }

    /**
     * Returns recipient by messageID.
     * @param messageID String messageID.
     * @return String email.
     */
    public String getRecipientOfMessageWithID(String messageID) {
        String recipient = null;
        for (Message message: messages) {
            if (message.getMessageID().equals(messageID)) {
                recipient = message.getRecipientEmail();
            }
        }
        return recipient;
    }

    /**
     * Method to get timestamp by messageID.
     * @param messageID String messageID.
     * @return LocalDateTime.
     */
    public LocalDateTime getTimestampOfMessageWithID(String messageID) {
        LocalDateTime time = null;
        for (Message message: messages) {
            if (message.getMessageID().equals(messageID)) {
                time = message.getTimestamp();
            }
        }
        return time;
    }

    /**
     * Return content of message by messageID.
     * @param messageID String messageID.
     * @return String content.
     */
    public String getContentOfMessageWithID(String messageID) {
        String content = null;
        for (Message message: messages) {
            if (message.getMessageID().equals(messageID)) {
                content = message.getMessageContent();
            }
        }
        return content;
    }


    /**
     * Method to return groupchatID by messageID.
     * @param messageID String messageID.
     * @return String groupID.
     */
    public String getGroupChatIDOfMessageWithID(String messageID) {
        String groupID = null;
        for (Message message: messages) {
            if (message.getMessageID().equals(messageID)) {
                groupID = getEventID();
            }
        }
        return groupID;
    }
}