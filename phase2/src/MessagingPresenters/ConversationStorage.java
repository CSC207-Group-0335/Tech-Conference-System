package MessagingPresenters;

import java.util.ArrayList;

/**
 * A class representing a conversation storage.
 */

public class ConversationStorage {
    private ArrayList<ConversationManager> conversationManagers;
    private ArrayList<GroupChatManager> groupChatManagers;

    /**
     * Nothing is needed to create an instance of ConversationStorage.
     */

    public ConversationStorage() {
        conversationManagers = new ArrayList<>();
        groupChatManagers = new ArrayList<>();
    }

    /**
     * Checks if instance of ConversationManager already exists in conversationManagers.
     *
     * @return True if conversation is added and false if it already exists.
     */

    public boolean contains(String senderEmail, String recipientEmail) {
        for (ConversationManager c : conversationManagers) {
            if (c.getParticipants().contains(senderEmail) && c.getParticipants().contains(recipientEmail)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(String talkID) {
        for (GroupChatManager g : groupChatManagers) {
            if (g.getTalkID().equals(talkID)){
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

    public GroupChatManager getGroupChatManager(String talkID) {
        for (GroupChatManager g : groupChatManagers) {
            if (g.getTalkID().equals(talkID)) {
                return g;
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

    public GroupChatManager addGroupChatManager(String talkID) {
        if (!this.contains(talkID)) {
            GroupChatManager g = new GroupChatManager(talkID);
            groupChatManagers.add(g);
            return g;
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

    public ArrayList<GroupChatManager> getGroupChatManagers() {
        return groupChatManagers;
    }

}
