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
    UserSchedulePresenter1 presenter;
    Scanner scan;
    ValidatorController validatorController;

    /**
     * Initializes a new controller for the user.
     * @param email The email of the user of the program.
     * @param userManager The userStorage associated with the conference.
     * @param eventManager The eventManager of the conference.
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
        presenter = new UserSchedulePresenter1();
        validatorController = new ValidatorController();
    }

    /**
     * Let a user sign up for an event.
     * @return A string notifying the user if they have successfully enrolled in
     * the event or if event was at full capacity.
     */
    public String signUp(String eventID) {
        if(this.eventManager.eventIdAtCapacity(eventID)){
            return "Event is at full capacity.";
        }
        else if(!(eventManager.checkDoubleBooking(eventID, userManager.emailToTalkList(email)))){
            return "Double booking";
        }
        else if (!(eventManager.checkIfUserAllowed(email, eventID))){
            return "VIP only event";
        }
        else{
            if (this.eventManager.addAttendee(email,eventID)){
                this.userManager.addEvent(email, eventID);
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
     * Get the event that corresponds to the specified int. Since event is stored in an ordered map this is possible.
     * @return An eventId representing the event at the specified index.
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
     * @return An ArrayList representing the events the user has signed up for.
     */
    public ArrayList<String> getRegisteredEvents(){
        ArrayList<String> registeredEvents = userManager.emailToTalkList(email);
        if (registeredEvents.size() == 0){
            presenter.ChoosingEvent(3);
        }
        else{
            ArrayList<String> stringRepRegisteredEvents = new ArrayList<>();
            for(String event: registeredEvents){
                stringRepRegisteredEvents.add(eventManager.toStringEvent(event));
            }
            presenter.printByIndex(stringRepRegisteredEvents);
        }
        return registeredEvents;
    }

    protected void createRequest(UserSchedulePresenter presenter, Scanner scan){
        //present the requests if you're an organizer and you can decide which request you want to deal with
        //based on index
        ArrayList<String> requestList = new ArrayList();
        HashMap<String, String> requestMap = userManager.getRequestList(email);
        requestList.addAll(requestMap.keySet());
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
     * Takes in a user input and registers them for an event.
     * @param presenter The presenter.
     * @param scan The scanner.
     */
    protected void registerEvent(UserSchedulePresenter1 presenter, Scanner scan){
        // show them a list of all available events
        presenter.printEvents(eventManager.EventMapStringRepresentation());
        //they will pick the number corresponding to each event
        presenter.ChoosingEvent(1);
        presenter.choose("event");
        //assuming they will have asked to see all events they could register before selecting command 1
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
                    if (signUpStatus.equals("User already registered for the requested event.")){
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
     * Takes in a user's input and shows them all the eventss offered.
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
            case "speaker":
                this.getAllSpeakers();
            case "day":
                this.getAllDays();


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
     * Shows all the Speakers speaking at the conference.
     *
     *
     */
    protected void getAllSpeakers(){
        ArrayList<String> speakerNames = this.userManager.getSpeakerNameList();
        if (speakerNames.size() == 0){
            presenter.scheduleBy(1);
            presenter.printGoBack();
        }
        presenter.printByIndex(speakerNames);
        presenter.choose("speaker");
        boolean doContinue  = true;
        while (doContinue){
            int speakerIndex = validatorController.userIntInputValidation("scheduling", "command",
                    scan);
                if (speakerIndex == 0){
                    return;
                }
                else if (speakerIndex -1 >= userManager.getSpeakerEmailList().size()){
                    presenter.printTryAgain("speaker index");
                }
                else{
                    String chosenSpeaker = userManager.getSpeakerEmailList().get(speakerIndex - 1);
                    ArrayList<String> eventList = new ArrayList<>();
                    for (String id: userManager.emailToTalkList(chosenSpeaker)){
                        eventList.add(eventManager.toStringEvent(id));
                    }
                    presenter.printByIndex(eventList);
                    return;
                }} }

    protected void getAllDays(){
        ArrayList<String> days = eventManager.getAllEventDays();
        if (days.size() == 0){
            presenter.scheduleBy(2);
        }
        presenter.choose("day index");
        presenter.printByIndex(days);
        boolean doContinue  = true;
        while (doContinue){
            int dayIndex = validatorController.userIntInputValidation("scheduling", "command", scan);
                if (dayIndex == 0){
                    return;
                }
                else if (dayIndex -1 >= days.size()){
                    presenter.printTryAgain("day index");
                }
                else{
                    int chosenInt = eventManager.getAllEventDayMonth().get(dayIndex-1);
                    ArrayList<String> eventList = new ArrayList<>();
                    for (String id: eventManager.intDayToEventIDs(chosenInt)){
                        eventList.add(eventManager.toStringEvent(id));
                    }
                    presenter.printByIndex(eventList);
                    return;
                }
    }}

    /**
     * Takes in a user's input and cancels their registration for a specified talk.
     * @param presenter The presenter.
     * @param scan The scanner.
     */
    protected void cancelAnEvent(UserSchedulePresenter1 presenter,
                               Scanner scan){
        ArrayList<String> registeredEvents = this.getRegisteredEvents();
        if (registeredEvents.size() != 0) {
            presenter.ChoosingEvent(2);
            presenter.choose("Event");
        boolean doContinue  = true;
        while (doContinue){
            int cancelEventIndex = validatorController.userIntInputValidation("scheduling", "command",
                    scan);
            if (cancelEventIndex == 0){
            presenter.printGoodbye("scheduling");
            return;
        }
        else if (registeredEvents.size() <= Math.abs(cancelEventIndex - 1)){
            presenter.printTryAgain("event index");
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
        presenter.printHello(userManager.emailToName(email));
        presenter.printMenu();
        presenter.InputCommandRequest();
        boolean doContinue = true;
        while(doContinue) {
            Integer command = validatorController.userIntInputValidation("main", "command", scan);
            switch (command){
            //if they want to register for an event
                case 1:
                    this.registerEvent(presenter, scan);
                    presenter.printMenu();
                    break;
                //If they want to see all available events
                case 2:
                    this.seeAll(presenter, scan, "events");
                    presenter.printMenu();
                    break;
                //if they want to see all the events they are currently registered for
                case 3:
                    this.seeAll(presenter, scan, "registered");
                    presenter.printMenu();
                    break;
                // if they want to cancel a registration
                case 4:
                    this.cancelAnEvent(presenter, scan);
                    presenter.printMenu();
                    break;
                case 5:
                    this.seeAll(presenter, scan, "speaker");
                    presenter.printMenu();
                    break;
                case 6:
                    this.seeAll(presenter, scan, "day");
                    presenter.printMenu();
                    break;
                case 0:
                    doContinue = false;
                    mainMenuController.runMainMenu(this.email);
                    break;
            }}}
    }

