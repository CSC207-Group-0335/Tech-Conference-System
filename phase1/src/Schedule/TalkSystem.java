package Schedule;

import MessagingPresenters.MessagingSystem;
import UserLogin.*;

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
    public MainMenuController mainMenuController;

    public TalkSystem(){
        this.talkManager = new TalkManager();
        this.messagingSystem = new MessagingSystem();
        this.scheduleSystem = new ScheduleSystem();
    }
    public void instantiateControllers(User user){
        if (user instanceof Attendee){
            UserScheduleManager userScheduleManager = this.userScheduleMap.get(user);
            this.userScheduleController = new UserScheduleController(userScheduleManager, talkManager,
                    mainMenuController, signUpMap);
            }
        else if (user instanceof Organizer){
            UserScheduleManager userScheduleManager = this.userScheduleMap.get(user);
            this.orgScheduleController = new OrgScheduleController(userScheduleManager, talkManager,
                    mainMenuController, signUpMap);
            this.addObserver(orgScheduleController);
        }
        else{
            SpeakerScheduleManager speakerScheduleManager = this.speakerScheduleMap.get(user);
            this.speakerScheduleController = new SpeakerScheduleController(speakerScheduleManager, talkManager,
                    mainMenuController);
        }
    }

    public void run(){
        //Moved Observers NOV 15
        this.addObserver(messagingSystem.speakerMessengerController); //would be created
        this.addObserver(scheduleSystem);
        this.addObserver(messagingSystem.speakerMessengerController.userInfo);
        CSVReader fileReader = new CSVReader("Talks.csv");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for(ArrayList<String> talkData: fileReader.getData()){
            this.talkManager.createTalk(talkData.get(0), talkData.get(1), talkData.get(2),
                    talkData.get(3), LocalDateTime.parse(talkData.get(4), formatter));
        }
        setTalkManager();
        messagingSystem.run();
        scheduleSystem.run();
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

    //Add Setters for controllers!!


    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof HashMap){
            if(((HashMap<?, ?>) arg).keySet().toArray()[0] instanceof Speaker){
                this.speakerScheduleMap = (HashMap<Speaker, SpeakerScheduleManager>) arg;
            }
            else{this.userScheduleMap = (HashMap<User, UserScheduleManager>) arg;}
        }
        //No longer need to observe user, because of changes made in the LogInController
        //if (arg instanceof User){
          //  this.user = (User) arg;
        //}
        if (arg instanceof MainMenuController){
            this.mainMenuController = (MainMenuController) arg;
        }

    }
}
