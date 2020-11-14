package Schedule;

import Schedule.Room;
import Schedule.RoomScheduleManager;

import java.util.*;

public class RoomStorage {
    ArrayList<Room> roomList;
    HashMap<Room, RoomScheduleManager> scheduleList;

    public RoomStorage(){
        this.roomList = new ArrayList<>();
        this.scheduleList = new HashMap<Room, RoomScheduleManager>();
    }

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
            scheduleList.put(room, rScheduleManager);
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
            scheduleList.put(room, rScheduleManager);
        }
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }

    public HashMap<Room, RoomScheduleManager> getScheduleList() {
        return scheduleList;
    }
}


