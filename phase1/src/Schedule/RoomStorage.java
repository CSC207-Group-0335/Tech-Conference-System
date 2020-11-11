package Schedule;

import Schedule.Room;
import Schedule.RoomScheduleManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class RoomStorage {
    ArrayList<Room> roomList;
    Map<Room, RoomScheduleManager> scheduleList;

    public RoomStorage(){
        this.roomList = new ArrayList<Room>();
        this.scheduleList = new Map<Room, RoomScheduleManager>(){
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public RoomScheduleManager get(Object key) {
                return null;
            }

            @Override
            public RoomScheduleManager put(Room key, RoomScheduleManager value) {
                return null;
            }

            @Override
            public RoomScheduleManager remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends Room, ? extends RoomScheduleManager> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<Room> keySet() {
                return null;
            }

            @Override
            public Collection<RoomScheduleManager> values() {
                return null;
            }

            @Override
            public Set<Entry<Room, RoomScheduleManager>> entrySet() {
                return null;
            }
        };
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

    public Map<Room, RoomScheduleManager> getScheduleList() {
        return scheduleList;
    }
}


