package Schedule;

import UserLogin.Speaker;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Quartet {
    Room room;
    ArrayList<Speaker> speakers;
    LocalDateTime start;
    LocalDateTime end;

    public Quartet(Room room, ArrayList<Speaker> speakers, LocalDateTime start, LocalDateTime end){
        this.room =room;
        this.speakers = speakers;
        this.start = start;
        this.end = end;
    }

    public Room getRoom() {
        return room;
    }

    public ArrayList<Speaker> getSpeaker() {
        return speakers;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public LocalDateTime getStart() {
        return start;
    }
}
