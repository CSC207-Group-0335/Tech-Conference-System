package MessagingPresenters;

import Schedule.SignUpAttendeesManager;
import Schedule.SpeakerScheduleManager;
import Schedule.Talk;
import Schedule.TalkSystem;
import UserLogin.*;

import java.time.LocalDateTime;
import java.util.*;

public class SpeakerMessageManager extends MessageManager implements Observer {
    private String speakerEmail;
    private UserStorage allUsers;
    private HashMap<Speaker, SpeakerScheduleManager> speakerScheduleManagerHashMap;
    private HashMap<Talk, SignUpAttendeesManager> signUpMap;
    private ConversationStorage conversationStorage;

    /**
     * A user is needed to create an instance of SpeakerMessageManager.
     * @param speakerEmail the email of speaker whose messages will be managed
     */

    public SpeakerMessageManager(String speakerEmail) {
        this.speakerEmail = speakerEmail;
    }


    /**
     * Returns a list of all talks the speaker will be participating in.
     * @return an ArrayList containing all talks in which the speaker is a part of
     */

    public ArrayList<Talk> getSpeakerTalks(){return speakerScheduleManagerHashMap.get("""
            we are going to use UserStorage to get user""").getTalkList();
    }

    /**
     * Returns a set of the emails of all attendees signed up for this speaker's talks.
     * @return a HashSet containing Strings representing the emails of all attendees signed up for this speaker's talks
     */

    public HashSet<String> getAllAttendees(){
        HashSet<String> emails = new HashSet<String>();
        for (Talk talk: getSpeakerTalks()){
            for (User user: signUpMap.get(talk).userList){
                emails.add(user.getEmail());
            }
        }
        return emails;
    }

    /**
     * Returns a set of the emails of all attendees signed up for this speaker's talks.
     * @return a HashSet containing Strings representing the emails of all attendees signed up for this speaker's talks
     */

    public ArrayList<String> getAttendeesOfTalk(Talk talk){
        ArrayList<String> emails = new ArrayList<String>();
        for (User user: signUpMap.get(talk).userList){
                emails.add(user.getEmail());
            }
        return emails;
    }

    public boolean canMessage(String friendEmail) {
        for (String friend: getAllAttendees()){
            if (friend.equals(friendEmail)){
                return true;
            }
        }
        return false;
    }

    /**
     * Sends a message containing </messageContent> to a user registered under the email </email>.
     * @param email a String representing the email of the recipient
     * @param messageContent a String representing the content of the message
     */

    void message(String email, String messageContent){
        if (conversationStorage.contains(speakerEmail, email)){
            ConversationManager c = conversationStorage.getConversationManager(speakerEmail, email);
            c.addMessage(email, speakerEmail, LocalDateTime.now(), messageContent);
        }
        else{
            ConversationManager c = conversationStorage.addConversationManager(speakerEmail, email);
            c.addMessage(email, speakerEmail, LocalDateTime.now(), messageContent);
        }

    }


    /**
     * Sends a message containing </messageContent> to all attendees.
     * @param messageContent a String representing the content of the message
     */

    public void messageAllAttendees(String messageContent){
        for (String email: getAllAttendees()){
            message(email, messageContent);
        }
    }

    /**
     * Returns a list containing all recipients.
     * @return an ArrayList containing all recipients
     */

    public ArrayList<String> getRecipients() {
        ArrayList<String> emails = new ArrayList<>();
        ArrayList<ConversationManager> managers = conversationStorage.getConversationManagers();
        for (ConversationManager manager: managers) {
            if (manager.getParticipants().contains(speakerEmail)){
                ArrayList<String> participants = new ArrayList<>(manager.getParticipants());
                participants.remove(speakerEmail);
                emails.add(participants.get(0));
            }
        }
        return emails;
    }

    /**
     * Returns a list of messages between this speaker and the user with email </email>.
     * @param email a String representing an email
     * @return an ArrayList containing messages
     */

    public ArrayList<Message> viewMessages(String email){
        if (conversationStorage.contains(speakerEmail, email)){
            ConversationManager c = conversationStorage.getConversationManager(speakerEmail, email);
            return c.getMessages();
        }
        return null;
    }

    /**
     * Updates </allUsers> if and only if </arg> is an instance of UserStorage, </signUpMap> if and only if </arg> is
     * an instance of TalkSystem, </speakerScheduleManagerHashMap> if </arg> is an instance of HashMap, and
     * </conversationStorage> if and only if </arg> is an instance of ConversationStorage.
     * @param o an observable parameter
     * @param arg an Object
     */

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof UserStorage) {
            this.allUsers = (UserStorage) arg;
        }
        else if (arg instanceof HashMap){
            if (o instanceof TalkSystem){
                this.signUpMap = (HashMap) arg;
            }
            else {
                this.speakerScheduleManagerHashMap = (HashMap) arg;
            }
        }
        else if (arg instanceof  ConversationStorage){
            this.conversationStorage = (ConversationStorage) arg;
        }
    }
}
