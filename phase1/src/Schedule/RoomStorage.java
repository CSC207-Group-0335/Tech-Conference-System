package Schedule;

import Schedule.Room;
import Schedule.RoomScheduleManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class RoomStorage {
    ArrayList<Room> roomList;
    ArrayList<RoomScheduleManager>scheduleList;
    //Map<Room, RoomScheduleManager> scheduleList;

    public RoomStorage(){
        this.roomList = new ArrayList<>();
        this.scheduleList = new ArrayList<>();
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
            scheduleList.add(rScheduleManager);
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
            scheduleList.add(rScheduleManager);
        }
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }

    public ArrayList<RoomScheduleManager> getScheduleList() {
        return scheduleList;
    }
}


