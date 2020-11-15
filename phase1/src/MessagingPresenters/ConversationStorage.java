package MessagingPresenters;

import UserLogin.Attendee;
import UserLogin.Organizer;
import UserLogin.Speaker;
import UserLogin.User;
import jdk.internal.icu.text.UnicodeSet;

import java.util.ArrayList;
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
        Set<String> participants;
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
     * Adds instance of ConversationManager if not stored already.
     * @return True if instance of ConversationManager is added.
     */

    public boolean addConversationManager(String senderEmail, String receipientEmail){
        if (!this.contains(senderEmail, receipientEmail)){
            conversationManagers.add(new ConversationManager(senderEmail, receipientEmail));
            return true;
        }
        return false;
    }
}
