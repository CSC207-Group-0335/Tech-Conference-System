package MessagingPresenters;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A class that represents a group chat manager.
 */

public class GroupChatManager {
    private String talkID;
    private ArrayList<Message> messages;

    /**
     * A talk ID is required to create an instance of GroupChatManager
     */

    public GroupChatManager(String talkID) {
        this.talkID = talkID;
        this.messages = new ArrayList<>();
    }

    /**
     * Adds a message.
     *
     * @param senderEmail a String representing the email of the sender
     * @param talkID a String representing a talk ID
     * @param timestamp a LocalDateTime object representing the time at which this message was sent
     * @param messageContent a String representing the content of this message
     */

    public void addMessage(String senderEmail, String talkID,
                           LocalDateTime timestamp, String messageContent) {
        if (this.talkID.equals(talkID) /*add way to check for participant*/) {
            Message message = new Message(talkID, senderEmail, timestamp, messageContent);
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
     * Returns the talk ID.
     *
     * @return a String representing the talk ID
     */

    public String getTalkID() {
        return talkID;
    }
}

