package Schedule;

import Files.CSVReader;
import Files.CSVWriter;
import UserLogin.*;

import java.util.*;

/**
 * Gateway Class that reads a .csv file for all user schedules and adds talks to the appropriate user schedule manager
 * and speaker schedule manager for for every user/speaker.
 */
public class ScheduleSystem implements Observer {
    UserStorage storage;
    HashMap<User, UserScheduleManager> userScheduleMap;
    TalkManager talkManager;

    /**
     * Creates a new ScheduleSystem with the specified talkManager.
     * @param talkManager The talkManager.
     */
    public ScheduleSystem(TalkManager talkManager){
        this.talkManager = talkManager;
    }

    /**
     * Finds a user with the specified email.
     * @param email The email of the user.
     * @return A user representing the user with the given email or null if there is no such user.
     */
    public User findUser(String email) {
        for (User user : storage.getUserList()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Finds a talk with the specified id.
     * @param id The string id of the talk.
     * @return A talk representing the talk with the given id or null if there is no such talk.
     */
    public Talk findTalk(String id){
        for (Talk t: talkManager.talkMap.keySet()){
            if(t.getTalkId().equals(id)){
                return t;
            }
        }
        return null;
    }

    /**
     * Reads the cvs and creates the userScheduleMap.
     */
    public void run(){
        CSVReader fileReader = new CSVReader("src/Resources/Registration.csv");
        for(ArrayList<String> scheduleData: fileReader.getData()){
            String email = scheduleData.get(0);
            User user = findUser(email);
            UserScheduleManager userSchedule = userScheduleMap.get(user);
            if (user instanceof Organizer || user instanceof Attendee){
            for(int i =1; i< scheduleData.size(); i++){
                String id = scheduleData.get(i);
                Talk talk = findTalk(id);
                if (talk != null) {
                    userSchedule.addTalk(talk);
                }
            }}}
        }

    /**
     * Method to write the changes to the Registration.csv, called in MainMenuController.logout().
     */
    public void save() {
        CSVWriter csvWriter = new CSVWriter();
        csvWriter.writeToRegistration("phase1/src/Resources/Registration.csv", this.getUserScheduleMap());
    }

    /**
     * Gets the userScheduleMap.
     * @return A HashMap representing the userScheduleMap
     */
    public HashMap<User, UserScheduleManager> getUserScheduleMap() {
        return userScheduleMap;
    }
    /**
     * Updating ScheduleSystem's storage, userScheduleMap, and talkManager.
     * @param o An Observable.
     * @param arg An Object.
     */
    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof UserStorage){
            this.storage = (UserStorage) arg;
        }
        if (arg instanceof HashMap){
            if (((HashMap<User, UserScheduleManager>) arg).keySet().toArray()[0] instanceof Organizer ||
            ((HashMap<User, UserScheduleManager>) arg).keySet().toArray()[0] instanceof Attendee) {
                this.userScheduleMap = (HashMap<User, UserScheduleManager>) arg;
            }
        }
        if(arg instanceof TalkManager){
            this.talkManager = (TalkManager) arg;
        }
    }
}
