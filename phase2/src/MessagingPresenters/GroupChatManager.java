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

    public void addMessage(String senderEmail, LocalDateTime timestamp, String messageContent) {
        if (this.eventID.equals(eventID) /*add way to check for participant*/) {
            Message message = new Message(eventID, senderEmail, timestamp, messageContent);
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
}

