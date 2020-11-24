package Schedule;

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
    public Room(String roomName, int capacity){
        this.roomName = roomName;
        this.capacity = capacity;
    }

    /**
     * Creates a room with the specified name.
     * @param roomName The name of the room.
     */
    public Room(String roomName){
        this.roomName = roomName;
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
}


