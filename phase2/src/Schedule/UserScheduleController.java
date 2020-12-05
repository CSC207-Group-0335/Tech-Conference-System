package Schedule;

import UserLogin.MainMenuController;
import UserLogin.UserManager;

import java.util.*;

/**
 * A controller class describing the actions a user can perform in the program.
 */
public class UserScheduleController{
    String email;
    UserManager userManager;
    EventManager eventManager;
    MainMenuController mainMenuController;
    RoomStorage roomStorage;
    UserSchedulePresenter presenter;
    UserSchedulePresenter1 presenter1;
    Scanner scan;
    ValidatorController validatorController;

    /**
     * Initializes a new controller for the user.
     * @param email The email of the user of the program.
     * @param userManager The userStorage associated with the conference.
     * @param eventManager The talkManager of the conference.
     * @param mainMenuController The menu of the conference.
     * @param scanner The scanner of MainMenuController.
     */
    public UserScheduleController(String email, EventManager eventManager, UserManager userManager,
                                  MainMenuController mainMenuController, RoomStorage roomStorage, Scanner scanner){
        this.scan = scanner;
        this.email = email;
        this.userManager = userManager;
        this.eventManager = eventManager;
        this.roomStorage = roomStorage;
        this.mainMenuController = mainMenuController;
        presenter = new UserSchedulePresenter();
        presenter1 = new UserSchedulePresenter1();
        validatorController = new ValidatorController();
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
        else if(!(eventManager.checkDoubleBooking(eventid, userManager.emailToTalkList(email)))){
            return "Double booking";
        }
        else if (!(eventManager.checkIfUserAllowed(email, eventid))){
            return "VIP only event";
        }
        else{
            if (this.eventManager.addAttendee(email,eventid)){
                this.userManager.addEvent(email, eventid);
                return "User added.";
            }
            return "User already signed up";
        }
    }

    public boolean submitRequest(String request) {
        if (this.userManager.requestNotRepeat(email, request)){
            this.userManager.addRequest(email, request);
            System.out.println("Request successfully added!");
            return true;
        }
        System.out.println("You have already made this request");
        return false;
    }

    /**
     * Let a user cancel their enrollment in an event.
     */
    //doesnt do anything rn
    public void cancelRegistration(String eventId){
        if (eventManager.eventIdToUsersSignedUp(eventId).contains(email)){
            eventManager.removeAttendee(email, eventId);
            userManager.removeEvent(email, eventId);
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
        ArrayList<String> registeredEvents = userManager.emailToTalkList(email);
        if (registeredEvents.size() == 0){
            presenter1.ChoosingEvent(3);
        }
        else{
            ArrayList<String> stringRepRegisteredEvents = new ArrayList<>();
            for(String event: registeredEvents){
                stringRepRegisteredEvents.add(eventManager.toStringEvent(event));
            }
            presenter1.printByIndex(stringRepRegisteredEvents);
        }
        return registeredEvents;
    }

    protected void createRequest(UserSchedulePresenter presenter, Scanner scan){
        //present the requests if you're an organizer and you can decide which request you want to deal with
        //based on index
        ArrayList<String> requestList = userManager.getRequestList(email);
        presenter.printAllRequests(requestList);
        System.out.println("Submit a request");
        boolean doContinue = true;
        while (doContinue){
            String request = scan.nextLine();
            try {
                if (request.equals("zero")){
                    presenter.printMenu(10);
                    return;
                }
                else{
                    if (this.submitRequest(request)){
                        return;
                    }
                }

            } catch (NumberFormatException nfe) {
                presenter.printMenu(8);;
            }
        }
    }

    /**
     * Takes in a user input and registers them for an event/talk.
     * @param presenter The presenter.
     * @param scan The scanner.
     */
    protected void registerTalk(UserSchedulePresenter1 presenter, Scanner scan){
        // show them a list of all available talks
        presenter.printEvents(eventManager.EventMapStringRepresentation());
        //they will pick the number corresponding to each talk
        presenter.ChoosingEvent(1);
        presenter.Choose("event");
        //assuming they will have asked to see all talks they could register before selecting command 1
        boolean doContinue  = true;
        while (doContinue){
            int eventIndex = validatorController.userIntInputValidation("scheduling", "command", scan);
            if (eventIndex == 0){
                return;
            }
            else if (getEventByIndex(eventIndex) == null){
                presenter.printTryAgain("event index");
            }
            else{
                String eventIdToRegister = getEventByIndex(eventIndex);
                if (this.signUp(eventIdToRegister).equals("User added.")) {
                    presenter.printSuccess();
                    presenter.printGoodbye("scheduling");
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
                    }}}}}

    /**
     * Takes in a user's input and shows them all the talks offered.
     * @param presenter The presenter.
     * @param scan The scanner.
     */
    protected void seeAll(UserSchedulePresenter1 presenter, Scanner scan, String events){
        switch (events) {
            case "events":
                presenter.printEvents(eventManager.EventMapStringRepresentation());
                break;
            case "registered":
                this.getRegisteredEvents();
                break;
        }
        presenter.printGoBack();
        boolean doContinue  = true;
        while (doContinue){
            int choice = validatorController.userIntInputValidation("scheduling", "command", scan);
            if (choice == 0){
                return; }
            else{presenter.printTryAgain("command");}}
    }

    /***
     * Takes in a users input and shows them all the Speakers speaking at the conference.
     * @param presenter The presenter.
     * @param scan The Scanner.
     */
    protected void seeAllSpeakers(UserSchedulePresenter presenter, Scanner scan){
        ArrayList<String> speakersnames = this.userManager.getSpeakerNameList();
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
                else if (speakerIndex -1 >= userManager.getSpeakerEmailList().size()){
                    presenter.printMenu(16);
                }
                else{
                    String chosenSpeaker = userManager.getSpeakerEmailList().get(speakerIndex - 1);
                    presenter.printSchedule(
                            userManager.emailToTalkList(chosenSpeaker), eventManager, 2);
                    return;
                }}catch (NumberFormatException nfe){
                presenter.printMenu(8);}}
    }

    protected void seeAllDays(UserSchedulePresenter presenter, Scanner scan){
        ArrayList<String> days = eventManager.getAllEventDays();
        if (days.size() == 0){
            presenter.printMenu(16);

        }
        presenter.printMenu(18);
        presenter.printAllSpeakers(days);
        boolean doContinue  = true;
        while (doContinue){
            String choice = scan.nextLine();
            try { int dayindex = Integer.parseInt(choice);
                if (dayindex == 0){
                    presenter.printMenu(10);
                    return;
                }
                else if (dayindex -1 >= days.size()){
                    presenter.printMenu(16);
                }
                else{
                    int chosenInt = eventManager.getAllEventDayMonth().get(dayindex-1);
                    presenter.printSchedule(eventManager.intDayToEventIDs(chosenInt), eventManager, 3);
                    return;
                }}catch (NumberFormatException nfe){
                presenter.printMenu(8);}}
    }

    /**
     * Takes in a user's input and cancels their registration for a specified talk.
     * @param presenter The presenter.
     * @param scan The scanner.
     */
    protected void cancelATalk(UserSchedulePresenter1 presenter,
                               Scanner scan){
        ArrayList<String> registeredEvents = this.getRegisteredEvents();
        if (registeredEvents.size() != 0) {
            presenter.ChoosingEvent(2);
            presenter.Choose("Event");
        boolean doContinue  = true;
        while (doContinue){
            int cancelEventIndex = validatorController.userIntInputValidation("scheduling", "command",
                    scan);
            if (cancelEventIndex == 0){
            presenter.printGoodbye("scheduling");
            return;
        }
        else if (registeredEvents.size() <= Math.abs(cancelEventIndex - 1)){
            presenter1.printTryAgain("event index");
        }
        else{
            String eventIdToCancel = registeredEvents.get(cancelEventIndex - 1);
            this.cancelRegistration(eventIdToCancel);
            // prints "Success"
            presenter.printSuccess();
            presenter.printGoodbye("scheduling");
            return;
        }}
    }
    else{
            boolean doContinue  = true;
            while (doContinue){
                int command = validatorController.userIntInputValidation("scheduling", "command", scan);
                    if (command == 0) {
                        presenter.printGoodbye("scheduling");
                        return;
                    } } }}

    /**
     * Lists all the available actions a user can perform and choose from, takes their input and outputs a text UI.
     */
    public void run(){
        presenter1.printHello(userManager.emailToName(email));
        presenter1.printMenu();
        presenter1.InputCommandRequest();
        boolean doContinue = true;
        while(doContinue) {
            Integer command = validatorController.userIntInputValidation("main", "command", scan);
            switch (command){
            //if they want to register for a talk
                case 1:
                    this.registerTalk(presenter1, scan);
                    presenter1.printMenu();
                    break;
                //If they want to see all available talks
                case 2:
                    this.seeAll(presenter1, scan, "events");
                    presenter1.printMenu();
                    break;
                //if they want to see all the talks they are currently registered for
                case 3:
                    this.seeAll(presenter1, scan, "registered");
                    presenter1.printMenu();
                    break;
                // if they want to cancel a registration
                case 4:
                    this.cancelATalk(presenter1, scan);
                    presenter1.printMenu();
                    break;
                case 5:
                    this.seeAllSpeakers(presenter, scan);
                    presenter1.printMenu();
                    break;
                case 6:
                    this.seeAllDays(presenter, scan);
                    presenter1.printMenu();
                    break;
                case 0:
                    doContinue = false;
                    mainMenuController.runMainMenu(this.email);
                    break;
            }}}
    }

