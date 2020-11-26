package MessagingPresenters;

import java.util.ArrayList;

/**
 * A class that represents a group chat manager.
 */

public class GroupChatManager {
    private ArrayList<String> participants;
    private ArrayList<Message> messages;

    /**
     * A sender and recipient is required to create instance of GroupChatManager.
     *
     * @param sender    the email of the sender;
     * @param recipients the emails of the recipients;
     */

    public GroupChatManager(String sender, ArrayList<String> recipients) {
        this.participants = new ArrayList<>();
        this.participants.add(sender);
        for (String email: recipients){
            this.participants.add(email);
        }
        this.messages = new ArrayList<>();
    }

    /**
     * Returns a list of messages sent between the users registered under the emails </sender> and </recipient>.
     *
     * @return an arraylist containing all messages sent between these two users
     */

    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     * Returns the email addresses of all the participants involved in this group chat.
     *
     * @return an ArrayList containing the emails of all the participants
     */

    public ArrayList<String> getParticipants() {
        return participants;
    }
}

