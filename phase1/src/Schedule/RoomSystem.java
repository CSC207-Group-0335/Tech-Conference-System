package Schedule;

import java.util.*;
public class RoomSystem extends Observable{
    public ArrayList<Room> roomList;
    public ArrayList<RoomScheduleManager> roomScheduleManagerList;
    public RoomStorage roomStorage;
    public TalkManager talkManager;
    public OrgScheduleController orgScheduleController;
    public RoomSystem(){
        this.roomStorage = new RoomStorage();
        this.roomList = this.roomStorage.getRoomList();
        this.roomScheduleManagerList = this.roomStorage.getScheduleList();
        this.addObserver(talkManager);
        this.addObserver(orgScheduleController);
    }

}
