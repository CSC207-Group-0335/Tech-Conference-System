package MessagingPresenters;

import Schedule.SignUpAttendeesManager;
import Schedule.SpeakerScheduleManager;
import Schedule.Event;
import Schedule.TalkSystem;
import UserLogin.*;

import java.util.*;

public class SpeakerMessageManager implements Observer{
    private String speakerEmail;
    private UserStorage allUsers;
    private HashMap<Speaker, SpeakerScheduleManager> speakerScheduleManagerHashMap;
    private HashMap<Event, SignUpAttendeesManager> signUpMap;
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

    public ArrayList<Event> getSpeakerTalks(){return speakerScheduleManagerHashMap.get("""
            we are going to use UserStorage to get user""").getTalkList();
    }

    /**
     * Returns a set of the emails of all attendees signed up for this speaker's talks.
     * @return a HashSet containing Strings representing the emails of all attendees signed up for this speaker's talks
     */

    public HashSet<String> getAllAttendees(){
        HashSet<String> emails = new HashSet<String>();
        for (Event event : getSpeakerTalks()){
            for (User user: signUpMap.get(event).userList){
                emails.add(user.getEmail());
            }
        }
        return emails;
    }

    /**
     * Returns a set of the emails of all attendees signed up for this speaker's talks.
     * @return a HashSet containing Strings representing the emails of all attendees signed up for this speaker's talks
     */

    public ArrayList<String> getAttendeesOfTalk(Event event){
        ArrayList<String> emails = new ArrayList<String>();
        for (User user: signUpMap.get(event).userList){
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
