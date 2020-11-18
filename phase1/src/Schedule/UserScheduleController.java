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

    protected void registerTalk(UserSchedulePresenter presenter, Scanner scan){
        // show them a list of all available talks
        presenter.printAllTalks(talkManager);
        //they will pick the number corresponding to each talk
        presenter.printMenu(3);
        //assuming they will have asked to see all talks they could register before selecting command 1
        boolean doContinue  = true;
        while (doContinue){
            int talkIndex = scan.nextInt();
            if (talkIndex == 0){
                presenter.printMenu(10);
                presenter.printMenu(1);
                return;
            }
            else if (getTalkByIndex(talkIndex) == null){
                presenter.printMenu(7);
            }
            else{
                Talk talkToRegister = getTalkByIndex(talkIndex);
                if (this.signUp(talkToRegister) == "User added.") {
                    // prints "Success"
                    presenter.printMenu(6);
                    presenter.printMenu(10);
                    presenter.printMenu(1);
                    return;
                }
                else{
                    if (this.signUp(talkToRegister) == "User already registered for the requested talk."){
                        presenter.printRegistrationBlocked(1);
                    }
                    else{
                        presenter.printRegistrationBlocked(2);
                    }
                }
            }
    }}
    protected void seeAllTalks(UserSchedulePresenter presenter, Scanner scan){
        //use the string representation in TalkManager
        presenter.printAllTalks(talkManager);
        presenter.printMenu(11);
        boolean doContinue  = true;
        while (doContinue){
            int talkIndex = scan.nextInt();
            if (talkIndex == 0){
                presenter.printMenu(10);
                presenter.printMenu(1);
                return;
            }
            else{presenter.printMenu(8);}
        }
    }

    protected void seeAllRegistered(UserSchedulePresenter presenter, Scanner scan){
        getRegisteredTalks();
        presenter.printMenu(11);
        boolean doContinue  = true;
        while (doContinue){
            int talkIndex = scan.nextInt();
            if (talkIndex == 0){
                presenter.printMenu(10);
                presenter.printMenu(1);
                return;
            }
        else{presenter.printMenu(8);
            }}
    }

    protected void cancelATalk(UserSchedulePresenter presenter, Scanner scan){
        ArrayList<Talk> registeredTalks = getRegisteredTalks();
        if (registeredTalks.size() != 0) {
            presenter.printMenu(5);
        boolean doContinue  = true;
        while (doContinue){
        int cancelTalkIndex = scan.nextInt();
        if (cancelTalkIndex == 0){
            presenter.printMenu(10);
            presenter.printMenu(1);
            return;
        }
        else if (registeredTalks.size() <= cancelTalkIndex - 1){
            presenter.printMenu(7);
        }
        else{
            Talk talkToCancel = registeredTalks.get(cancelTalkIndex - 1);
            this.cancelRegistration(talkToCancel);
            // prints "Success"
            presenter.printMenu(6);
            presenter.printMenu(10);
            presenter.printMenu(1);
            return;
        }}}
        else{
            boolean doContinue  = true;
            while (doContinue){
                int cancelTalkIndex = scan.nextInt();
                if (cancelTalkIndex == 0){
                    presenter.printMenu(10);
                    presenter.printMenu(1);
                    return;
                }
                else{presenter.printMenu(8);}
        }
    }}

//changed the method name to go because there already other methods named run in the MessagingPresenterPackage
//Daniel: its ok its good that run means the same thing in the whole program so I changed back
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
                this.registerTalk(presenter, scan);
                //If they want to see all available talks
            }else if (command == 2) {
                this.seeAllTalks(presenter, scan);
                //if they want to see all the talks they are currently registered for
            }else if (command == 3) {
                this.seeAllRegistered(presenter, scan);
                // if they want to cancel a registration
            }else if (command == 4) {
                this.cancelATalk(presenter, scan);
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
    }
}
