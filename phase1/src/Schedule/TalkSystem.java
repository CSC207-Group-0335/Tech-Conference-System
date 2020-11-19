package Schedule;

import Files.CSVReader;
import Files.CSVWriter;
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
        this.scheduleSystem = new ScheduleSystem(talkManager);
        this.signUpMap = new HashMap<Talk, SignUpAttendeesManager>();
    }
    public void instantiateControllers(User user, Scanner scanner){
        this.addObserver(mainMenuController);
        if (user instanceof Attendee){
            UserScheduleManager userScheduleManager = this.userScheduleMap.get(user);
            this.userScheduleController = new UserScheduleController(userScheduleManager, talkManager,
                    mainMenuController, scanner);
            setUserScheduleController();
            }
        else if (user instanceof Organizer){
            UserScheduleManager userScheduleManager = this.userScheduleMap.get(user);
            this.orgScheduleController = new OrgScheduleController(userScheduleManager, talkManager,
                    mainMenuController, scanner);
            this.addObserver(orgScheduleController);
            setOrgScheduleController();
        }
        else{
            SpeakerScheduleManager speakerScheduleManager = this.speakerScheduleMap.get(user);
            this.speakerScheduleController = new SpeakerScheduleController(speakerScheduleManager, talkManager,
                    mainMenuController, scanner);
            setSpeakerScheduleController();
        }

    }

    public void run() {
        //Moved Observers NOV 15
        if (messagingSystem.speakerMessengerController != null) {
            this.addObserver(messagingSystem.speakerMessengerController); //would be created
        }
        if (messagingSystem.speakerMessengerController != null) {
            this.addObserver(messagingSystem.speakerMessengerController.userInfo);
        }
        CSVReader fileReader = new CSVReader("phase1/src/Resources/Talks.csv");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for(ArrayList<String> talkData: fileReader.getData()){
            this.talkManager.createTalk(talkData.get(0), talkData.get(1), talkData.get(2),
                    talkData.get(3), LocalDateTime.parse(talkData.get(4), formatter));
        }
        setTalkManager();
        messagingSystem.run();
        scheduleSystem.run();
        createSignUpAttendees();
        if (this.user instanceof Attendee) {
            userScheduleController.setSignUpMap(signUpMap);
        }
        if (this.user instanceof Organizer) {
            orgScheduleController.setSignUpMap(signUpMap);
        }
    }

    public void save() {
        CSVWriter csvWriter = new CSVWriter();
        //csvWriter.writeToTalks("phase1/src/Resources/Talks.csv", this.talkManager.talkMap); //Not implemented yet
    }

    public void createSignUpAttendees(){
        for(UserScheduleManager schedule: userScheduleMap.values()){
            if (schedule.getTalkList() != null){
            for(Talk t: schedule.getTalkList()){
                if(signUpMap.keySet().contains(t)){
                    signUpMap.get(t).addUser(schedule.user);
                }
                else{ //We need a method in talk manager to match every talk with its room/speaker
                    SignUpAttendeesManager signup = new SignUpAttendeesManager(t, talkManager.getTalkRoom(t).capacity);
                    signup.addUser(schedule.user);
                    signUpMap.put(t, signup);
                }}
            }}
            setSignUpMap();
        }

    public void setTalkManager() {
        setChanged();
        notifyObservers(talkManager);
    }

    public TalkManager getTalkManager() {
        return talkManager;
    }

    public void setSignUpMap(){
        setChanged();
        notifyObservers(signUpMap);
    }

    public void setOrgScheduleController(){
        setChanged();
        notifyObservers(orgScheduleController);
    }

    public void setUserScheduleController(){
        setChanged();
        notifyObservers(userScheduleController);
    }

    public void setSpeakerScheduleController(){
        setChanged();
        notifyObservers(speakerScheduleController);
    }

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
