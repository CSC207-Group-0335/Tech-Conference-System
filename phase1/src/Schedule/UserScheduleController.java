package Schedule;

import UserLogin.MainMenuController;

import java.util.*;

/**
 * A controller class describing the actions a user can perform in the program
 */
public class UserScheduleController{
    /**
     * The user of the program
     */
    UserScheduleManager attendee ;
    TalkManager talkManager;
    MainMenuController mainMenuController;
    public HashMap<Talk, SignUpAttendeesManager> signUpMap;

    /**
     * Initializes a new controller for the user
     * @param user the user of the program
     */
    public UserScheduleController(UserScheduleManager user, TalkManager talkManager,
                                  MainMenuController mainMenuController,
                                  HashMap<Talk, SignUpAttendeesManager> signUpMap){
        this.attendee = user;
        this.talkManager = talkManager;
        this.mainMenuController = mainMenuController;
        this.signUpMap = signUpMap;
    }

    public void signUp(Talk talk) {
        if (signUpMap.get(talk).addUser(attendee.getUser())) {
            attendee.addTalk(talk);
        }
    }

    public void cancelRegistration(Talk talk) {
        if (signUpMap.get(talk).removeUser(attendee.getUser())) {
            attendee.removeTalk(talk);
        }
    }

    public Talk getTalkByIndex(int talkIndex){
        Set<Talk> keys = talkManager.talkMap.keySet();
        ArrayList<Talk> talkList = new ArrayList<Talk>();
        for(Talk t: keys){talkList.add(t);};
        if (talkIndex - 1 >= talkList.size()){return null;}
        else{Talk t = talkList.get(talkIndex - 1);
        return t;
        }
    }
    
    //public ArrayList<ArrayList<Object>> allRegistered() {
        //list of all the talks they're enrolled for
    //    ArrayList<Talk> talkList = attendee.getTalkList();
        // new list with the same length as talkList
    //    ArrayList<ArrayList<Object>> registeredFor = new ArrayList<>();
        //for loop to populate the new list
    //    for (int i = 0; i < talkList.size(); i++) {
            //this will be the "tuple" that's stored
            // the 0 index will contain the string representation of talk
            // the 1 index will have the speaker name
            // the 2 index will have the room name
            // we will append this to the new list
    //        ArrayList<Object> talkSpeakerRoom = new ArrayList<>();
    //Talk speech = talkList.get(i);
    //        talkSpeakerRoom.set(0, speech.toString());
    //        talkSpeakerRoom.set(1, talkManager.getTalkSpeaker(speech).getName());
    //        talkSpeakerRoom.set(2, talkManager.getTalkRoom(speech).getRoomName());
    //        registeredFor.set(i, talkSpeakerRoom);
    //    }
    //    return registeredFor;}

    protected void registerTalk(UserSchedulePresenter userSchedulePresenter, Scanner scan){
        // show them a list of all available talks
        userSchedulePresenter.printAllTalks(talkManager);
        //they will pick the number corresponding to each talk
        userSchedulePresenter.printMenu(3);
        //assuming they will have asked to see all talks they could register before selecting command 1
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
    }}
    protected void seeAllTalks(UserSchedulePresenter userSchedulePresenter, Scanner scan){
        //use the string representation in TalkManager
        userSchedulePresenter.printAllTalks(talkManager);
    }
    protected void seeAllRegistered(UserSchedulePresenter userSchedulePresenter, Scanner scan){
        //each line shows talk, time, speaker, room
        //Daniel: changed it bc I added a talktostring mathod in talkmanager that makes this more simple
        Integer i = 1;
        for (Talk t: attendee.talkList){
            userSchedulePresenter.printTalk(i, t , talkManager);
            i++;
        }
    }
    protected void cancelATalk(UserSchedulePresenter userSchedulePresenter, Scanner scan){
        userSchedulePresenter.printAllTalks(talkManager);
        userSchedulePresenter.printMenu(5);
        boolean doContinue  = true;
        while (doContinue){
        int cancelTalkIndex = scan.nextInt();
        if (cancelTalkIndex == 0){
            userSchedulePresenter.printMenu(10);
            userSchedulePresenter.printMenu(1);
            return;
        }
        else if (getTalkByIndex(cancelTalkIndex) == null){
            userSchedulePresenter.printMenu(7);
        }
        else{
            Talk talkToCancel = getTalkByIndex(cancelTalkIndex);
            this.cancelRegistration(talkToCancel);
            // prints "Success"
            userSchedulePresenter.printMenu(6);
            userSchedulePresenter.printMenu(10);
            userSchedulePresenter.printMenu(1);
            return;
        }
    }}
//changed the method name to go because there already other methods named run in the MessagingPresenterPackage
//Daniel: its ok its good that run means the same thing in the whole program so I changed back
    public void run(){
        UserSchedulePresenter userSchedulePresenter = new UserSchedulePresenter();
        userSchedulePresenter.printHello(attendee);
        userSchedulePresenter.printMenu(1);
        userSchedulePresenter.printMenu(2);
        Scanner scan = new Scanner(System.in);
        boolean doContinue = true;
        while(doContinue) {
            int command = scan.nextInt();
            //if they want to register for a talk
            if (command == 1) {
                this.registerTalk(userSchedulePresenter, scan);
                //If they want to see all available talks
            }else if (command == 2) {
                this.seeAllTalks(userSchedulePresenter, scan);
                //if they want to see all the talks they are currently registered for
            }else if (command == 3) {
                this.seeAllRegistered(userSchedulePresenter, scan);
                // if they want to cancel a registration
            }else if (command == 4) {
                this.cancelATalk(userSchedulePresenter, scan);
            }
            else if (command ==0){
                scan.close();
                doContinue = false;
                mainMenuController.runMainMenu(attendee.getUser());
            }
            else{userSchedulePresenter.printMenu(8);}
        }
    }
}
