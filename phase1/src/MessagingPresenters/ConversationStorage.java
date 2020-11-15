package MessagingPresenters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
     * @return instance of ConversationManager
     */
    public ConversationManager getConversationManager(String senderEmail, String receipientEmail) {
        Set<String> participants = new HashSet<String>();
        participants.add(senderEmail);
        participants.add(receipientEmail);
        for (ConversationManager c : conversationManagers) {
            if (c.getParticipants().equals(participants)) {
                return c;
            }
        }
        return null;
    }


    /**
     * Adds instance of ConversationManager if not stored already.
     * @return instance of ConversationManager that is added.
     */
    public ConversationManager addConversationManager(String senderEmail, String receipientEmail){
        if (!this.contains(senderEmail, receipientEmail)){
            ConversationManager c = new ConversationManager(senderEmail, receipientEmail);
            conversationManagers.add(c);
            return c;
        }
        return null;
    }

}
