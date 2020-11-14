package Schedule;

import UserLogin.User;

import java.util.*;
public class RoomSystem extends Observable{

    public ArrayList<Room> roomList;
    public HashMap<Room, RoomScheduleManager> roomScheduleManagerList;
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
        this.roomScheduleManagerList = new HashMap<Room, RoomScheduleManager>();
    }

    public void setRoomList(ArrayList<Room> roomlst) {
        this.roomList = roomlst;
        setChanged();
        notifyObservers(roomList);
    }

    public void setRoomScheduleManagerList(HashMap<Room, RoomScheduleManager> roomSchedList) {
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
