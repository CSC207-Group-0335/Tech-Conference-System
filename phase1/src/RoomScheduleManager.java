import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;

public class RoomScheduleManager extends ScheduleManager{
    Room room;
    ArrayList<Talk> talkList;

    public RoomScheduleManager(Room room){
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }
}
