package MessagingPresenters;

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
     * Returns the email of the user who sent the latest message.
     *
     * @return a String representing the email of the most recent sender
     */

    public String getLastSenderEmail() {
        return messages.get(messages.size() - 1).getSenderEmail();
    }

    /**
     * Adds a message to conversation manager.
     *
     * @param recipientEmail a String representing the email of the recipient
     * @param senderEmail    a String representing the email of the sender
     * @param timestamp      a LocalDateTime object representing the time at which the message was sent
     * @param messageContent a String representing the content of the message
     */

    public void addMessage(String recipientEmail, String senderEmail,
                           LocalDateTime timestamp, String messageContent) {
        if (this.participants.contains(recipientEmail) && this.participants.contains(senderEmail)) {
            Message message = new Message(recipientEmail, senderEmail, timestamp, messageContent);
            this.messages.add(message);
        }
    }

    private boolean indexExists(int index){
        return index - 1 == messages.size();
    }

    public void addStatus(String email, int index, String status){
        if (indexExists(index)){
            messages.get(index).addStatus(email, status);
        }
    }

    public void removeStatus(String email, int index, String status){
        if (indexExists(index)){
            messages.get(index).removeStatus(email, status);
        }
    }

    public Boolean hasStatus(String email, int index, String status){
        Boolean returnStatus = null;
        if (indexExists(index)){
            returnStatus = messages.get(index).hasStatus(email, status);
        }
        return returnStatus;
    }

    private Boolean isValidIndex(Integer index) {
        if (index >= 0 && index < this.messages.size()) {
            return true;
        }
        else {
            return false;
        }
    }

    public void deleteMessage(String email, Integer index) {
        if (this.isValidIndex(index) && email.equals(messages.get(index).getSenderEmail())) {
            this.messages.remove(index);
        }
    }

    /**
     * Returns set of participants.
     */

    public ArrayList<String> getParticipants() {
        return this.participants;
    }

    public ArrayList<Message> getMessages() { return this.messages; }

    // FOR JSON READER AND WRITER
    public ArrayList<String> getMessageIDs() {
        ArrayList<String> messageIDs = new ArrayList<String>();
        for (Message message: messages) {
            messageIDs.add(message.getMessageID());
        }
        return messageIDs;
    }

    public String getRecipientOfMessageWithID(String messageID) {
        String recipient = null;
        for (Message message: messages) {
            if (message.getMessageID() == messageID) {
                recipient = message.getRecipientEmail();
            }
        }
        return recipient;
    }

    public String getSenderOfMessageWithID(String messageID) {
        String sender = null;
        for (Message message: messages) {
            if (message.getMessageID() == messageID) {
                sender = message.getSenderEmail();
            }
        }
        return sender;
    }

    public LocalDateTime getTimestampOfMessageWithID(String messageID) {
        LocalDateTime time = null;
        for (Message message: messages) {
            if (message.getMessageID() == messageID) {
                time = message.getTimestamp();
            }
        }
        return time;
    }

    public String getContentOfMessageWithID(String messageID) {
        String content = null;
        for (Message message: messages) {
            if (message.getMessageID() == messageID) {
                content = message.getMessageContent();
            }
        }
        return content;
    }

    public ArrayList<String> getSenderStatusesOfMessageWithID(String messageID) {
        ArrayList<String> senderStatuses = null;
        for (Message message: messages) {
            if (message.getMessageID() == messageID) {
                senderStatuses = message.getSenderStatuses();
            }
        }
        return senderStatuses;
    }

    public ArrayList<String> getRecipientStatusesOfMessageWithID(String messageID) {
        ArrayList<String> recipientStatuses = null;
        for (Message message: messages) {
            if (message.getMessageID() == messageID) {
                recipientStatuses = message.getRecipientStatuses();
            }
        }
        return recipientStatuses;
    }

}
