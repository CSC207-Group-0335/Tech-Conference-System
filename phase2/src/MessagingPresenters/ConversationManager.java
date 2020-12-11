package MessagingPresenters;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ConversationManager {
    private final ArrayList<String> participants;
    private final ArrayList<Message> messages;

    /**
     * A sender and recipient is required to create instance of ConversationManager.
     *
     * @param sender    the email of the sender;
     * @param recipient the email of the recipient;
     */

    public ConversationManager(String sender, String recipient) {
        this.participants = new ArrayList<>();
        this.participants.add(sender);
        this.participants.add(recipient);
        this.messages = new ArrayList<>();
    }

    /**
     * Returns a list of messages sent between the users registered under the emails </sender> and </recipient>.
     *
     * @return an arraylist containing all messages sent between these two users
     */

    public ArrayList<Message> getUnarchivedMessages(String email) {
        ArrayList<Message> unarchivedMessages = new ArrayList<>();
        for (Message message: messages){
            if (message.hasStatus(email, "Unarchived")){
                unarchivedMessages.add(message);
            }
        }
        return unarchivedMessages;
    }

    /**
     * Method the get a users' archived messages by their email.
     * @param email The String email.
     * @return An ArrayList of Messages.
     */
    public ArrayList<Message> getArchivedMessages(String email) {
        ArrayList<Message> archivedMessages = new ArrayList<>();
        for (Message message: messages){
            if (message.hasStatus(email, "Archived")){
                archivedMessages.add(message);
            }
        }
        return archivedMessages;
    }

    /**
     * Adds a message to conversation manager.
     *
     * @param recipientEmail a String representing the email of the recipient
     * @param senderEmail    a String representing the email of the sender
     * @param timestamp      a LocalDateTime object representing the time at which the message was sent
     * @param messageContent a String representing the content of the message
     */

    /**
     * Method to add a message to the messages list
     * @param recipientEmail The String recipient's email.
     * @param senderEmail The String sender's email.
     * @param timestamp The LocalDateTime timestamp.
     * @param messageContent The String message content.
     */
    public void addMessage(String recipientEmail, String senderEmail,
                           LocalDateTime timestamp, String messageContent, ArrayList<String> senderStatuses, ArrayList<String> recipientStatuses) {
        if (this.participants.contains(recipientEmail) && this.participants.contains(senderEmail)) {
            Message message = new Message(recipientEmail, senderEmail, timestamp, messageContent, senderStatuses, recipientStatuses);
            this.messages.add(message);
        }
    }

    private boolean indexExists(int index){
        return index < messages.size();
    }

    public Message getMessageWithIndexAndStatus(String email, int index, Boolean viewingArchived) {
        String status;
        if (viewingArchived) {
            status = "Archived";
        } else {
            status = "Unarchived";
        }

        int i = 0;
        int j = 0;
        while (i < this.messages.size()) {
            Message message = this.messages.get(i);
            if (message.hasStatus(email, status)) {
                if (j == index) {
                    return message;
                } else {
                    j++;
                }
            }
            i++;
        }
        return null;
    }

    /**
     * Method to add a status to a specific message by index and user email.
     * @param email The String email.
     * @param index The int index.
     * @param status The String status.
     */
    public void addStatus(String email, int index, String status, Boolean viewingArchived){
        getMessageWithIndexAndStatus(email, index, viewingArchived).addStatus(email, status);
    }

    /**
     * Method to remove a status to a specific message by index and user email.
     * @param email The String email.
     * @param index The int index.
     * @param status The String status.
     */
    public void removeStatus(String email, int index, String status, Boolean viewingArchived){
        getMessageWithIndexAndStatus(email, index, viewingArchived).removeStatus(email, status);
    }

    /**
     * Method that returns a boolean signifying if the user has a status with a specific message.
     * @param email The String email.
     * @param index The int index.
     * @param status The String status.
     * @return Boolean.
     */
    public Boolean hasStatus(String email, int index, String status, Boolean viewingArchived){
        return getMessageWithIndexAndStatus(email, index, viewingArchived).hasStatus(email, status);
    }

    public void swapStatus(String email, int index, String originalStatus, String newStatus, Boolean viewingArchived){
        getMessageWithIndexAndStatus(email, index, viewingArchived).swapStatus(email, originalStatus, newStatus);
    }

    private Boolean isValidIndex(int index) {
        if (index >= 0 && index < this.messages.size()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Method to delete a message by index and email.
     * @param email The string email.
     * @param index The int index.
     */
    public void deleteMessage(String email, int index, Boolean viewingArchived) {
        this.messages.remove(getMessageWithIndexAndStatus(email, index, viewingArchived));
    }

    /**
     * Returns set of participants.
     */
    /**
     * Method that returns the participants.
     * @return ArrayList of Strings.
     */
    public ArrayList<String> getParticipants() {
        return this.participants;
    }

    /**
     * Method to return the messages in this conversation.
     * @return ArrayList of Messages
     */
    public ArrayList<Message> getMessages() { return this.messages; }

    // FOR JSON READER AND WRITER

    /**
     * Method to return message IDs in this conversation.
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
     * Method to return recipient of a message by messageID.
     * @param messageID The String messageID.
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
     * Method to return sender of a message by messageID.
     * @param messageID The String messageID.
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
     * Method to return timestamp of a message by messageID.
     * @param messageID The String messageID.
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
     * Method to return content of a message by messageID.
     * @param messageID The String messageID.
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
     * Method to return sender status of a message by messageID.
     * @param messageID The String messageID.
     * @return ArrayList of Strings.
     */
    public ArrayList<String> getSenderStatusesOfMessageWithID(String messageID) {
        ArrayList<String> senderStatuses = null;
        for (Message message: messages) {
            if (message.getMessageID().equals(messageID)) {
                senderStatuses = message.getSenderStatuses();
            }
        }
        return senderStatuses;
    }
    /**
     * Method to return recipient status of a message by messageID.
     * @param messageID The String messageID.
     * @return ArrayList of Strings.
     */
    public ArrayList<String> getRecipientStatusesOfMessageWithID(String messageID) {
        ArrayList<String> recipientStatuses = null;
        for (Message message: messages) {
            if (message.getMessageID().equals(messageID)) {
                recipientStatuses = message.getRecipientStatuses();
            }
        }
        return recipientStatuses;
    }
}
