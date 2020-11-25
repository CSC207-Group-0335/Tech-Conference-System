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

    ArrayList<String> talkList;

    public Room(String roomName, int capacity){
        this.roomName = roomName;
        this.capacity = capacity;
        this.talkList = new ArrayList<>();
    }

    /**
     * Creates a room with the specified name.
     * @param roomName The name of the room.
     */
    public Room(String roomName){
        this.roomName = roomName;
        this.talkList = new ArrayList<>();
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

    public boolean addTalk(String talkId){
        //boolean found = true;
        for (String id : talkList){
            if (id.equals(talkId)){
                return false;
            }
        }
        this.talkList.add(talkId);
        return true;
    }
    public ArrayList<String> getTalkList(){
        return this.talkList;
    }
}


