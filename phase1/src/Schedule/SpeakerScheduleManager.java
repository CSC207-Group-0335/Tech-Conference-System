package Schedule;

import Schedule.ScheduleManager;
import UserLogin.Speaker;

import java.util.ArrayList;

/**
 * Manages a speaker.
 */
public class SpeakerScheduleManager extends ScheduleManager {
    Speaker speaker;
    /**
     * The list of talks the speaker is speaking at.
     */
    ArrayList<Talk> talkList;

    /**
     * Creates a SpeakerScheduleManager with the specified speaker.
     * @param speaker The Speaker.
     */
    public SpeakerScheduleManager(Speaker speaker){
        this.speaker = speaker;
    }

    /**
     * Gets the speaker.
     * @return A speaker representing the speaker of SpeakerScheduleManager.
     */
    public Speaker getSpeaker() {
        return speaker;
    }
}
