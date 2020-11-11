package Schedule;

import Schedule.ScheduleManager;
import UserLogin.Speaker;

import java.util.ArrayList;

public class SpeakerScheduleManager extends ScheduleManager {
    Speaker speaker;
    ArrayList<Talk> talkList;

    public SpeakerScheduleManager(Speaker speaker){
        this.speaker = speaker;
    }

    public Speaker getSpeaker() {
        return speaker;
    }
}
