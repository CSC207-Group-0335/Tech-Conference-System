package MessagingPresenters;

import java.util.ArrayList;

/**
 * A class representing a conversation storage.
 */

public class ConversationStorage {
    private final ArrayList<ConversationManager> conversationManagers;

    /**
     * Nothing is needed to create an instance of ConversationStorage.
     */

    public ConversationStorage() {
        conversationManagers = new ArrayList<ConversationManager>();
    }

    /**
     * Checks if instance of ConversationManager already exists in conversationManagers.
     *
     * @return True if conversation is add and false if it already exists.
     */

    public boolean contains(String senderEmail, String recipientEmail) {
        for (ConversationManager c : conversationManagers) {
            if (c.getParticipants().contains(senderEmail) && c.getParticipants().contains(recipientEmail)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets instance of ConversationManager.
     *
     * @param senderEmail    a String representing the email of the sender
     * @param recipientEmail a String representing the email of the recipient
     * @return instance of ConversationManager
     */
    public ConversationManager getConversationManager(String senderEmail, String recipientEmail) {
        for (ConversationManager c : conversationManagers) {
            if (c.getParticipants().contains(senderEmail) && c.getParticipants().contains(recipientEmail)) {
                return c;
            }
        }
        return null;
    }


    /**
     * Adds instance of ConversationManager if not stored already.
     *
     * @param senderEmail    a String representing the email of the sender
     * @param recipientEmail a String representing the email of the recipient
     * @return instance of ConversationManager that is added.
     */
    public ConversationManager addConversationManager(String senderEmail, String recipientEmail) {
        if (!this.contains(senderEmail, recipientEmail)) {
            ConversationManager c = new ConversationManager(senderEmail, recipientEmail);
            conversationManagers.add(c);
            return c;
        }
        return null;
    }

    /**
     * Returns a list of all instances of ConversationManager.
     *
     * @return an ArrayList containing all instances of ConversationManager
     */

    public ArrayList<ConversationManager> getConversationManagers() {
        return conversationManagers;
    }
}
