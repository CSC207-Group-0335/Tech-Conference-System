package Schedule;
import Schedule.Room;
import Schedule.RoomScheduleManager;
import Schedule.Talk;
import UserLogin.Speaker;

import java.util.*;

public class TalkManager implements Observer {
    public RoomStorage roomStorage;
    //public SpeakerStorage speakerStorage;
    //Map<Talk, ArrayList<Object>> talkMap;
    public ArrayList<RoomScheduleManager> roomScheduleList;
    public TalkManager(){
        this.roomStorage = new RoomStorage();
        //this.speakerStorage = new SpeakerStorage();
        //this.roomScheduleList = this.roomStorage.getScheduleList();
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

    }
}
