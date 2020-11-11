public class Room {
    String roomName;
    int capacity = 2;

    public Room(String roomName, int capacity){
        this.roomName = roomName;
        this.capacity = capacity;
    }

    public Room(String roomName){
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getCapacity() {
        return this.capacity;
    }
}
