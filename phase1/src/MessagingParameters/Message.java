package MessagingParameters;

import java.time.LocalDateTime;

/**
 * A class that represents a message.
 */

public class Message {

    private int messageID;
    private String recipientEmail;
    private String senderEmail;
    private int conversationID;
    private LocalDateTime timestamp;

    /**
     * A message and conversation ID, recipient and sender email address, and a timestamp are required to create an
     * instance of Message.
     * @param messageID a unique integer representing the ID of a message
     * @param recipientEmail the email address of the recipient
     * @param senderEmail the email address of the sender
     * @param conversationID a unique integer representing the ID of a conversation
     * @param timestamp the time and date this message was sent
     */

    public Message(int messageID, String recipientEmail, String senderEmail, int conversationID,
                   LocalDateTime timestamp) {
        this.messageID = messageID;
        this.recipientEmail = recipientEmail;
        this.senderEmail = senderEmail;
        this.conversationID = conversationID;
        this.timestamp = timestamp;
    }

    /**
     * Returns the message ID of this message.
     * @return the message ID of the message
     */

    public int getMessageID() {
        return messageID;
    }

    /**
     * Returns the email address of the user to which this message is being sent.
     * @return the email address of the recipient
     */

    public String getRecipientEmail() {
        return recipientEmail;
    }

    /**
     * Returns the email address of the user who sent this message.
     * @return the email address of the sender
     */

    public String getSenderEmail() {
        return senderEmail;
    }

    /**
     * Returns the message ID of the conversation in which this message belongs.
     * @return the message ID of the conversation in which this message is located
     */

    public int getConversationID() {
        return conversationID;
    }

    /**
     * Returns the date and time at which this message was sent.
     * @return the date and time this message was sent
     */

    public LocalDateTime getTimestamp(){
        return timestamp;
    }
}