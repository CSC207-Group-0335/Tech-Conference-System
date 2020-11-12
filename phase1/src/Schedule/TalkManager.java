package Schedule;
import Schedule.Room;
import Schedule.RoomScheduleManager;
import Schedule.Talk;
import UserLogin.Speaker;

import java.util.*;

public class TalkManager {
    RoomStorage roomStorage;
    Map<Talk, ArrayList<Object>> talkMap;
    Map<Room, RoomScheduleManager> roomScheduleList;
    public TalkManager(){
        this.roomStorage = new RoomStorage();
        this.roomScheduleList = this.roomStorage.getScheduleList();
        this.talkMap = new Map<Talk, ArrayList<Object>>() {
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
            public ArrayList<Object> get(Object key) {
                return null;
            }

            @Override
            public ArrayList<Object> put(Talk key, ArrayList<Object> value) {
                return null;
            }

            @Override
            public ArrayList<Object> remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends Talk, ? extends ArrayList<Object>> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<Talk> keySet() {
                return null;
            }

            @Override
            public Collection<ArrayList<Object>> values() {
                return null;
            }

            @Override
            public Set<Entry<Talk, ArrayList<Object>>> entrySet() {
                return null;
            }

            @Override
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }
        };
    }
    public boolean createTalk(String title, String speaker, String room, Date d){
        ArrayList<Room> roomList = this.roomStorage.getRoomList();
        //ArrayList<Speaker> speakerList = this.speakerStorage.getSpeakerList();
        Room r = new Room("", 0);
        Speaker s = new Speaker("", "", "");
        for (Room room_iterator : roomList){
            if (room_iterator.getRoomName().equals(room)){
                r = room_iterator;
                break;
            }
        }
//        for (Speaker speaker_iterator : speakerList){
//            if (speaker_iterator.getSpeakerName().equals(speaker)){
//                s = speaker_iterator;
//                break;
//            }
//        }
        RoomScheduleManager roomScheduleManager = this.roomScheduleList.get(r);
        //SpeakerScheduleManager speakerScheduleManager = this.speakerScheduleList.get(s);
        ArrayList<Object> tup = new ArrayList<Object>();
        tup.add(s);
        tup.add(r);
        tup.add(d);
        Talk t = new Talk(title, d);
        boolean addedToRoomScheduleManager = roomScheduleManager.addTalk(t);
        //boolean addedToSpeakerScheduleManager = speakerScheduleManager.addTalk(t);
        if (addedToRoomScheduleManager){
            this.talkMap.put(t, tup);
            return true;
        }
        return false;
    }
    public boolean removeTalk(Talk t){
        boolean found = this.talkMap.containsKey(t);
        if (found){
            this.talkMap.remove(t);
            return true;
        }
        return false;


    }

}
