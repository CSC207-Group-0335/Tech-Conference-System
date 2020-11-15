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
     * A user is needed to create an instance of CanMessageManager.
     * @param speaker the speaker whose messages will be managed
     */

    public SpeakerMessageManager(Speaker speaker) {
        this.speaker = speaker;
    }

    public ArrayList<Talk> getSpeakerTalks(){
        return speakerScheduleManagerHashMap.get(speaker).getTalkList();
    }

    public HashSet<String> getAllAttendees(){
        HashSet<String> emails = new HashSet<String>();
        for (Talk talk: getSpeakerTalks()){
            for (User user: signUpMap.get(talk).userList){
                emails.add(user.getEmail());
            }
        }
        return emails;
    }

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

    public boolean canReply(String email){
        if (getAllSenders().contains(email)){
            return true;
        }
        return false;
    }


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
