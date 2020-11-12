package MessagingPresenters;

import UserLogin.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

public class ConversationManager {
    private Set<String> participants;
    private ArrayList<Message> messages;

    /**
     * A sender and recipient is required to create instance of ConversationManager.
     * @param sender the email of the sender;
     * @param recipient the email of the recipient;
     */

    public ConversationManager(String sender, String recipient) {
        this.participants.add(sender);
        this.participants.add(recipient);
    }

    /**
     * Adds a message to conversation manager.
     */

    public void addMessage(String recipientEmail, String senderEmail,
                       LocalDateTime timestamp, String messageContent){
        if (this.participants.contains(recipientEmail) and this.participants.contains(senderEmail)){
            Message message = new Message(recipientEmail, senderEmail, timestamp, messageContent);
            this.messages.add(message);
        }
    }


}
