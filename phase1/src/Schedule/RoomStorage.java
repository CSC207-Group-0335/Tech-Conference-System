package Schedule;

import Schedule.Room;
import Schedule.RoomScheduleManager;

import java.util.*;

/**
 * Stores all the rooms for the conference.
 */
public class RoomStorage {
    /**
     * A list of all the rooms.
     */
    ArrayList<Room> roomList;
    /**
     * A mapping of rooms to its corresponding RoomScheduleManager which checks for double booking.
     */
    HashMap<Room, RoomScheduleManager> scheduleList;

    /**
     * Creates a room storage.
     */
    public RoomStorage(){
        this.roomList = new ArrayList<>();
        this.scheduleList = new HashMap<Room, RoomScheduleManager>();
    }

    /**
     * Creates a room with the specified name. And adds the rooms to the room list  and schedule list.
     * @param roomName The name of the room.
     * @return A boolean notifying if the room was successfully created.
     */
    public boolean createRoom(String roomName) {
        boolean bool = true;
        for (Room r : roomList) {
            if (r.getRoomName().equals(roomName)) {
                bool = false;
            }
        }
        if (bool) {
            Room room = new Room(roomName);
            roomList.add(room);
            RoomScheduleManager rScheduleManager = new RoomScheduleManager(room);
            scheduleList.put(room, rScheduleManager);
            return true;
        }
        return false;
    }

    /**
     * Creates a room with the specified name and capacity. And adds the rooms to the room list  and schedule list.
     * @param roomName The name of the room.
     * @param capacity The capacity of the room.
     */
    public void createRoom(String roomName, int capacity) {
        boolean bool = true;
        for (Room r : roomList) {
            if (r.getRoomName() == roomName) {
                bool = false;
            }
        }
        if (bool) {
            Room room = new Room(roomName, capacity);
            roomList.add(room);
            RoomScheduleManager rScheduleManager = new RoomScheduleManager(room);
            scheduleList.put(room, rScheduleManager);
        }
    }

    /**
     * Gets the list of rooms.
     * @return An ArrayList representing the roomList of RoomStorage.
     */
    public ArrayList<Room> getRoomList() {
        return roomList;
    }

    /**
     * Gets the schedule list.
     * @return A HashMap representing the scheduleList of RoomStorage.
     */
    public HashMap<Room, RoomScheduleManager> getScheduleList() {
        return scheduleList;
    }
}


