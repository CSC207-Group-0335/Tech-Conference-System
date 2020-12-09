package Schedule;

import java.util.ArrayList;

/**
 * Represents a Room.
 */
public class Room {
    String roomName;
    /**
     * The amount of attendees allowed in the room, not including the speaker.
     */
    int capacity = 2;

    /**
     * Creates a room with the specified name and capacity.
     * @param roomName The name of the room.
     * @param capacity The capacity of the room.
     */

    ArrayList<String> eventList;

    public Room(String roomName, int capacity){
        this.roomName = roomName;
        this.capacity = capacity;
        this.eventList = new ArrayList<>();
    }

    /**
     * Creates a room with the specified name.
     * @param roomName The name of the room.
     */
    public Room(String roomName){
        this.roomName = roomName;
        this.eventList = new ArrayList<>();
    }

    /**
     * Gets the room's name.
     * @return A string representing the room's name.
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Gets the capacity of the room.
     * @return An int representing the room's capacity.
     */

    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Returns true if the event was successfully added.
     *
     * @param eventId a String representing a unique even ID
     * @return a boolean representing whether or not the event was successfully created
     */

    public boolean addEvent(String eventId){
        //boolean found = true;
        for (String id : eventList){
            if (id.equals(eventId)){
                return false;
            }
        }
        this.eventList.add(eventId);
        return true;
    }

    /**
     * Returns an event list.
     *
     * @return an ArrayList containing events
     */

    public ArrayList<String> getEventList(){
        return this.eventList;
    }

    /**
     * Changes the capacity of this room.
     *
     * @param capacity an int representing the new capacity.
     */

    public void changeCapacity(int capacity){
        this.capacity = capacity;
    }
}


