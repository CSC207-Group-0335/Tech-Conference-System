import java.util.ArrayList;

public class SpeakerScheduleManager extends ScheduleManager{
    Object speaker;
    ArrayList<Talk> talkList;

    public SpeakerScheduleManager(Object speaker){
        this.speaker = speaker;
    }

    public Object getSpeaker() {
        return speaker;
    }
}
