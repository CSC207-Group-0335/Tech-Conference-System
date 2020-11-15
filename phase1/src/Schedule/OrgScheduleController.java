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

    public boolean requestTalks(String title, String speakerEmail, String roomName, LocalDateTime time) {
        //return this.talkManager.createTalk(title, talkId, speakerEmail, roomName, time);
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
