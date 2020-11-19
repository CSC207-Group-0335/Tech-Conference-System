package Schedule;

import Files.CSVWriter;
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
    Scanner scan;

    /**
     * Initializes a new controller for the user
     * @param user the user of the program
     */
    public UserScheduleController(UserScheduleManager user, TalkManager talkManager,
                                  MainMenuController mainMenuController, Scanner scanner){
        this.attendee = user;
        this.talkManager = talkManager;
        this.mainMenuController = mainMenuController;
        presenter = new UserSchedulePresenter();
        this.scan = scanner;
    }

    public String signUp(Talk talk, UserScheduleManager userScheduleManager,
                         HashMap<Talk, SignUpAttendeesManager> signUpMap) {
        if (signUpMap.get(talk).addUser(userScheduleManager.getUser())) {
            userScheduleManager.addTalk(talk);
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

    public void cancelRegistration(Talk talk, HashMap<Talk, SignUpAttendeesManager> signUpMap) {
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

    public ArrayList<Talk> getRegisteredTalks(UserScheduleManager userScheduleManager){
        ArrayList<Talk> registeredTalks = new ArrayList<Talk>();
        if(userScheduleManager.getTalkList().size() == 0){
            presenter.printMenu(13);
            presenter.printMenu(11);
        }
        else{
            Integer i = 1;
            for (Talk t: userScheduleManager.getTalkList()){
                presenter.printTalk(i, t , talkManager);
                registeredTalks.add(t);
                i++;
            }}
        return registeredTalks;
    }

    protected void registerTalk(UserSchedulePresenter presenter, Scanner scan,
                                UserScheduleManager userScheduleManager,
                                HashMap<Talk, SignUpAttendeesManager> signUpMap){
        // show them a list of all available talks
        presenter.printAllTalks(talkManager);
        //they will pick the number corresponding to each talk
        presenter.printMenu(3);
        //assuming they will have asked to see all talks they could register before selecting command 1
        boolean doContinue  = true;
        while (doContinue){
            String choice = scan.nextLine();
            try {
                int talkIndex = Integer.parseInt(choice);
            if (talkIndex == 0){
                presenter.printMenu(10);
                return;
            }
            else if (getTalkByIndex(talkIndex) == null){
                presenter.printMenu(7);
            }
            else{
                Talk talkToRegister = getTalkByIndex(talkIndex);
                if (this.signUp(talkToRegister,userScheduleManager, signUpMap ) == "User added.") {
                    // prints "Success"
                    presenter.printMenu(6);
                    presenter.printMenu(10);
                    return;
                }
                else{
                    if (this.signUp(talkToRegister, userScheduleManager, signUpMap) == "User already registered for the requested talk."){
                        presenter.printRegistrationBlocked(1);
                    }
                    else{
                        presenter.printRegistrationBlocked(2);
                    }
                }
            }}
            catch (NumberFormatException nfe){
        presenter.printMenu(8);;
    }}}

    protected void seeAllTalks(UserSchedulePresenter presenter, Scanner scan){
        //use the string representation in TalkManager
        presenter.printAllTalks(talkManager);
        presenter.printMenu(11);
        boolean doContinue  = true;
        while (doContinue){
            String choice = scan.nextLine();
            try {
                int talkIndex = Integer.parseInt(choice);
            if (talkIndex == 0){
                presenter.printMenu(10);
                return;
            }
            else{presenter.printMenu(8);}}
            catch (NumberFormatException nfe){
       presenter.printMenu(8);;
        }
    }}

    protected void seeAllRegistered(UserSchedulePresenter presenter,
                                    Scanner scan, UserScheduleManager userScheduleManager){
        getRegisteredTalks(userScheduleManager);
        presenter.printMenu(11);
        boolean doContinue  = true;
        while (doContinue){
            String choice = scan.nextLine();
            try { int talkIndex = Integer.parseInt(choice);
            if (talkIndex == 0){
                presenter.printMenu(10);
                return;
            }
        else{presenter.printMenu(8);
            }}catch (NumberFormatException nfe){
        presenter.printMenu(8);;
    }}}

    protected void cancelATalk(UserSchedulePresenter presenter,
                               Scanner scan, UserScheduleManager userScheduleManager,
                               HashMap<Talk, SignUpAttendeesManager> signUpMap){
        ArrayList<Talk> registeredTalks = getRegisteredTalks(userScheduleManager);
        if (registeredTalks.size() != 0) {
            presenter.printMenu(5);
        boolean doContinue  = true;
        while (doContinue){
            String choice = scan.nextLine();
            try {
                int cancelTalkIndex = Integer.parseInt(choice);
            if (cancelTalkIndex == 0){
            presenter.printMenu(10);
            return;
        }
        else if (registeredTalks.size() <= Math.abs(cancelTalkIndex - 1)){
            presenter.printMenu(7);
        }
        else{
            Talk talkToCancel = registeredTalks.get(cancelTalkIndex - 1);
            this.cancelRegistration(talkToCancel, signUpMap);
            // prints "Success"
            presenter.printMenu(6);
            presenter.printMenu(10);
            return;
        }}catch (NumberFormatException nfe){
        presenter.printMenu(8);;
    }}}
    else{
            boolean doContinue  = true;
            while (doContinue){
                String choice = scan.nextLine();
                try {
                    int cancelTalkIndex = Integer.parseInt(choice);
                    if (cancelTalkIndex == 0) {
                        presenter.printMenu(10);
                        return;
                    }}catch (NumberFormatException nfe){
                        presenter.printMenu(8);;
            }
        }}}


    public void run(){
        presenter.printHello(attendee);
        presenter.printMenu(1);
        presenter.printMenu(2);
        boolean doContinue = true;
        while(doContinue) {
            String choice = scan.nextLine();
            try {
                int command = Integer.parseInt(choice);
            //if they want to register for a talk
            if (command == 1) {
                this.registerTalk(presenter, scan, attendee, signUpMap);
                presenter.printMenu(1);
                //If they want to see all available talks
            }else if (command == 2) {
                this.seeAllTalks(presenter, scan);
                presenter.printMenu(1);
                //if they want to see all the talks they are currently registered for
            }else if (command == 3) {
                this.seeAllRegistered(presenter, scan, attendee);
                presenter.printMenu(1);
                // if they want to cancel a registration
            }else if (command == 4) {
                this.cancelATalk(presenter, scan, attendee, signUpMap);
                presenter.printMenu(1);
            }
            else if (command ==0){
                doContinue = false;
                mainMenuController.runMainMenu(attendee.getUser());
            }
            else{presenter.printMenu(8);}
        } catch (NumberFormatException nfe){
        presenter.printMenu(8);;
    }
    }}

    public void setSignUpMap(HashMap<Talk, SignUpAttendeesManager> signUpMap){
        this.signUpMap = signUpMap;
    }}



//String choice = scan.nextLine();
//            try {
//                int command = Integer.parseInt(choice);}
// catch (NumberFormatException nfe){
//        presenter.printMenu(8);;

