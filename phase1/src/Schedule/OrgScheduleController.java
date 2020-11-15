package Schedule;

import UserLogin.*;

import java.time.LocalDateTime;
import java.util.*;

public class OrgScheduleController extends UserScheduleController implements Observer {
    UserScheduleManager organizer;
    TalkManager talkManager;
    RoomStorage roomStorage;
    UserStorage userStorage;
    MainMenuController mainMenuController;
    HashMap<Talk, SignUpAttendeesManager> signUpAttendeesManager;


    public OrgScheduleController(UserScheduleManager organizer, TalkManager talkManager,
                                 MainMenuController mainMenuController, HashMap<Talk, SignUpAttendeesManager>
                                         signUpAttendeesManager){
        super(organizer, talkManager, mainMenuController, signUpAttendeesManager);
        this.talkManager = talkManager;
        this.mainMenuController = mainMenuController;
    }

    public ArrayList<Speaker> getSpeakerList(){
        ArrayList<Speaker> speakerList = new ArrayList<Speaker>();
        for(User u: userStorage.getUserList()){
            if (u instanceof Speaker){
                speakerList.add((Speaker) u);
            }
        }
        return speakerList;
    }

    public Speaker pickSpeaker(TalkManager talkmanager, Scanner scan) {
        // first they pick a speaker, then they pick a room, then they pick a time and check if it works
        orgSchedulePresenter.printIndexList(getSpeakerList());
        orgSchedulePresenter.printMenu("Pick speaker by Index, press 0 to go back");
        boolean doContinue  = true;
        while (doContinue){
            int speakerIndex = scan.nextInt();
            if (speakerIndex == 0){
                orgSchedulePresenter.printMenu(10);
                orgSchedulePresenter.printMenu(1);
                return null;
            }
            else if (speakerIndex >= getSpeakerList().size()){
                orgSchedulePresenter.printMenu(7);
            }
            else{
                Speaker chosenSpeaker = getSpeakerList().get(speakerIndex - 1);
                return chosenSpeaker;
            }}
        return null;}

    public Room pickRoom(TalkManager talkmanager, Scanner scan) {
        // first they pick a speaker, then they pick a room, then they pick a time and check if it works
        orgSchedulePresenter.printIndexList(roomStorage.getRoomList());
        orgSchedulePresenter.printMenu("Pick Room by Index, press 0 to go back");
        boolean doContinue  = true;
        while (doContinue){
            int RoomIndex = scan.nextInt();
            if (speakerIndex == 0){
                orgSchedulePresenter.printMenu(10);
                orgSchedulePresenter.printMenu(1);
                return null;
            }
            else if (speakerIndex >= getSpeakerList().size()){
                orgSchedulePresenter.printMenu(7);
            }
            else{
                Room chosenRoom = roomStorage.getRoomList().get(RoomIndex - 1);
                return chosenRoom;
            }}
        return null;}

    public boolean pickTime(TalkManager talkmanager, Scanner scan) {
        // first they pick a speaker, then they pick a room, then they pick a time and check if it works
        orgSchedulePresenter.printAllSpeakers(getSpeakerList());
        orgSchedulePresenter.printMenu("Pick speaker");
        boolean doContinue  = true;
        while (doContinue){
            int speakerIndex = scan.nextInt();
            if (speakerIndex == 0){
                orgSchedulePresenter.printMenu(10);
                orgSchedulePresenter.printMenu(1);
                return null;
            }
            else if (speakerIndex >= getSpeakerList().size()){
                orgSchedulePresenter.printMenu(7);
            }
            else{
                Speaker chosenSpeaker = getSpeakerList().get(speakerIndex - 1);
                return chosenSpeaker;
            }}
        return null;}

        public boolean requestTalks(TalkManager talkmanager, Scanner scan) {
            // first they pick a speaker, then they pick a room, then they pick a time and check if it works
            orgSchedulePresenter.printAllSpeakers(userStorage.userList);
            orgSchedulePresenter.printMenu("Pick speaker");
            boolean doContinue  = true;
            while (doContinue){
                int talkIndex = scan.nextInt();
                if (talkIndex == 0){
                    userSchedulePresenter.printMenu(10);
                    userSchedulePresenter.printMenu(1);
                    return;
                }
                else if (getTalkByIndex(talkIndex) == null){
                    userSchedulePresenter.printMenu(7);
                }
                else{
                    Talk talkToRegister = getTalkByIndex(talkIndex);
                    this.signUp(talkToRegister);
                    // prints "Success"
                    userSchedulePresenter.printMenu(6);
                    userSchedulePresenter.printMenu(10);
                    userSchedulePresenter.printMenu(1);
                    return;
                }
                //return this.talkManager.createTalk(title, speakerEmail, roomName, time);
                return true;
            }}

        public boolean pickSpeaker(TalkManager talkmanager, Scanner scan) {
            // first they pick a speaker, then they pick a room, then they pick a time and check if it works
            orgSchedulePresenter.printAllSpeakers(userStorage.userList);
            orgSchedulePresenter.printMenu("Pick speaker");
            boolean doContinue  = true;
            while (doContinue){
                int talkIndex = scan.nextInt();
                if (talkIndex == 0){
                    userSchedulePresenter.printMenu(10);
                    userSchedulePresenter.printMenu(1);
                    return;
                }
                else if (getTalkByIndex(talkIndex) == null){
                    userSchedulePresenter.printMenu(7);
                }
                else{
                    Talk talkToRegister = getTalkByIndex(talkIndex);
                    this.signUp(talkToRegister);
                    // prints "Success"
                    userSchedulePresenter.printMenu(6);
                    userSchedulePresenter.printMenu(10);
                    userSchedulePresenter.printMenu(1);
                    return;
                }
                //return this.talkManager.createTalk(title, speakerEmail, roomName, time);
                return true;
            }

    //there's also a createRoom in RoomStorage with the parameter capacity
    public void addRoom(String roomName) {
        this.roomStorage.createRoom(roomName);
    }

    //can't put anything here since speakerStorage hasn't been made
    public boolean requestSpeaker(String name, String password, String email) {
        return this.userStorage.createUser("Speaker", name, password, email);
    }
//    public void run(){
//        Presenter presenter = new Presenter();
//        Scanner scan = new Scanner(System.in);
//        boolean doContinue = true;
//        while (doContinue){
//            int command = scan.nextInt();
//            if (command == 1){
//
//            }
//            else if (command == )
//        }
//
//    }
    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof RoomStorage){
            this.roomStorage = roomStorage;
        }

    }
}
