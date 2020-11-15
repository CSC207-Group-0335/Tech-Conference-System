package MessagingPresenters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A class representing a conversation storage.
 */

public class ConversationStorage{
    private ArrayList<ConversationManager> conversationManagers;

    /**
     * Nothing is needed to create an instance of ConversationStorage.
     */

    public ConversationStorage() {
    }

    /**
     * Checks if instance of ConversationManager already exists in conversationManagers.
     * @return True if conversation is add and false if it already exists.
     */

    public boolean contains(String senderEmail, String receipientEmail){
        Set<String> participants = new HashSet<String>();
        participants.add(senderEmail);
        participants.add(receipientEmail);
        for (ConversationManager c: conversationManagers){
            if (c.getParticipants().equals(participants)){
                return true;
            }
        }
        return false;
    }

    /**
     * Gets instance of ConversationManager.
     * @param senderEmail a String representing the email of the sender
     * @param recipientEmail a String representing the email of the recipient
     * @return instance of ConversationManager
     */
    public ConversationManager getConversationManager(String senderEmail, String recipientEmail) {
        Set<String> participants = new HashSet<String>();
        participants.add(senderEmail);
        participants.add(recipientEmail);
        for (ConversationManager c : conversationManagers) {
            if (c.getParticipants().equals(participants)) {
                return c;
            }
        }
        return null;
    }


    /**
     * Adds instance of ConversationManager if not stored already.
     * @param senderEmail a String representing the email of the sender
     * @param recipientEmail a String representing the email of the recipient
     * @return instance of ConversationManager that is added.
     */
    public ConversationManager addConversationManager(String senderEmail, String recipientEmail){
        if (!this.contains(senderEmail, recipientEmail)){
            ConversationManager c = new ConversationManager(senderEmail, recipientEmail);
            conversationManagers.add(c);
            return c;
        }
        return null;
    }

    /**
     * Returns a list of all instances of ConversationManager.
     * @return an ArrayList containing all instances of ConversationManager
     */

    public ArrayList<ConversationManager> getConversationManagers() {
        return conversationManagers;
    }
}
