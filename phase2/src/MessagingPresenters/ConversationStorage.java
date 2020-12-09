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

    /**
     * Returns True if and only if groupChatManagers contains a specific talk ID.
     *
     * @param talkID a String representing a talk ID
     * @return a boolean representing whether or not talkID is in groupChatManagers
     */

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

    /**
     * Returns the GroupChatManager with talk ID </talkID> if it exists.
     *
     * @param talkID a String representing a talk ID
     * @return a GroupChatManager with talk ID </talkID> if it exists. Returns null otherwise
     */

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

    /**
     * Adds a GroupChatManager with talk ID </talkID> if it does not already exist.
     *
     * @param talkID a String representing a talk ID
     * @return the new GroupChatManager created. Returns null if it already exists
     */

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

    /**
     * Returns a list of GroupChatManagers
     *
     * @return an ArrayList containing GroupChatManagers
     */

    public ArrayList<GroupChatManager> getGroupChatManagers() {
        return groupChatManagers;
    }

}
