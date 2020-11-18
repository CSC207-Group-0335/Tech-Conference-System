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
    UserSchedulePresenter presenter;

    /**
     * Initializes a new controller for the user
     * @param user the user of the program
     */
    public UserScheduleController(UserScheduleManager user, TalkManager talkManager,
                                  MainMenuController mainMenuController){
        this.attendee = user;
        this.talkManager = talkManager;
        this.mainMenuController = mainMenuController;
        presenter = new UserSchedulePresenter();
    }

    public String signUp(Talk talk) {
        if (signUpMap.get(talk).addUser(attendee.getUser())) {
            attendee.addTalk(talk);
            return "User added.";
        }
        else{
                if(signUpMap.get(talk).userList.contains(attendee.getUser())){
                    return "User already registered for the requested talk.";
                }
                else{
                    return "Event is at full capacity.";
                }}
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

    public ArrayList<Talk> getRegisteredTalks(){
        ArrayList<Talk> registeredTalks = new ArrayList<Talk>();
        if(attendee.getTalkList().size() == 0){
            presenter.printMenu(13);
            presenter.printMenu(11);
        }
        else{
            Integer i = 1;
            for (Talk t: attendee.getTalkList()){
                presenter.printTalk(i, t , talkManager);
                registeredTalks.add(t);
                i++;
            }}
        return registeredTalks;
    }

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
                if (this.signUp(talkToRegister) == "User added.") {
                    // prints "Success"
                    userSchedulePresenter.printMenu(6);
                    userSchedulePresenter.printMenu(10);
                    userSchedulePresenter.printMenu(1);
                    return;
                }
                else{
                    if (this.signUp(talkToRegister) == "User already registered for the requested talk."){
                        userSchedulePresenter.printRegistrationBlocked(1);
                    }
                    else{
                        userSchedulePresenter.printRegistrationBlocked(2);
                    }
                }
            }
    }}
    protected void seeAllTalks(UserSchedulePresenter userSchedulePresenter, Scanner scan){
        //use the string representation in TalkManager
        userSchedulePresenter.printAllTalks(talkManager);
        userSchedulePresenter.printMenu(11);
        boolean doContinue  = true;
        while (doContinue){
            int talkIndex = scan.nextInt();
            if (talkIndex == 0){
                userSchedulePresenter.printMenu(10);
                userSchedulePresenter.printMenu(1);
                return;
            }
            else{userSchedulePresenter.printMenu(8);}
        }
    }

    protected void seeAllRegistered(UserSchedulePresenter userSchedulePresenter, Scanner scan){
        getRegisteredTalks();
        userSchedulePresenter.printMenu(11);
        boolean doContinue  = true;
        while (doContinue){
            int talkIndex = scan.nextInt();
            if (talkIndex == 0){
                userSchedulePresenter.printMenu(10);
                userSchedulePresenter.printMenu(1);
                return;
            }
        else{userSchedulePresenter.printMenu(8);
            }}
    }

    protected void cancelATalk(UserSchedulePresenter userSchedulePresenter, Scanner scan){
        ArrayList<Talk> registeredTalks = getRegisteredTalks();
        if (registeredTalks.size() != 0) {
            userSchedulePresenter.printMenu(5);
        boolean doContinue  = true;
        while (doContinue){
        int cancelTalkIndex = scan.nextInt();
        if (cancelTalkIndex == 0){
            userSchedulePresenter.printMenu(10);
            userSchedulePresenter.printMenu(1);
            return;
        }
        else if (registeredTalks.size() <= cancelTalkIndex - 1){
            userSchedulePresenter.printMenu(7);
        }
        else{
            Talk talkToCancel = registeredTalks.get(cancelTalkIndex - 1);
            this.cancelRegistration(talkToCancel);
            // prints "Success"
            userSchedulePresenter.printMenu(6);
            userSchedulePresenter.printMenu(10);
            userSchedulePresenter.printMenu(1);
            return;
        }}}
        else{
            boolean doContinue  = true;
            while (doContinue){
                int cancelTalkIndex = scan.nextInt();
                if (cancelTalkIndex == 0){
                    userSchedulePresenter.printMenu(10);
                    userSchedulePresenter.printMenu(1);
                    return;
                }
                else{userSchedulePresenter.printMenu(8);}
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

    public void setSignUpMap(HashMap<Talk, SignUpAttendeesManager> signUpMap){
        this.signUpMap = signUpMap;
    }
}
