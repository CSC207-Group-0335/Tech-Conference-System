package Schedule;

import UserLogin.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * A controller class describing the actions an organizer can perform in the program
 */
public class OrgScheduleController extends UserScheduleController {
    OrgSchedulePresenter presenter;

    /**
     * Creates a new controller for the organizer.
     * @param eventManager The eventManager.
     * @param mainMenuController The mainMenuController.
     * @param scanner The scanner.
     */
    public OrgScheduleController(String email, EventManager eventManager, UserManager userManager,
                                 MainMenuController mainMenuController, RoomStorage roomStorage, Scanner scanner){
        super(email, eventManager, userManager, mainMenuController, roomStorage, scanner);
        presenter = new OrgSchedulePresenter();
    }

    /**
     * Allows the organizer to choose a list of speakers from all the speakers.
     * @param scan The scanner.
     * @return Returns an ArrayList of speaker names representing the speakers chosen by the organizer.
     */
    public ArrayList<String> pickSpeakers(Scanner scan) {
        ArrayList<String> speakerList = userManager.getSpeakerNameList();
        presenter.printByIndex(speakerList);
        presenter.printChooseSpeakers(1);
        ArrayList<String> chosenSpeakers = new ArrayList<>();
        boolean doContinue = true;
        while (doContinue) {
            String next = scan.nextLine();
            try {
                int numberOfSpeakers = Integer.parseInt(next);
                while (numberOfSpeakers > speakerList.size() || numberOfSpeakers < 0) {
                    //option to create a speaker here?
                    if (numberOfSpeakers < 0) {
                        presenter.printChooseSpeakers(4);
                        presenter.printChooseSpeakers(1);
                    } else if (numberOfSpeakers > speakerList.size()) {
                        presenter.printChoosingSpeakersProcess(3, Integer.toString(speakerList.size()));
                        presenter.printChooseSpeakers(1);
                    }
                    next = scan.nextLine();
                    try {
                        numberOfSpeakers = Integer.parseInt(next);
                    } catch (NumberFormatException nfe) {
                        presenter.printTryAgain("number");
                    }
                }
                if (numberOfSpeakers == 0) {
                    return chosenSpeakers;
                }
                presenter.printChoosingSpeakersProcess(1, Integer.toString(numberOfSpeakers));
                while (chosenSpeakers.size() < numberOfSpeakers) {
                    // put an option in the presenter class?
                    presenter.choose("speaker");
                    Integer speakerIndex = validatorController.userIntInputValidation("scheduling", "speaker index",
                            scan);
                    if (speakerIndex  == null){
                        continue;
                    }
                    else if (speakerIndex == 0) {
                        return null;
                    } else if (speakerIndex - 1 >= userManager.getSpeakerEmailList().size()) {
                        presenter.printTryAgain("speaker index");
                    } else {
                        String chosenSpeaker = userManager.getSpeakerEmailList().get(speakerIndex - 1);
                        if (!(chosenSpeakers.contains(chosenSpeaker))) {
                            this.getSchedule(email, 1, "User");
                            chosenSpeakers.add(chosenSpeaker);
                            presenter.printChooseSpeakers(2);
                        } else {
                            presenter.printChooseSpeakers(3);
                        }
                    }
                }
                return chosenSpeakers;
            } catch (NumberFormatException nfe) {
                presenter.printTryAgain("number");
            }

//         }

        }
        return null;
    }
    /**
     * Allows the organizer to choose a room from a list of rooms.
     * @param scan The scanner.
     * @return Returns the name of the room chosen by the organizer.
     */

    public String pickRoom(Scanner scan) {
        // first they pick a speaker, then they pick a room, then they pick a time and check if it works
        presenter.printByIndex(roomStorage.getRoomNameList());
        presenter.choose("room");
        boolean doContinue  = true;
        while (doContinue){
            Integer roomIndex = validatorController.userIntInputValidation("scheduling", "room index",
                    scan);
            if (roomIndex == null){
                continue;
            }
            else if (roomIndex == 0){
                return null;
            }
            else if (roomIndex -1 >= roomStorage.getRoomNameList().size()){
                presenter.printTryAgain("room index");
            }
            else{
                String chosenRoom = roomStorage.getRoomNameList().get(roomIndex - 1);
                this.getSchedule(chosenRoom, 2, "Room");
                return chosenRoom;
            }
        }
    return null;
    }

    /**
     * Allows the organizer to choose a day - in this case our conference is three-days long.
     * @param scan The scanner.
     * @return Returns a int representing the day chosen by the organizer.
     */
    public Integer pickDay(Scanner scan){
        boolean doContinue  = true;
        while(doContinue) {
            presenter.printRequestEventProcess(1);
            String day = validatorController.userStringInputValidation("scheduling", "day", scan);
            if (day == null){
                continue;
            }
            else if (day.equals("Zero")){
                return null;
            }
            else if ((day.equals("November 21")||day.equals("November 22") || day.equals("November 23") )){
                int days = Integer.parseInt(day.substring(day.length()-2, day.length()))-20;
                return days;
            }
        }
    return null;
    }

    /**
     * Allows the organizer to choose an hour between 9am and 5pm.
     * @param scan The scanner.
     * @return Returns a int representing the hour chosen by the organizer.
     */
    public Integer pickHour(Scanner scan) {
        boolean doContinue = true;
        while (doContinue) {
            presenter.printRequestEventProcess(2);
            Integer hours = validatorController.userIntInputValidation("scheduling", "hour",
                    scan);
            if (hours == null){
                continue;
            }
            if (hours == 0) {
                return null;
            } else if (hours > 16.5 || hours < 9) {
                presenter.printTryAgain("hour");
            } else {
                return hours;
            }

        }
        return null;
    }
        /**
         * Allows the organizer to choose a day and hour for the start time of the event.
         * @param scan The scanner.
         * @return A LocalDateTime representing the start time chosen by the organizer.
         */
        public LocalDateTime pickTime (Scanner scan){
            // first they pick a speaker, then they pick a room, then they pick a time and check if it works
            Integer day = pickDay(scan);
            if (day == null) {
                return null;
            }
            Integer hour = pickHour(scan);
            if (hour == null) {
                return null;
            }
            LocalDateTime dateTime = LocalDateTime.of(2020, 11, 20 + day, hour, 0);
            return dateTime;

    }
    /**
     * Check if the room and speaker are double-booked, or if just the speaker is double-booked, or if just the room
     * is double booked.
     * @param speaker The speaker.
     * @param room The room.
     * @param startTime The start time.
     * @return An int representing one of the three aforementioned options.
     */
    public int checkDoubleBooking(String speaker, String room, LocalDateTime startTime, LocalDateTime endTime){
        //LocalDateTime end = dateTime.plusHours(1);
         if(!eventManager.checkDoubleBooking(startTime, endTime, userManager.emailToTalkList(speaker))
                && !eventManager.checkDoubleBooking(startTime, endTime, roomStorage.roomNameToEventIds(room))){return 1;}
        else if(!eventManager.checkDoubleBooking(startTime, endTime, userManager.emailToTalkList(speaker))){
            return 2;
        }
        else if(!eventManager.checkDoubleBooking(startTime, endTime, roomStorage.roomNameToEventIds(room))){return 3;}
        else{return 0;}
    }
    /**
     * Check if any speaker in the list of speakers is double booked at the specified room and time
     * @param speakers The list of speakers.
     * @param room The room.
     * @param startTime The start time.
     * @param endTime The end time.
     * @return An boolean representing if all the speakers are available at the room and time or not.
     */
    public boolean checkDoubleBookingSpeakers(ArrayList<String> speakers, String room, LocalDateTime startTime, LocalDateTime endTime){
        for (String s : speakers){
            if (!(checkDoubleBooking(s, room, startTime, endTime)==0)){
                return false;
            }
        }
        return true;
    }

    public Integer pickCapacity(String room){
        presenter.printRequestEventMenu(1);
        boolean doContinue = true;
        while (doContinue){
            Integer capacity = validatorController.userIntInputValidation("scheduling", "capacity", scan);
            if (capacity == null){
                continue;
            }
            else if (capacity > roomStorage.roomNameToCapacity(room)){
                presenter.printRequestEventMenu(2);
            }
            else {
                return capacity;
            }
        }
    return null;}

    /**
     * Allows the organizer to create event.
     * @param scan The scanner.
     * @return A boolean notifying the organizer that they have successfully created an event.
     */
    public boolean requestEvent(Scanner scan){
        ArrayList<String> speakers = pickSpeakers(scan);
        if (speakers == null){ return false;}
        presenter.printChoosingSpeakersProcess(1, Integer.toString(speakers.size()));
        String room = pickRoom(scan);
        if (room == null){return false;}
        presenter.printRequestEventMenu(7);
        LocalDateTime startTime = pickTime(scan);
        if (startTime==null){ return false;}
        presenter.printRequestEventMenu(8);
        LocalDateTime endTime = pickTime(scan);
        if (endTime == null){ return false;}
        while (!(endTime.isAfter(startTime))){
            presenter.printRequestEventMenu(9);

            endTime = pickTime(scan);
            if (endTime == null) {return false;}
        }
        if (speakers.size() == 0){
            int capacity = pickCapacity(room);
            presenter.printRequestEventMenu(3);
            String eventTitle = scan.nextLine();
            presenter.printRequestEventMenu(4);
            String vip1 = validatorController.userStringInputValidation("scheduling", "VIP label", scan);
            boolean vip1bool = false;
            if (vip1.equals("VIP")){
                vip1bool = true;
            }
            if (eventManager.createEvent(eventTitle, speakers, room, startTime, endTime, capacity, vip1bool)){
                presenter.printChoosingSpeakersProcess(2, eventTitle);
                return true;
            }
            else{return  false;}
        }
        boolean doubleBookingChecker = checkDoubleBookingSpeakers(speakers, room, startTime, endTime);
        while(!doubleBookingChecker){
            presenter.printRequestEventMenu(5);
            room = pickRoom(scan);
            if (room == null){return false;}
            presenter.printRequestEventMenu(7);
            startTime = pickTime(scan);
            if (startTime==null){ return false;}
            presenter.printRequestEventMenu(8);
            endTime = pickTime(scan);
            if (endTime == null){ return false;}
            while (!(endTime.isAfter(startTime))){
                presenter.printRequestEventMenu(9);

                endTime = pickTime(scan);
                if (endTime == null) {return false;}
            }
            doubleBookingChecker = checkDoubleBookingSpeakers(speakers, room, startTime, endTime);
        }
        int capacity = pickCapacity(room);
        presenter.printRequestEventMenu(6);
        presenter.printRequestEventMenu(3);
        String eventTitle = scan.nextLine();
        presenter.printRequestEventMenu(4);
        String vip2 = validatorController.userStringInputValidation("scheduling", "VIP label", scan);
        boolean vip2bool = false;
        if (vip2.equals("VIP")){
            vip2bool = true;
        }

        if (eventManager.createEvent(eventTitle, speakers, room, startTime, endTime, capacity, vip2bool)){
            presenter.printChoosingSpeakersProcess(2, eventTitle);
            return true;
        }
        else{return  false;}


    }

    /**
     * Allows the organizer to create a room with the specified name and capacity.
     * @param roomName The name of the room.
     * @param cap The capacity of the room.
     * @return A boolean notifying the organizer if they have successfully created a room.
     */
    public boolean addRoom(String roomName, int cap) {
        return this.roomStorage.createRoom(roomName, cap);
    }

    /**
     * Allows the organizer to create a user with the specified name, password, and email.
     * @param name The name of the user.
     * @param password The password of the user.
     * @param email The email of the user.
     * @param type The type of the user
     * @return A boolean notifying the organizer if they have successfully created a user.
     */
    public boolean requestUser(String name, String password, String email, String type, boolean vip) {
        return this.userManager.createUser(type, name, password, email, vip);
    }

    /**
     * Uses the addRoom method to register a room.
     * @param scan The scanner.
     */
    public void registerRoom(Scanner scan) {
        presenter.printRegisterRoom(1);
        boolean doContinue = true;
        while (doContinue) {
            String roomName = validatorController.userStringInputValidation("scheduling", "capacity",
                    scan);
            if (roomName == null){
                continue;
            }
            presenter.printRegisterRoom(2);
            Integer capacity = validatorController.userIntInputValidation("scheduling", "capacity",
                    scan);
            if (capacity == null){
                continue;
            }
            if (roomName.equals("Zero")) {
                return;
            } else if (this.addRoom(roomName, capacity)) {
                presenter.printSuccess();
                presenter.printGoodbye("scheduling");
                return;
            } else {
                presenter.printRegisterRoom(3);
            } } }

    /**
     * Uses the requestUser to create a user.
     * @param scan The Scanner.
     */

    public void registerUser(Scanner scan){
        presenter.registerUserMenu(1);
        String name = scan.nextLine();
        String password = scan.nextLine();
        String email = scan.nextLine();
        while(!email.contains("@") || !userManager.checkIfValidEmail(email)){
            presenter.printTryAgain("email address");
            email = scan.nextLine();
        }
        String type = scan.nextLine();
        while (!(type.equals("Attendee") || type.equals("Speaker") || type.equals("Organizer"))){
            presenter.printTryAgain("user type");
             type = scan.nextLine();
        }
        if (type.equals("Attendee")){
            presenter.registerUserMenu(2);
            String vip = scan.nextLine();
            if (vip.equals("VIP") || vip.equals("vip")){
                this.requestUser(name, password, email, type, true);
                presenter.printSuccess();
                presenter.printGoodbye("scheduling");
                return;
            }
        }
        if (this.requestUser(name, password, email, type, false)){
            presenter.printSuccess();
            presenter.printGoodbye("scheduling");
            return;
        }

    }
    /**
     * Lists out all the events in the schedule and lets an organizer cancel an event from the schedule based on their input.
     * @param scan The Scanner
     */
    public void cancelEvent(Scanner scan){
        presenter.printEvents(eventManager.EventMapStringRepresentation());
        presenter.cancelEvent(1, "event");
        boolean doContinue = true;
        while(doContinue){
            Integer eventIndex = validatorController.userIntInputValidation("scheduling", "event index",
                    scan);
            if (eventIndex == null){
                continue;
            }
            else if (eventIndex == 0){
                presenter.printGoBack();
                return;
            }
            else if (getEventByIndex(eventIndex) == null){
                presenter.printTryAgain("event");
            }
            else{
                String eventIdToCancel= getEventByIndex(eventIndex);
                if (this.eventManager.cancelEvent(eventIdToCancel)) {
                    presenter.cancelEvent(2, eventManager.eventIdToTitle(eventIdToCancel));
                    return;
                } } } }

    private void reviewRequests(Scanner scan) {
        ArrayList<String> requestsList = userManager.totalPending();
        presenter.printByIndex(requestsList);
    }


    public void run(){
        presenter.printHello(this.userManager.emailToName(email));
        presenter.printMenu();
        presenter.InputCommandRequest();
        boolean doContinue = true;
        while(doContinue) {
            Integer command = validatorController.userIntInputValidation("scheduling", "command",
                    scan);
            if (command == null){
                continue;
            }
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
                case 7:
                    this.requestEvent(scan);
                    presenter.printMenu();
                    break;
                case 8:
                    this.registerRoom(scan);
                    presenter.printMenu();
                    break;
                case 9:
                    this.registerUser(scan);
                    presenter.printMenu();
                    break;
                case 10:
                    this.cancelEvent(scan);
                    presenter.printMenu();
                    break;
                case 11:
                    this.reviewRequests(scan);
                    presenter.printMenu();
                    break;
                case 0:
                    doContinue = false;
                    mainMenuController.runMainMenu(email);
                    break;
        }}}
}

