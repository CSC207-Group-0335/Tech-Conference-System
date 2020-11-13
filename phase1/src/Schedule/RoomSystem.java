package Schedule;

import UserLogin.User;

import java.util.*;
public class RoomSystem extends Observable{

    public ArrayList<Room> roomList;
    public Map<Room, RoomScheduleManager> roomScheduleManagerList;
    public RoomStorage roomStorage;
    public TalkSystem talkSystem;
    public ScheduleSystem scheduleSystem;

    public RoomSystem(){
        this.roomStorage = new RoomStorage();
        this.talkSystem = new TalkSystem();
        this.addObserver(this.talkSystem.talkManager);
        this.addObserver(this.talkSystem.orgScheduleController);
        this.scheduleSystem = new ScheduleSystem();
        this.addObserver(this.scheduleSystem);
        this.roomList = new ArrayList<Room>();
        this.roomScheduleManagerList = new Map<Room, RoomScheduleManager>() {
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

    public void setRoomList(ArrayList<Room> roomlst) {
        this.roomList = roomlst;
        setChanged();
        notifyObservers(roomList);
    }

    public void setRoomScheduleManagerList(Map<Room, RoomScheduleManager> roomSchedList) {
        this.roomScheduleManagerList = roomSchedList;
        setChanged();
        notifyObservers(roomScheduleManagerList);
    }

    //Edit this method to read from .csv file and creates an updated version of Roomstorage
    public void setRoomStorage(String name) {
        this.roomStorage.createRoom(name);
        setRoomList(this.roomStorage.getRoomList());
        setRoomScheduleManagerList(this.roomStorage.getScheduleList());



}
}
