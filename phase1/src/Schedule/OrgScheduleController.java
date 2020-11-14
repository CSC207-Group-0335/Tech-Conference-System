package Schedule;

import UserLogin.*;

import java.time.LocalDateTime;
import java.util.Date;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class OrgScheduleController extends UserScheduleController implements Observer {
    UserScheduleManager organizer;
    TalkManager talkManager;
    RoomStorage roomStorage;


    public OrgScheduleController(UserScheduleManager organizer, TalkManager talkManager){
        super(organizer, talkManager);
        roomStorage = null;
        this.talkManager = talkManager;
    }

    public boolean requestTalks(String title, String talkId,  String speakerEmail, String roomName, LocalDateTime time) {
        return this.talkManager.createTalk(title, talkId, speakerEmail, roomName, time);
    }

    //there's also a createRoom in RoomStorage with the parameter capacity
    public void addRoom(String roomName) {
        this.roomStorage.createRoom(roomName);
    }

    //can't put anything here since speakerStorage hasn't been made
    public boolean createSpeaker() {
        return false;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof RoomStorage){
            this.roomStorage = roomStorage;
        }

    }
}
