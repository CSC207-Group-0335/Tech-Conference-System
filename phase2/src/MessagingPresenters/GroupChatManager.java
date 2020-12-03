package MessagingPresenters;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A class that represents a group chat manager.
 */

public class GroupChatManager {
    private String talkID;
    private ArrayList<Message> messages;


    public GroupChatManager(String talkID) {
        this.talkID = talkID;
        this.messages = new ArrayList<>();
    }

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

    public String getTalkID() {
        return talkID;
    }
}

