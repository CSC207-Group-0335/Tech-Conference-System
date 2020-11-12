package MessagingPresenters;

import UserLogin.Attendee;
import UserLogin.Organizer;
import UserLogin.Speaker;
import UserLogin.User;

import java.util.ArrayList;
import java.util.Set;

public class ConversationStorage{
    private ArrayList<ConversationManager> conversationManagers;

    /**
     * Nothing is needed to create an instance of ConversationStorage.
     */

    public ConversationStorage(User sender, User recipient) {
    }

    /**
     * Checks if instance of ConversationManager already exists in conversationManagers.
     * @return True if conversation is add and false if it already exists.
     */

    @Override
    public boolean contains(ConversationManager conversationManager){
        for (ConversationManager c: conversationManagers){
            if (conversationManager.partcipants.equals(c.participants)){
                return true;
            }
        }
        return false;
    }

    /**
     * Adds instance of ConversationManager if not stored already.
     * @return True if instance of ConversationManager is added.
     */

    public boolean addConversationManager(ConversationManager conversationManager) {
        if (!this.contains(conversationManager)){
            conversationManagers.add(conversationManager);
            return true;
        }
        return false;
    }
}
