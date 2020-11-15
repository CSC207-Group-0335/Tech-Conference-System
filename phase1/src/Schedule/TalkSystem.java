package Schedule;

import MessagingPresenters.MessagingSystem;
import UserLogin.Speaker;
import UserLogin.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class TalkSystem extends Observable implements Observer{
    public OrgScheduleController orgScheduleController;
    public UserScheduleController userScheduleController;
    public SpeakerScheduleController speakerScheduleController;
    public TalkManager talkManager;
    public MessagingSystem messagingSystem;
    public ScheduleSystem scheduleSystem;
    public User user;
    public HashMap<User, UserScheduleManager> userScheduleMap;
    public HashMap<Speaker, SpeakerScheduleManager> speakerScheduleMap;
    public HashMap<Talk, SignUpAttendeesManager> signUpMap;

    public TalkSystem(){
        this.talkManager = new TalkManager();
        this.messagingSystem = new MessagingSystem();
        this.addObserver(messagingSystem.SpeakerMessengerController); //would be created
        this.scheduleSystem = new ScheduleSystem();
        this.addObserver(scheduleSystem);
        this.signUpMap = new HashMap<Talk, SignUpAttendeesManager>();
        this.orgScheduleController = new OrgScheduleController(talkManager); //Do we not want only one instance -
        this.addObserver(orgScheduleController); // - of controllers in the program?
        this.userScheduleController = new UserScheduleController(talkManager);
        this.addObserver(userScheduleController); //do controllers need to
        this.speakerScheduleController = new SpeakerScheduleController(talkManager);
        this.addObserver(speakerScheduleController);
    }

    public void run(){
        CSVReader fileReader = new CSVReader("Talks.csv");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for(ArrayList<String> talkData: fileReader.getData()){
            this.talkManager.createTalk(talkData.get(0), talkData.get(1), talkData.get(2),
                    talkData.get(3), LocalDateTime.parse(talkData.get(4), formatter));
        }
        setTalkManager();
    }

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
            if(!((HashMap<?, ?>) arg).isEmpty()){

            }
            else{}
            this.userScheduleMap = (HashMap<User, UserScheduleManager>) arg;
        }
        if (arg instanceof User){
            this.user = (User) arg;
        }
    }
}


