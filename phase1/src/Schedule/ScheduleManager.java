package Schedule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

/**
 * An abstract class representing the basic responsibilities of each manager use case class.
 */
public abstract class ScheduleManager {
    /**
     * A list of talks.
     */
    public ArrayList<Talk> talkList;

    /**
     * Creates a ScheduleManager with an empty talkList.
     */
    public ScheduleManager(){
        this.talkList = new ArrayList<Talk>();
    }

    /**
     * Checks for double booking of a talk, assures that there is no talk scheduled at the specified time.
     * @param date The start time.
     * @return A boolean notifying if there are no currently scheduled talks at the specifiec time.
     */
    public boolean checkDoubleBooking(LocalDateTime date){
        for(Talk t: talkList){
            if(t.getStartTime() == date){
                return false;
            }}
        return true;
    }

    /**
     * Removes a talk from talkList.
     * @param talk The talk you wish to remove.
     * @return A boolean notifying a successful removal of the talk.
     */
    public boolean removeTalk(Talk talk){
        if(talkList.contains(talk)){
            talkList.remove(talk);
            return true;
        }
        return false;
    }

    /**
     * Adds a talk to the talkList.
     * @param talk The talk you wish to add.
     * @return A boolean notifying a successful addition of the talk.
     */
    public boolean addTalk(Talk talk){
        if (talkList.contains(talk)){
            return false;
        }
        else if(!checkDoubleBooking(talk.getStartTime())){
            return false;
        }
        talkList.add(talk);
        return true;
    }

    /**
     * Gets the talkList.
     * @return An ArrayList representing the talks of ScheduleManager.
     */
    public ArrayList<Talk> getTalkList() {
        return talkList;
    }
}
