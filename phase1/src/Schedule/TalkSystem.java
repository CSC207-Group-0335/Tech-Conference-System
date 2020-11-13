package Schedule;

import MessagingPresenters.MessagingSystem;
import UserLogin.User;

import java.util.*;
public class TalkSystem extends Observable implements Observer{
    public OrgScheduleController orgScheduleController;
    public UserScheduleController userScheduleController;
    public SpeakerScheduleController speakerScheduleController;
    public TalkManager talkManager;
    public MessagingSystem messagingSystem;
    public ScheduleSystem scheduleSystem;
    public Map<User, UserScheduleManager> userScheduleMap;
    public Map<Talk, SignUpAttendeesManager> signUpMap;

    public TalkSystem(){
        this.orgScheduleController = new OrgScheduleController(); //Do we not want only one instance -
        this.addObserver(orgScheduleController); // - of controllers in the program?
        this.userScheduleController = new UserScheduleController();
        this.addObserver(userScheduleController); //do controllers need to
        this.speakerScheduleController = new SpeakerScheduleController();
        this.addObserver(speakerScheduleController);
        this.talkManager = new TalkManager();
        this.messagingSystem = new MessagingSystem();
        this.addObserver(messagingSystem.SpeakerMessengerController); //would be created
        this.scheduleSystem = new ScheduleSystem();
        this.addObserver(scheduleSystem);
        this.signUpMap = new Map<Talk, SignUpAttendeesManager>() {
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
            public SignUpAttendeesManager get(Object key) {
                return null;
            }

            @Override
            public SignUpAttendeesManager put(Talk key, SignUpAttendeesManager value) {
                return null;
            }

            @Override
            public SignUpAttendeesManager remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends Talk, ? extends SignUpAttendeesManager> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<Talk> keySet() {
                return null;
            }

            @Override
            public Collection<SignUpAttendeesManager> values() {
                return null;
            }

            @Override
            public Set<Entry<Talk, SignUpAttendeesManager>> entrySet() {
                return null;
            }
        };
    }

    public void readFile(){}

    public void writeToFile(){}

    public void createSignUpAttendees(){
        for(UserScheduleManager schedule: userScheduleMap.values()){
            for(Talk t: schedule.talkList){
                if(signUpMap.keySet().contains(t)){
                    signUpMap.get(t).addUser(schedule.user);
                }
                else{ //We need a method in talk manager to match every talk with its room/speaker
                    SignUpAttendeesManager signup = new SignUpAttendeesManager(t, talkManager.getroomfortalk(t));
                    signup.addUser(schedule.user);
                    signUpMap.put(t, signup);
                }
            }
            setSignUpMap();
        }
    }

    public void setTalkManager() {
        setChanged();
        notifyObservers(talkManager);
    }

    public void setSignUpMap(){
        setChanged();
        notifyObservers(signUpMap);
    }


    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Map){
            this.userScheduleMap = (Map<User, UserScheduleManager>) arg;
        }
    }
}


