package Schedule;

import UserLogin.MainMenuController;
import UserLogin.UserStorage;

import java.util.*;

/**
 * A controller class describing the actions a user can perform in the program.
 */
public class UserScheduleController{
    String email;
    UserStorage userStorage;
    EventManager eventManager;
    MainMenuController mainMenuController;
    RoomStorage roomStorage;
    UserSchedulePresenter presenter;
    Scanner scan;

    /**
     * Initializes a new controller for the user.
     * @param email The email of the user of the program.
     * @param userStorage The userStorage associated with the conference.
     * @param eventManager The talkManager of the conference.
     * @param mainMenuController The menu of the conference.
     * @param scanner The scanner of MainMenuController.
     */
    public UserScheduleController(String email, EventManager eventManager, UserStorage userStorage,
                                  MainMenuController mainMenuController, RoomStorage roomStorage, Scanner scanner){
        this.email = email;
        this.userStorage = userStorage;
        this.eventManager = eventManager;
        this.roomStorage = roomStorage;
        this.mainMenuController = mainMenuController;
        presenter = new UserSchedulePresenter();
        this.scan = scanner;
    }

    /**
     * Let a user sign up for an event.
     * @return A string notifying the user if they have successfully enrolled in
     * the talk or if talk was at full capacity.
     */
    public String signUp(String eventid) {
        if(!(this.eventManager.eventIdAtCapacity(eventid))){
            return "Event is at full capacity.";
        }
        else if(!(eventManager.checkDoubleBooking(eventid, userStorage.emailToTalkList(email)))){
            return "Double booking";
        }
        else if (!(eventManager.checkIfUserAllowed(email, eventid))){
            return "VIP only event";
        }
        else{
            if (this.eventManager.addAttendee(email,eventid)){
                this.userStorage.addEvent(email, eventid);
                return "User added.";
            }
            return "User already signed up";
        }
    }

    /**
     * Let a user cancel their enrollment in an event.
     */
    //doesnt do anything rn
    public void cancelRegistration(String eventId){
        if (eventManager.eventIdToUsersSignedUp(eventId).contains(email)){
            eventManager.removeAttendee(email, eventId);
            userStorage.removeEvent(email, eventId);
        }
    }

    /**
     * Get the talk that corresponds to the specified int. Since talk is stored in an ordered map this is possible.
     * @return A talk representing the talk at the specified index.
     */
    public String getEventByIndex(int eventIndex){
        ArrayList<String> eventIds = eventManager.getEventIdsList();
        if (eventIndex -1 >= eventIds.size()){
            return null;
        }
        else{
            String eventId = eventIds.get(eventIndex-1);
            return eventId;
        }
    }

    /**
     * Let the user see the schedule of events for which they signed up.
     * @return An ArrayList representing the talks the user has signed up for.
     */
    public ArrayList<String> getRegisteredEvents(){
        ArrayList<String> registeredEvents = userStorage.emailToTalkList(email);
        if (registeredEvents.size() == 0){
            presenter.printMenu(13);
            presenter.printMenu(11);
        }
        else{
            Integer i = 1;
            for (String e : registeredEvents){
                presenter.printTalk(i, e, eventManager);
                i++;
            }
        }
        return registeredEvents;
    }

    /**
     * Takes in a user input and registers them for an event/talk.
     * @param presenter The presenter.
     * @param scan The scanner.
     */
    protected void registerTalk(UserSchedulePresenter presenter, Scanner scan){
        // show them a list of all available talks
        presenter.printAllTalks(eventManager);
        //they will pick the number corresponding to each talk
        presenter.printMenu(3);
        //assuming they will have asked to see all talks they could register before selecting command 1
        boolean doContinue  = true;
        while (doContinue){
            String choice = scan.nextLine();
            try {
                int eventIndex = Integer.parseInt(choice);
            if (eventIndex == 0){
                presenter.printMenu(10);
                return;
            }
            else if (getEventByIndex(eventIndex) == null){
                presenter.printMenu(7);
            }
            else{
                String eventIdToRegister = getEventByIndex(eventIndex);
                if (this.signUp(eventIdToRegister).equals("User added.")) {
                    // prints "Success"
                    presenter.printMenu(6);
                    presenter.printMenu(10);
                    return;
                }
                else{
                    String signUpStatus = this.signUp(eventIdToRegister);
                    if (signUpStatus.equals("User already registered for the requested talk.")){
                        presenter.printRegistrationBlocked(1);
                    }
                    else if(signUpStatus.equals("Double booking.")){
                        presenter.printRegistrationBlocked(3);
                    }
                    else if(signUpStatus.equals("VIP only event")){
                        presenter.printRegistrationBlocked(4);
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
        presenter.printAllTalks(eventManager);
        presenter.printMenu(11);
        boolean doContinue  = true;
        while (doContinue){
            String choice = scan.nextLine();
            try {
                int eventIndex = Integer.parseInt(choice);
            if (eventIndex == 0){
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
     */
    protected void seeAllRegistered(UserSchedulePresenter presenter,
                                    Scanner scan){
        ArrayList<String> registeredEvents = this.getRegisteredEvents();
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

    /***
     * Takes in a users input and shows them all the Speakers speaking at the conference.
     * @param presenter The presenter.
     * @param scan The Scanner.
     */
    protected void seeAllSpeakers(UserSchedulePresenter presenter, Scanner scan){
        ArrayList<String> speakersnames = this.userStorage.getSpeakerNameList();
        if (speakersnames.size() == 0){
            presenter.printMenu(15);
            presenter.printMenu(11);
        }
        presenter.printAllSpeakers(speakersnames);
        presenter.printMenu(14);
        boolean doContinue  = true;
        while (doContinue){
            String choice = scan.nextLine();
            try { int speakerIndex = Integer.parseInt(choice);
                if (speakerIndex == 0){
                    presenter.printMenu(10);
                    return;
                }
                else if (speakerIndex -1 >= userStorage.getSpeakerEmailList().size()){
                    presenter.printMenu(16);
                }
                else{
                    String chosenSpeaker = userStorage.getSpeakerEmailList().get(speakerIndex - 1);
                    presenter.printSchedule(
                            userStorage.emailToTalkList(chosenSpeaker), eventManager, 2);
                    return;
                }}catch (NumberFormatException nfe){
                presenter.printMenu(8);}}
    }






    /**
     * Takes in a user's input and cancels their registration for a specified talk.
     * @param presenter The presenter.
     * @param scan The scanner.
     */
    protected void cancelATalk(UserSchedulePresenter presenter,
                               Scanner scan){
        ArrayList<String> registeredEvents = getRegisteredEvents();
        if (registeredEvents.size() != 0) {
            presenter.printMenu(5);
        boolean doContinue  = true;
        while (doContinue){
            String choice = scan.nextLine();
            try {
                int cancelEventIndex = Integer.parseInt(choice);
            if (cancelEventIndex == 0){
            presenter.printMenu(10);
            return;
        }
        else if (registeredEvents.size() <= Math.abs(cancelEventIndex - 1)){
            presenter.printMenu(7);
        }
        else{
            String eventIdToCancel = registeredEvents.get(cancelEventIndex - 1);
            this.cancelRegistration(eventIdToCancel);
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
                    int cancelEventIndex = Integer.parseInt(choice);
                    if (cancelEventIndex == 0) {
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
        presenter.printHello(userStorage.emailToName(email));
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
                presenter.printMenu(1);
                //If they want to see all available talks
            }else if (command == 2) {
                this.seeAllTalks(presenter, scan);
                presenter.printMenu(1);
                //if they want to see all the talks they are currently registered for
            }else if (command == 3) {
                this.seeAllRegistered(presenter, scan);
                presenter.printMenu(1);
                // if they want to cancel a registration
            }else if (command == 4) {
                this.cancelATalk(presenter, scan);
                presenter.printMenu(1);
            }
            else if (command ==5){
                this.seeAllSpeakers(presenter, scan);
                presenter.printMenu(1);
            }
            else if (command ==0){
                doContinue = false;
                mainMenuController.runMainMenu(this.email);
            }
            else{presenter.printMenu(8);}
        } catch (NumberFormatException nfe){
        presenter.printMenu(8);;
    }
    }}
}

