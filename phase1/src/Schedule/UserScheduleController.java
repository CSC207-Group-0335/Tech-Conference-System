package Schedule;

import Files.CSVWriter;
import UserLogin.MainMenuController;

import java.util.*;

/**
 * A controller class describing the actions a user can perform in the program.
 */
public class UserScheduleController{
    /**
     * A user of the conference.
     */
    UserScheduleManager attendee ;
    /**
     * The TalkManager for the conference.
     */
    TalkManager talkManager;
    /**
     * The menu for the conference.
     */
    MainMenuController mainMenuController;
    /**
     * A mapping of talks to its corresponding SignUpAttendeesManager.
     */
    public HashMap<Talk, SignUpAttendeesManager> signUpMap;
    /**
     * The presenter of the user controller.
     */
    UserSchedulePresenter presenter;
    /**
     * The scanner for the conference.
     */
    Scanner scan;

    /**
     * Initializes a new controller for the user.
     * @param user The user of the program.
     * @param talkManager The talkManager of the conference.
     * @param mainMenuController The menu of the conference.
     * @param scanner The scanner of MainMenuController.
     */
    public UserScheduleController(UserScheduleManager user, TalkManager talkManager,
                                  MainMenuController mainMenuController, Scanner scanner){
        this.attendee = user;
        this.talkManager = talkManager;
        this.mainMenuController = mainMenuController;
        presenter = new UserSchedulePresenter();
        this.scan = scanner;
    }

    /**
     * Let a user sign up for an event.
     * @param talk The talk they want to register for.
     * @param userScheduleManager The userScheduleManager.
     * @param signUpMap The signUpMap.
     * @return A string notifying the user if they have successfully enrolled in
     * the talk or if talk was at full capacity.
     */
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

    /**
     * Let a user cancel their enrollment in an event.
     * @param talk They talk they no longer want to attend.
     * @param signUpMap The signUpMap.
     */
    public void cancelRegistration(Talk talk, HashMap<Talk, SignUpAttendeesManager> signUpMap) {
        if (signUpMap.get(talk).removeUser(attendee.getUser())) {
            attendee.removeTalk(talk);
        }
    }

    /**
     * Get the talk that corresponds to the specified int. Since talk is stored in an ordered map this is possible.
     * @param talkIndex The position of the talk.
     * @return A talk representing the talk at the specified index.
     */
    public Talk getTalkByIndex(int talkIndex){
        Set<Talk> keys = talkManager.talkMap.keySet();
        ArrayList<Talk> talkList = new ArrayList<Talk>();
        for(Talk t: keys){talkList.add(t);};
        if (talkIndex - 1 >= talkList.size()){return null;}
        else{Talk t = talkList.get(talkIndex - 1);
        return t;
        }
    }

    /**
     * Let the user see the schedule of events for which they signed up.
     * @param userScheduleManager The userScheduleManager of the user.
     * @return An ArrayList representing the talks the user has signed up for.
     */
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

    /**
     * Takes in a user input and registers them for an event/talk.
     * @param presenter The presenter.
     * @param scan The scanner.
     * @param userScheduleManager The UserScheduleManager.
     * @param signUpMap The signUpMap.
     */
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
                if (this.signUp(talkToRegister,userScheduleManager, signUpMap ).equals("User added.")) {
                    // prints "Success"
                    presenter.printMenu(6);
                    presenter.printMenu(10);
                    return;
                }
                else{
                    if (this.signUp(talkToRegister, userScheduleManager, signUpMap).equals("User already registered for the requested talk.")){
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

    /**
     * Takes in a user's input and shows them all the talks offered.
     * @param presenter The presenter.
     * @param scan The scanner.
     */
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

    /**
     * Takes in a user's input and shows them all the talks they are currently registered for.
     * @param presenter The presenter.
     * @param scan The scanner.
     * @param userScheduleManager The UserScheduleManager.
     */
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

    /**
     * Takes in a user's input and cancels their registration for a specified talk.
     * @param presenter The presenter.
     * @param scan The scanner.
     * @param userScheduleManager The userScheduleManager.
     * @param signUpMap The signUpMap.
     */
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

    /**
     * Lists all the available actions a user can perform and choose from, takes their input and outputs a text UI.
     */
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

    /**
     * Sets the signUpMap for UserScheduleController.
     * @param signUpMap The signUpMap.
     */
    public void setSignUpMap(HashMap<Talk, SignUpAttendeesManager> signUpMap){
        this.signUpMap = signUpMap;
    }}

