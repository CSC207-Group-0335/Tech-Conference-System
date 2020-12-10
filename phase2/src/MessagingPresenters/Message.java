package MessagingPresenters;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

/**
 * A class that represents a message.
 */

public class Message {

    private final String recipientEmail;
    private final String senderEmail;
    private final LocalDateTime timestamp;
    private final String messageContent;
    private ArrayList<String> senderStatuses;
    private ArrayList<String> recipientStatuses;
    private String messageID;

    /**
     * A recipient and sender email address, and a timestamp are required to create an instance of Message. This
     * Message has a uniquely generated ID.
     *
     * @param recipientEmail the email address of the recipient or the eventID
     * @param senderEmail    the email address of the sender
     * @param timestamp      the time and date this message was sent
     * @param messageContent the content of the message
     */

    public Message(String recipientEmail, String senderEmail,
                   LocalDateTime timestamp, String messageContent) {

        this.recipientEmail = recipientEmail;
        this.senderEmail = senderEmail;
        this.timestamp = timestamp;
        this.messageContent = messageContent;
        this.senderStatuses = new ArrayList<>();
        this.recipientStatuses = new ArrayList<>();
        this.senderStatuses.add("Read");
        this.senderStatuses.add("Unarchived");
        this.recipientStatuses.add("Unread");
        this.recipientStatuses.add("Unarchived");
        this.messageID = UUID.randomUUID().toString();
    }


    /**
     * Returns the email address of the user to which this message is being sent.
     *
     * @return the email address of the recipient
     */

    public String getRecipientEmail() {
        return recipientEmail;
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

    public void addStatus(String email, String status) {
        if (email.equals(senderEmail)) {
            this.senderStatuses.add(status);
        } else {
            this.recipientStatuses.add(status);
        }
    }

    public void removeStatus(String email, String status) {
        if (email.equals(senderEmail)) {
            this.senderStatuses.remove(status);
        } else {
            this.recipientStatuses.remove(status);
        }
    }

    public boolean hasStatus(String email, String status) {
        if (email.equals(senderEmail) && senderStatuses.contains(status)) {
            return true;
        } else if (email.equals(recipientEmail) && recipientStatuses.contains(status)) {
            return true;
        } else {
            return false;
        }
    }

    public String getMessageID() { return messageID; }

    public ArrayList<String> getSenderStatuses() {
        return this.senderStatuses;
    }

    public ArrayList<String> getRecipientStatuses() {
        return this.recipientStatuses;
    }

}