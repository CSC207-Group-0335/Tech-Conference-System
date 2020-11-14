package Schedule;
import Schedule.Room;
import Schedule.RoomScheduleManager;
import Schedule.Talk;
import UserLogin.Speaker;
import UserLogin.TechConferenceSystem;

import java.time.LocalDateTime;
import java.util.*;

public class TalkManager implements Observer {
    HashMap<Talk, ArrayList<Object>> talkMap;
    public HashMap<Room, RoomScheduleManager> roomScheduleMap;
    public HashMap<Speaker, SpeakerScheduleManager> speakerScheduleMap;

    public TalkManager(){
        this.roomScheduleMap = new HashMap<Room, RoomScheduleManager>();
        this.speakerScheduleMap = new HashMap<Speaker, SpeakerScheduleManager>();
        this.talkMap = new HashMap<Talk, ArrayList<Object>>();
    }

    public void addTalk(Talk t, Room r, Speaker s, LocalDateTime d){
        ArrayList<Object> tup = new ArrayList<Object>();
        tup.add(s);
        tup.add(r);
        tup.add(d);
        talkMap.put(t, tup);
    }

    public Room findRoom(String roomName){
        for (Room r : this.roomScheduleMap.keySet()){
            if (r.getRoomName().equals(roomName)){
                return r;
            }
        }
        return null;
    }

    public Speaker findSpeaker(String speakerEmail){
        for (Speaker s : speakerScheduleMap.keySet()){
            if (s.getEmail().equals(speakerEmail)){
                return s;
            }
        }
        return null;
    }

    public boolean createTalk(String talkId, String talkTitle, String speakerEmail, String roomName, LocalDateTime d){
        Room talkRoom = findRoom(roomName);
        Speaker talkSpeaker = findSpeaker(speakerEmail);
        if (talkRoom != null && talkSpeaker != null){
            if (this.speakerScheduleMap.get(talkSpeaker).checkDoubleBooking(d) &&
                this.roomScheduleMap.get(talkRoom).checkDoubleBooking(d)){
                Talk t = new Talk(talkTitle, d, UUID.fromString(talkId));
                this.addTalk(t, talkRoom, talkSpeaker, d);
                this.speakerScheduleMap.get(talkSpeaker).addTalk(t);
                this.roomScheduleMap.get(talkRoom).addTalk(t);
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public boolean removeTalk(Talk t){
        boolean found = this.talkMap.containsKey(t) ;
        if (found){
            this.talkMap.remove(t);
            return true;
        }
        return false;
    }

    public Speaker getTalkSpeaker(Talk t){
        return (Speaker) this.talkMap.get(t).get(0);
    }

    public Room getTalkRoom(Talk t){
        return (Room) this.talkMap.get(t).get(1);
    }

    public LocalDateTime getTalkTime(Talk t){
        return (LocalDateTime) this.talkMap.get(t).get(2);
    }

    public String talkMapStringRepresentation(){
        ArrayList<String> lines = new ArrayList<String>();
        for(Talk t: talkMap.keySet()){
            String line = "Talk: " + t.getTitle() + "Room: " + this.getTalkRoom(t).getRoomName() + "Speaker: "
                    + this.getTalkSpeaker(t).getName() + "Time: " + this.getTalkTime(t).toString();
            lines.add(line);
        }
        String totalString = "";
        for(String line: lines){
            totalString += line + System.lineSeparator();
        }
        return totalString;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof RoomSystem){
            this.roomScheduleMap = (HashMap<Room, RoomScheduleManager>) arg;
        }
        else if(o instanceof TechConferenceSystem){
            this.speakerScheduleMap = (HashMap<Speaker, SpeakerScheduleManager>) arg;
        }
    }
}
