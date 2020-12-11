package Schedule;

import UserLogin.Speaker;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A class representing event features.
 */

public class EventFeatures {
    Room room;
    ArrayList<Speaker> speakers;
    LocalDateTime start;
    LocalDateTime end;

    /**
     *  A room, list of speakers, start time, and end time are required to create an instance of EventFeatures.
     *
     * @param room a Room
     * @param speakers an ArrayList containing speakers
     * @param start the start time
     * @param end the end time
     */

    public EventFeatures(Room room, ArrayList<Speaker> speakers, LocalDateTime start, LocalDateTime end){
        this.room =room;
        this.speakers = speakers;
        this.start = start;
        this.end = end;
    }

    /**
     * Returns a room.
     *
     * @return a Room
     */

    public Room getRoom() {
        return room;
    }

    /**
     * Returns a list of speakers.
     *
     * @return an ArrayList containing speakers
     */

    public ArrayList<Speaker> getSpeaker() {
        return speakers;
    }

    /**
     * Returns end time.
     *
     * @return a LocalDateTime object representing the end time
     */

    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Returns start time.
     *
     * @return a LocalDateTime object representing the start time
     */

    public LocalDateTime getStart() {
        return start;
    }
}
