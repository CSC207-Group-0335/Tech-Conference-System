package Schedule;

import Schedule.Room;
import Schedule.RoomScheduleManager;

import java.util.ArrayList;

public class RoomStorage {
    ArrayList<Room> roomList;

    public void createRoom(String roomName) {
        boolean bool = true;
        for (Room r : roomList) {
            if (r.getRoomName() == roomName) {
                bool = false;
            }
        }
        if (bool) {
            Room room = new Room(roomName);
            roomList.add(room);
            RoomScheduleManager rScheduleManager = new RoomScheduleManager(room);
        }
    }

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
        }
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }
}

