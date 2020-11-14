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
    public HashMap<User, UserScheduleManager> userScheduleMap;
    public HashMap<Talk, SignUpAttendeesManager> signUpMap;

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
        this.signUpMap = new HashMap<Talk, SignUpAttendeesManager>();
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
                    SignUpAttendeesManager signup = new SignUpAttendeesManager(t, talkManager.getTalkRoom(t).capacity);
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
        if (arg instanceof HashMap){
            this.userScheduleMap = (HashMap<User, UserScheduleManager>) arg;
        }
    }
}


