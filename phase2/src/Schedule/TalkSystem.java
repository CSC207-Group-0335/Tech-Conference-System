package Schedule;

import Files.CSVReader;
import Files.CSVWriter;
import MessagingPresenters.MessagingSystem;
import UserLogin.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * A gateway class that reads a .csv file for
 * all accounts with their credentials and requests creating talks (from TalkManager)
 */
public class TalkSystem extends Observable implements Observer{
    public OrgScheduleController orgScheduleController;
    public UserScheduleController userScheduleController;
    public SpeakerScheduleController speakerScheduleController;
    public EventManager eventManager;
    public MessagingSystem messagingSystem;
    public ScheduleSystem scheduleSystem;
    public String userEmail;
    public UserStorage userStorage;
    public MainMenuController mainMenuController;

    /**
     * creates a new TalkSystem.
     */
    public TalkSystem(UserStorage userStorage, RoomStorage roomStorage, MainMenuController mainMenuController){
        this.eventManager = new EventManager(userStorage, roomStorage);
        this.userStorage = userStorage;
        this.messagingSystem = new MessagingSystem();
        this.scheduleSystem = new ScheduleSystem(eventManager);
        this.mainMenuController = mainMenuController;
    }

    /**
     * Instantiates user, speaker, and organizer controllers depending on what instance the user is of.
     * @param user The user.
     * @param scanner The scanner to be used for all controllers.
     */
    public void instantiateControllers(String userEmail, Scanner scanner){
        this.addObserver(mainMenuController);
        if (userStorage.emailToUser(userEmail).getType().equals("Attendee")){
            this.userScheduleController = new UserScheduleController(userEmail, userStorage, eventManager,
                    mainMenuController, scanner);
            setUserScheduleController();
            }
        else if (userStorage.emailToUser(userEmail).getType().equals("Organizer")){
            this.orgScheduleController = new OrgScheduleController(userEmail, eventManager,
                    mainMenuController, scanner);
            this.addObserver(orgScheduleController);
            setOrgScheduleController();
        }
        else{
            this.speakerScheduleController = new SpeakerScheduleController(userEmail, userStorage, eventManager,
                    mainMenuController, scanner);
            setSpeakerScheduleController();
        }

    }

    /**
     * Adds observers, calls the run methods of messagingSystem and scheduleSystem.
     */
    public void run() {
        //Moved Observers NOV 15
        if (messagingSystem.speakerMessengerController != null) {
            this.addObserver(messagingSystem.speakerMessengerController); //would be created
        }
        if (messagingSystem.speakerMessengerController != null) {
            this.addObserver(messagingSystem.speakerMessengerController.userInfo);
        }
        CSVReader fileReader = new CSVReader("src/Resources/Talks.csv");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for(ArrayList<String> talkData: fileReader.getData()){
            this.eventManager.createTalk(talkData.get(0), talkData.get(1), talkData.get(2),
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

    /**
     * Method to write the changes to the Talks.csv, called in MainMenuController.logout().
     */
    public void save() {
        CSVWriter csvWriter = new CSVWriter();
        csvWriter.writeToTalks("phase1/src/Resources/Talks.csv", this.getTalkManager()); //Not implemented yet
    }

    /**
     * creates the SignUpAttendees.
     */
    public void createSignUpAttendees(){
        for(UserScheduleManager schedule: userScheduleMap.values()){
            if (schedule.getTalkList() != null){
            for(Event t: schedule.getTalkList()){
                if(signUpMap.keySet().contains(t)){
                    signUpMap.get(t).addUser(schedule.user);
                }
                else{
                    SignUpAttendeesManager signup = new SignUpAttendeesManager(t, eventManager.getTalkRoom(t).capacity);
                    signup.addUser(schedule.user);
                    signUpMap.put(t, signup);
                }}
            }}
            setSignUpMap();
        }

    /**
     * Sets talk manager.
     */
    public void setTalkManager() {
        setChanged();
        notifyObservers(eventManager);
    }

    /**
     * Gets talk manager.
     * @return A TalkManager that represents the talkManager.
     */
    public EventManager getTalkManager() {
        return eventManager;
    }

    /**
     * Sets signUpMap.
     */
    public void setSignUpMap(){
        setChanged();
        notifyObservers(signUpMap);
    }

    /**
     * Sets the organizer schedule controller.
     */
    public void setOrgScheduleController(){
        setChanged();
        notifyObservers(orgScheduleController);
    }

    /**
     * Sets the user schedule controller.
     */
    public void setUserScheduleController(){
        setChanged();
        notifyObservers(userScheduleController);
    }

    /**
     * Sets the speaker schedule controller.
     */
    public void setSpeakerScheduleController(){
        setChanged();
        notifyObservers(speakerScheduleController);
    }
    /**
     * Updating TalkSystem's speakerScheduleMap or userScheduleMap and its mainMenuController.
     * @param o An Observable.
     * @param arg An Object.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof HashMap){
            if(((HashMap<?, ?>) arg).keySet().toArray()[0] instanceof Speaker){
                this.speakerScheduleMap = (HashMap<Speaker, SpeakerScheduleManager>) arg;
            }
            else{this.userScheduleMap = (HashMap<User, UserScheduleManager>) arg;}
        }
        if (arg instanceof MainMenuController){
            this.mainMenuController = (MainMenuController) arg;
        }

    }
}
