package Schedule;

import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * An abstract class representing the basic responsibilities of each manager use case class.
 */
public abstract class ScheduleManager {
    /**
     * A list of talks.
     */
    public ArrayList<Event> eventList;

    /**
     * Creates a ScheduleManager with an empty talkList.
     */
    public ScheduleManager(){
        this.eventList = new ArrayList<Event>();
    }

    /**
     * Checks for double booking of a talk, assures that there is no talk scheduled at the specified time.
     * @param date The start time.
     * @return A boolean notifying if there are no currently scheduled talks at the specifiec time.
     */
    public boolean checkDoubleBooking(LocalDateTime date){
        for(Event t: eventList){
            if(t.getStartTime().equals(date)){
                return false;
            }}
        return true;
    }

    /**
     * Removes a talk from talkList.
     * @param event The talk you wish to remove.
     * @return A boolean notifying a successful removal of the talk.
     */
    public boolean removeTalk(Event event){
        if(eventList.contains(event)){
            eventList.remove(event);
            return true;
        }
        return false;
    }

    /**
     * Adds a talk to the talkList.
     * @param event The talk you wish to add.
     * @return A boolean notifying a successful addition of the talk.
     */
    public boolean addTalk(Event event){
        if (eventList.contains(event)){
            return false;
        }
        else if(!checkDoubleBooking(event.getStartTime())){
            return false;
        }
        eventList.add(event);
        return true;
    }

    /**
     * Gets the talkList.
     * @return An ArrayList representing the talks of ScheduleManager.
     */
    public ArrayList<Event> getTalkList() {
        return eventList;
    }
}
