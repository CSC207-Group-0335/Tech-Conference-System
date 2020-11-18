package Schedule;

import UserLogin.User;
import UserLogin.UserStorage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ScheduleSystem implements Observer {
    UserStorage storage;
    HashMap<User, UserScheduleManager> userUserScheduleMap;
    TalkManager talkManager;

    public User findUser(String email) {
        for (User user : storage.userList) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public Talk findTalk(String id){
        for (Talk t: talkManager.talkMap.keySet()){
            if(t.getTalkId().equals(id)){
                return t;
            }
        }
        return null;
    }

    public void run(){
        CSVReader fileReader = new CSVReader("phase1/src/Resources/Registration.csv");
        for(ArrayList<String> scheduleData: fileReader.getData()){
            String email = scheduleData.get(0);
            User user = findUser(email);
            UserScheduleManager userSchedule = userUserScheduleMap.get(user);
            for(int i =1; i< scheduleData.size(); i++){
                String id = scheduleData.get(i);
                Talk talk = findTalk(id);
                userSchedule.addTalk(talk);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof UserStorage){
            this.storage = (UserStorage) arg;
        }
        if (arg instanceof HashMap){
            this.userUserScheduleMap = (HashMap<User, UserScheduleManager>) arg;
        }
        if(arg instanceof TalkManager){
            this.talkManager = (TalkManager) arg;
        }
    }
}
