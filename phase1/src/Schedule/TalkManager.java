package Schedule;
import Schedule.Room;
import Schedule.RoomScheduleManager;
import Schedule.Talk;
import UserLogin.Speaker;

import java.util.*;

public class TalkManager implements Observer {
    //Map<Talk, ArrayList<Object>> talkMap;
    public Map<Room, RoomScheduleManager> roomScheduleMap;
    public Map<Speaker, SpeakerScheduleManager> speakerScheduleMap;
    public TalkManager(){
        this.speakerScheduleMap = new Map<Speaker, SpeakerScheduleManager>() {
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
            public SpeakerScheduleManager get(Object key) {
                return null;
            }

            @Override
            public SpeakerScheduleManager put(Speaker key, SpeakerScheduleManager value) {
                return null;
            }

            @Override
            public SpeakerScheduleManager remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends Speaker, ? extends SpeakerScheduleManager> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<Speaker> keySet() {
                return null;
            }

            @Override
            public Collection<SpeakerScheduleManager> values() {
                return null;
            }

            @Override
            public Set<Entry<Speaker, SpeakerScheduleManager>> entrySet() {
                return null;
            }
        };
        this.roomScheduleMap = new Map<Room, RoomScheduleManager>() {
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


//    public boolean createTalk(String title, String speaker, String room, Date d){
//        ArrayList<Room> roomList = this.roomStorage.getRoomList();
//        //ArrayList<Speaker> speakerList = this.speakerStorage.getSpeakerList();
//        Room r;
//        Speaker s;
//        for (Room room_iterator : roomList){
//            if (room_iterator.getRoomName().equals(room)){
//                r = room_iterator;
//                break;
//            }
//        }
////        for (Speaker speaker_iterator : speakerList){
////            if (speaker_iterator.getSpeakerName().equals(speaker)){
////                s = speaker_iterator;
////                break;
////            }
////        }
//        RoomScheduleManager roomScheduleManager;
//        //SpeakerScheduleManager speakerScheduleManager;
//        for (RoomScheduleManager rsm : this.roomScheduleList){
//            if (rsm.getRoom().equals(r)){
//                roomScheduleManager = rsm;
//                break;
//            }
//        }
////        ArrayList<Object> tup = new ArrayList<Object>();
////        tup.add(s);
////        tup.add(r);
////        tup.add(d);
////        Talk t = new Talk(title, d);
////        boolean addedToRoomScheduleManager = roomScheduleManager.addTalk(t);
////        //boolean addedToSpeakerScheduleManager = speakerScheduleManager.addTalk(t);
////        if (addedToRoomScheduleManager){
////            this.talkMap.put(t, tup);
////            return true;
////        }
////        return false;
////    }
////    public boolean removeTalk(Talk t){
////        boolean found = this.talkMap.containsKey(t) ;
////        if (found){
////            this.talkMap.remove(t);
////            return true;
////        }
////        return false;
////
////
//    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Map);
    }
}
