package Schedule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public abstract class ScheduleManager {
    ArrayList<Talk> talkList;

    public ScheduleManager(){
        this.talkList = new ArrayList<Talk>();
    }

    public boolean checkDoubleBooking(LocalDateTime date){
        for(Talk t: talkList){
            if(t.getStartTime() == date){
                return false;
            }}
        return true;
    }

    public boolean removeTalk(Talk talk){
        if(talkList.contains(talk)){
            talkList.remove(talk);
            return true;
        }
        return false;
    }

    public boolean addTalk(Talk talk){
        if (talkList.contains(talk)){
            return false;
        }
        else if(!checkDoubleBooking(talk.getStartTime())){
            return false;
        }
        talkList.add(talk);
        return true;
    }

    public ArrayList<Talk> getTalkList() {
        return talkList;
    }
}
