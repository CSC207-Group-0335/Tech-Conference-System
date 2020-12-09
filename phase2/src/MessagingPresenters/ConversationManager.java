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
            if (message.getStatus(email, "unarchived")){
                unarchivedMessages.add(message);
            }
        }
        return unarchivedMessages;
    }

    public ArrayList<Message> getArchivedMessages(String email) {
        ArrayList<Message> unarchivedMessages = new ArrayList<>();
        for (Message message: messages){
            if (!message.getStatus(email, "unarchived")){
                unarchivedMessages.add(message);
            }
        }
        return unarchivedMessages;
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

    public void changeStatus(String email, int index, String status){
        if (indexExists(index)){
            messages.get(index).setStatus(email, status);
        }
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

}
