package Schedule;

import java.util.*;

/**
 * Stores all the rooms for the conference.
 */
public class RoomManager {
    /**
     * A list of all the rooms.
     */
    ArrayList<Room> roomList;
    ArrayList<String> roomNameList;
    HashMap<String, Room> roomNameMap;

    /**
     * Creates a room storage.
     */
    public RoomManager(){
        this.roomList = new ArrayList<>();
        this.roomNameList = new ArrayList<>();
        this.roomNameMap = new HashMap<String, Room>();
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
                break;
            }
        }
        if (bool) {
            Room room = new Room(roomName);
            roomList.add(room);
            roomNameList.add(roomName);
//            RoomScheduleManager rScheduleManager = new RoomScheduleManager(room);
//            scheduleList.put(room, rScheduleManager);
            roomNameMap.put(roomName, room);
            return true;
        }
        return false;
    }

    /**
     * Creates a room with the specified name and capacity. And adds the rooms to the room list  and schedule list.
     * @param roomName The name of the room.
     * @param capacity The capacity of the room.
     */
    public boolean createRoom(String roomName, int capacity) {
        boolean bool = true;
        for (Room r : roomList) {
            if (r.getRoomName().equals(roomName)) {
                bool = false;
                break;
            }
        }
        if (bool) {
            Room room = new Room(roomName, capacity);
            roomList.add(room);
            roomNameList.add(roomName);
//            RoomScheduleManager rScheduleManager = new RoomScheduleManager(room);
//            scheduleList.put(room, rScheduleManager);
            roomNameMap.put(roomName, room);
            return true;
        }
        return false;
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

    public boolean addEvent(String roomName, String EventId){
        Room r = roomNameMap.get(roomName);
        boolean found = true;
        for (String id : r.getEventList()){
            if (id.equals(EventId)){
                found = false;
            }
        }
        if (found){
            r.addEvent(EventId);
            return true;
        }
        return false;
    }

    /**
     * Removes an event.
     *
     * @param roomName a String representing a room name
     * @param eventId a String representing an event ID
     */

    public void removeEvent(String roomName, String eventId){
        Room r = roomNameMap.get(roomName);
        for (String id : r.getEventList()){
            if (id.equals(eventId)){
                r.getEventList().remove(id);
                return;
            }
        }
    }

    /**
     * Returns the room with the name </roomName>, if it exists.
     *
     * @param roomName a String representing the name of the room
     * @return a Room
     */

    public Room nameToRoom(String roomName){
        if (roomNameMap.containsKey(roomName)){
            return roomNameMap.get(roomName);
        }
        return null;
    }

    /**
     * Returns a list of event IDs scheduled to occur in room </roomName>.
     *
     * @param roomName the name of the room
     * @return an ArrayList containing event IDs
     */

    public ArrayList<String> roomNameToEventIds(String roomName){
        Room room = nameToRoom(roomName);
        return room.getEventList();
    }

    /**
     * Returns the capacity of a room.
     *
     * @param roomName the name of the room
     * @return the capacity of the room
     */

    public int roomNameToCapacity(String roomName){
        Room room = nameToRoom(roomName);
        return room.getCapacity();
    }

    /**
     * Returns a list of room names.
     *
     * @return an ArrayList containing room names
     */

    public ArrayList<String> getRoomNameList() {
        return this.roomNameList;
    }

}



