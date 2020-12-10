package MessagingPresenters;

import java.time.LocalDateTime;
import java.util.UUID;

public class GroupChatMessage {
    private final String groupChatID;
    private final String senderEmail;
    private final LocalDateTime timestamp;
    private final String messageContent;
    private String messageID;

    /**
     * A recipient and sender email address, and a timestamp are required to create an instance of Message. This
     * Message has a uniquely generated ID.
     *
     * @param groupChatID the eventID this groupchat pertains too
     * @param senderEmail    the email address of the sender
     * @param timestamp      the time and date this message was sent
     * @param messageContent the content of the message
     */

    public GroupChatMessage(String groupChatID, String senderEmail,
                   LocalDateTime timestamp, String messageContent) {

        this.groupChatID = groupChatID;
        this.senderEmail = senderEmail;
        this.timestamp = timestamp;
        this.messageContent = messageContent;
        this.messageID = UUID.randomUUID().toString();
    }



    public String getGroupChatID() {
        return groupChatID;
    }

    /**
     * Returns the email address of the user who sent this message.
     *
     * @return the email address of the sender
     */

    public String getSenderEmail() {
        return senderEmail;
    }

    /**
     * Returns the time at which this message was sent.
     *
     * @return a LocalDateTime object representing the time at which this message was sent
     */


    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the content of the message sent.
     *
     * @return a String representing the content of message that was sent
     */

    public String getMessageContent() {
        return messageContent;
    }


    public String getMessageID() { return messageID; }

}
