package MessagingPresenters;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A class that represents a group chat manager.
 */

public class GroupChatManager {
    private String eventID;
    private ArrayList<GroupChatMessage> messages;

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

    public void addMessage(String senderEmail, LocalDateTime timestamp, String messageContent) {
        if (this.eventID.equals(eventID) /*add way to check for participant*/) {
            GroupChatMessage message = new GroupChatMessage(eventID, senderEmail, timestamp, messageContent);
            this.messages.add(message);
        }
    }

    /**
     * Returns a list of messages sent between the users registered under the emails </sender> and </recipient>.
     *
     * @return an arraylist containing all messages sent between these two users
     */

    public ArrayList<GroupChatMessage> getMessages() {
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

    public ArrayList<String> getMessageIDs() {
        ArrayList<String> messageIDs = new ArrayList<String>();
        for (GroupChatMessage message: messages) {
            messageIDs.add(message.getMessageID());
        }
        return messageIDs;
    }

    public String getSenderOfMessageWithID(String messageID) {
        String sender = null;
        for (GroupChatMessage message: messages) {
            if (message.getMessageID().equals(messageID)) {
                sender = message.getSenderEmail();
            }
        }
        return sender;
    }

    public LocalDateTime getTimestampOfMessageWithID(String messageID) {
        LocalDateTime time = null;
        for (GroupChatMessage message: messages) {
            if (message.getMessageID().equals(messageID)) {
                time = message.getTimestamp();
            }
        }
        return time;
    }

    public String getContentOfMessageWithID(String messageID) {
        String content = null;
        for (GroupChatMessage message: messages) {
            if (message.getMessageID().equals(messageID)) {
                content = message.getMessageContent();
            }
        }
        return content;
    }


    public String getGroupchatIDOfMessageWithID(String messageID) {
        String groupID = null;
        for (GroupChatMessage message: messages) {
            if (message.getMessageID().equals(messageID)) {
                groupID = message.getGroupChatID();
            }
        }
        return groupID;
    }
}