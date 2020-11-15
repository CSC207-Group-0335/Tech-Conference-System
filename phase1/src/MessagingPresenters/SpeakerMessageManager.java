package MessagingPresenters;

import Schedule.SignUpAttendeesManager;
import Schedule.SpeakerScheduleManager;
import Schedule.Talk;
import Schedule.TalkSystem;
import UserLogin.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class SpeakerMessageManager implements Observer{
    private Speaker speaker;
    private UserStorage allUsers;
    private HashMap<Speaker, SpeakerScheduleManager> speakerScheduleManagerHashMap;
    private HashMap<Talk, SignUpAttendeesManager> signUpMap;

    /**
     * A user is needed to create an instance of CanMessageManager.
     * @param speaker the speaker whose messages will be managed
     */

    public SpeakerMessageManager(Speaker speaker) {
        this.speaker = speaker;
    }

    public SpeakerScheduleManager getSpeakerSchedule(){
        return speakerScheduleManagerHashMap.get(speaker);
    }

    public HashMap getSpeakersMap(){
        return this.speakerScheduleManagerHashMap;
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
    }
}
