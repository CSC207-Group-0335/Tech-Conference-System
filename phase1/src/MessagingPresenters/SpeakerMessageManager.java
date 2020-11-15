package MessagingPresenters;

import Schedule.SignUpAttendeesManager;
import Schedule.SpeakerScheduleManager;
import Schedule.Talk;
import Schedule.TalkSystem;
import UserLogin.*;

import java.util.*;

public class SpeakerMessageManager implements Observer{
    private Speaker speaker;
    private UserStorage allUsers;
    private HashMap<Speaker, SpeakerScheduleManager> speakerScheduleManagerHashMap;
    private HashMap<Talk, SignUpAttendeesManager> signUpMap;
    private ConversationStorage conversationStorage;

    /**
     * A user is needed to create an instance of SpeakerMessageManager.
     * @param speaker the speaker whose messages will be managed
     */

    public SpeakerMessageManager(Speaker speaker) {
        this.speaker = speaker;
    }

    /**
     * Returns a list of all talks the speaker will be participating in.
     * @return an ArrayList containing all talks in which the speaker is a part of
     */

    public ArrayList<Talk> getSpeakerTalks(){
        return speakerScheduleManagerHashMap.get(speaker).getTalkList();
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
     * Returns a list containing the emails of all senders in conversations involving this speaker.
     * @return an ArrayList containing the emails of all senders
     */

    public ArrayList<String> getAllSenders(){
        ArrayList<String> senders = new ArrayList<String>();
        for (ConversationManager c: conversationStorage.getConversationManagers()){
            if (c.getParticipants().contains(speaker.getEmail()) && !c.getLastSenderEmail().equals(speaker.getEmail())){
                HashSet<String> participants = c.getParticipants();
                for (String email: participants){
                    if (!(email.equals(speaker.getEmail()))){
                        senders.add(email);
                    }
                }

            }
        }
        return senders;
    }

    /**
     * Returns True if and only if this speaker can reply to the user registered under the email </email>.
     * @param email a String representing the email of the user
     * @return a boolean representing whether or not the speaker can reply
     */

    public boolean canReply(String email){
        if (getAllSenders().contains(email)){
            return true;
        }
        return false;
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
