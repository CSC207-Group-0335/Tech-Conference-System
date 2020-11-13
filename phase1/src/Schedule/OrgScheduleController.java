package Schedule;

import UserLogin.*;
import java.util.Date;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class OrgScheduleController extends UserScheduleController implements Observer {

    UserScheduleManager organizer;
    TalkManager talkManager;
    SignUpAttendeesManager signUpList;

    public OrgScheduleController(UserScheduleManager organizer, TalkManager talkManager, SignUpAttendeesManager
            signUpList){
        super(organizer, talkManager,signUpList);
    }

    //as of now, Talk manager does the hard work of creating the talk
    //so don't have to modify anything when I use it in the while loop
    //probably will have to figure out how to convert string time to Date time
    public boolean requestTalks(String title, String speaker, String room, Date time) {
        return this.talkManager.createTalk(title, speaker, room, time);
    }

    //there's also a createRoom in RoomStorage with the parameter capacity
    public void addRoom(String roomName) {
        this.talkManager.roomStorage.createRoom(roomName);
    }

    //can't put anything here since speakerStorage hasn't been made
    public boolean createSpeaker() {
        return false;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
