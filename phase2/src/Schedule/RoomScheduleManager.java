package Schedule;

import java.util.ArrayList;

/**
 * Manages a Room.
 */
public class RoomScheduleManager extends ScheduleManager {
    Room room;
    /**
     * The list of talks being given at the room.
     */
    ArrayList<Talk> talkList;

    /**
     * Creates a RoomScheduleManager with the specified room.
     * @param room The room.
     */
    public RoomScheduleManager(Room room){
        this.room = room;
    }

    /**
     * Gets the room.
     * @return A room representing the room of RoomScheduleManager.
     */
    public Room getRoom() {
        return room;
    }

}
