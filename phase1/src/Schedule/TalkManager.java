package Schedule;
import Schedule.Room;
import Schedule.RoomScheduleManager;
import Schedule.Talk;
import UserLogin.Speaker;

import java.util.*;

public class TalkManager {
    RoomStorage roomStorage;
    Map<Talk, ArrayList<Object>> talkMap;
    Map<Room, RoomScheduleManager> scheduleList;
    public TalkManager(){
        this.roomStorage = new RoomStorage();
        this.scheduleList = this.roomStorage.getScheduleList();
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
    public void createTalk(Talk t, Speaker s, Room r, Date d){
        RoomScheduleManager roomScheduleManager = this.scheduleList.get(r);
        if (roomScheduleManager.checkDoubleBooking(d)) {
            ArrayList<Object> tup = new ArrayList<Object>();
            tup.add(s);
            tup.add(r);
            tup.add(d);
            this.talkMap.put(t, tup);
        }
    }
    public void removeTalk(Talk t){
        this.talkMap.remove(t);

    }

}
