package MessagingPresenters;

import java.time.LocalDateTime;

/**
 * A class that represents a message.
 */

public class Message {

    private String recipientEmail;
    private String senderEmail;
    private LocalDateTime timestamp;
    private String messageContent;

    /**
     * A recipient and sender email address, and a timestamp are required to create an instance of Message. This
     * Message has a uniquely generated ID.
     * @param recipientEmail the email address of the recipient
     * @param senderEmail the email address of the sender
     * @param timestamp the time and date this message was sent
     * @param messageContent the content of the message
     */

    public Message(String recipientEmail, String senderEmail,
                   LocalDateTime timestamp, String messageContent) {

        this.recipientEmail = recipientEmail;
        this.senderEmail = senderEmail;
        this.timestamp = timestamp;
        this.messageContent = messageContent;
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
     * Returns the time at which this message was sent.
     * @return a LocalDateTime object representing the time at which this message was sent
     */


    public LocalDateTime getTimestamp(){
        return timestamp;
    }

    /**
     * Returns the content of the message sent.
     * @return a String representing the content of message that was sent
     */

    public String getMessageContent(){
        return messageContent;
    }

}