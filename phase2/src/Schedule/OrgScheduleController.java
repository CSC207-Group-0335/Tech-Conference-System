package Schedule;

import UserLogin.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * A controller class describing the actions an organizer can perform in the program
 */
public class OrgScheduleController extends UserScheduleController {
    OrgSchedulePresenter orgSchedulePresenter;
    OrgSchedulePresenter1 orgSchedulePresenter1;

    /**
     * Creates a new controller for the organizer.
     * @param eventManager The eventManager.
     * @param mainMenuController The mainMenuController.
     * @param scanner The scanner.
     */
    public OrgScheduleController(String email, EventManager eventManager, UserManager userManager,
                                 MainMenuController mainMenuController, RoomStorage roomStorage, Scanner scanner){
        super(email, eventManager, userManager, mainMenuController, roomStorage, scanner);
        orgSchedulePresenter = new OrgSchedulePresenter();
        orgSchedulePresenter1 = new OrgSchedulePresenter1();
    }

    /**
     * Allows the organizer to choose a list of speakers from all the speakers.
     * @param scan The scanner.
     * @return Returns an ArrayList of speaker names representing the speakers chosen by the organizer.
     */

    public ArrayList<String> pickSpeakers(Scanner scan){
        ArrayList<String> speakerList = userManager.getSpeakerNameList();
        orgSchedulePresenter1.printByIndex(speakerList);
        // put an option in the presenter class?
        System.out.println("Enter the number of speakers");
        ArrayList<String>chosenSpeakers = new ArrayList<>();
        boolean doContinue = true;
        while(doContinue){
            String next = scan.nextLine();
            int numberOfSpeakers = Integer.parseInt(next);
            if (numberOfSpeakers == 0){
                return chosenSpeakers;
            }
            while (numberOfSpeakers > speakerList.size()){
                //option to create a speaker here?
                System.out.println("Not enough speakers, try again");
                next = scan.nextLine();
                numberOfSpeakers = Integer.parseInt(next);
            }
            System.out.println("Number of speakers chosen: " + numberOfSpeakers);
            while(chosenSpeakers.size() < numberOfSpeakers){
                // put an option in the presenter class?
                orgSchedulePresenter.printMenu(8);
                int speakerIndex = validatorController.userIntInputValidation("scheduling", "command",
                        scan);
                if (speakerIndex == 0){
                    //orgSchedulePresenter.printMenu(16);
                    return null;
                }
                else if (speakerIndex - 1 >= userManager.getSpeakerEmailList().size()){
                    orgSchedulePresenter.printMenu(14);
                }
                else{
                    String chosenSpeaker = userManager.getSpeakerEmailList().get(speakerIndex - 1);
                    if (!(chosenSpeakers.contains(chosenSpeaker))){
                        orgSchedulePresenter.printSchedule(
                                userManager.emailToTalkList(chosenSpeaker), eventManager, 2);
                        chosenSpeakers.add(chosenSpeaker);
                        System.out.println("Speaker added");
                    }
                }
            }
            return chosenSpeakers;
        }
        return null;
    }
    /**
     * Allows the organizer to choose a room from a list of rooms.
     * @param scan The scanner.
     * @return Returns the name of the room chosen by the organizer.
     */
    //NATHAN NOV 28 (made this reference the RoomNameList instead of RoomList throughout)

    public String pickRoom(Scanner scan) {
        // first they pick a speaker, then they pick a room, then they pick a time and check if it works
        orgSchedulePresenter.printAllRooms(roomStorage.getRoomNameList());
        orgSchedulePresenter.printMenu(7);
        boolean doContinue  = true;
        while (doContinue){
            int roomIndex = validatorController.userIntInputValidation("scheduling", "command",
                    scan);
            if (roomIndex == 0){
                orgSchedulePresenter.printMenu(16);
                return null;
            }
            else if (roomIndex -1 >= roomStorage.getRoomNameList().size()){
                orgSchedulePresenter.printMenu(12); //invalid input
            }
            else{
                String chosenRoom = roomStorage.getRoomNameList().get(roomIndex - 1);
                orgSchedulePresenter.printSchedule(
                        roomStorage.roomNameToEventIds(chosenRoom), eventManager, 1);
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
//            orgSchedulePresenter.PrintRequestTalkProcess(1);
//            int days = validatorController.userIntInputValidation("scheduling", "command",
//                    scan);
//            if (days == 0){
//                orgSchedulePresenter.printMenu(16);
//                return null;
//            }
//            else if (days > 3 || days < 0){
//                orgSchedulePresenter.printMenu(18);
//            }
//            else {
//                return days;
//            }
            orgSchedulePresenter.PrintRequestTalkProcess(1);
            String day = validatorController.userStringInputValidation("scheduling", "command", scan);
            if (day.equals("Zero")){
                orgSchedulePresenter.printMenu(16);
                return null;
            }
            else if (!(day.equals("November 21")||day.equals("November 22") || day.equals("November 23") )){
                orgSchedulePresenter.printMenu(18);
            }
            else{
                int days = Integer.parseInt(day.substring(day.length()-2, day.length()))-1;
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
            orgSchedulePresenter.PrintRequestTalkProcess(2);
            int hours = validatorController.userIntInputValidation("scheduling", "command",
                    scan);
            if (hours == 0) {
                orgSchedulePresenter.printMenu(16);
                return null;
            } else if (hours > 16 || hours < 9) {
                orgSchedulePresenter.printMenu(19);
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
        orgSchedulePresenter1.printRequestEventMenu(1);
        int capacity = validatorController.userIntInputValidation("scheduling", "command", scan);
        while (capacity > roomStorage.roomNameToCapacity(room)){
            orgSchedulePresenter1.printRequestEventMenu(2);
            capacity = validatorController.userIntInputValidation("scheduling", "command", scan);
        return capacity;
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
        orgSchedulePresenter1.printRequestEvent(1, Integer.toString(speakers.size()));
        String room = pickRoom(scan);
        if (room == null){return false;}
        LocalDateTime startTime = pickTime(scan);
        if (startTime==null){ return false;}
        LocalDateTime endTime = pickTime(scan);
        if (endTime == null){ return false;}
        while (!(endTime.isAfter(startTime))){
            System.out.println("End time must be after the start time. Pick another end time");
            endTime = pickTime(scan);
            if (endTime == null) {return false;}
        }
        if (speakers.size() == 0){
            int capacity = pickCapacity(room);
            orgSchedulePresenter1.printRequestEventMenu(3);
            String eventTitle = scan.nextLine();
            orgSchedulePresenter1.printRequestEventMenu(4);
            String vip1 = scan.nextLine();
            boolean vip1bool = false;
            if (vip1.equals("VIP")){
                vip1bool = true;
            }
            if (eventManager.createEvent(eventTitle, speakers, room, startTime, endTime, capacity, vip1bool)){
                orgSchedulePresenter1.printRequestEvent(2, eventTitle);
                return true;
            }
            else{return  false;}
        }
        boolean doubleBookingChecker = checkDoubleBookingSpeakers(speakers, room, startTime, endTime);
        while(!doubleBookingChecker){
            orgSchedulePresenter1.printRequestEventMenu(5);
            room = pickRoom(scan);
            if (room == null){return false;}
            startTime = pickTime(scan);
            if (startTime==null){ return false;}
            endTime = pickTime(scan);
            if (endTime == null){ return false;}
            doubleBookingChecker = checkDoubleBookingSpeakers(speakers, room, startTime, endTime);
        }
        int capacity = pickCapacity(room);
        orgSchedulePresenter1.printRequestEventMenu(6);
        orgSchedulePresenter1.printRequestEventMenu(3);
        String eventTitle = scan.nextLine();
        orgSchedulePresenter1.printRequestEventMenu(4);
        String vip2 = scan.nextLine();
        boolean vip2bool = false;
        if (vip2.equals("VIP")){
            vip2bool = true;
        }

        if (eventManager.createEvent(eventTitle, speakers, room, startTime, endTime, capacity, vip2bool)){
            orgSchedulePresenter1.printRequestEvent(2, eventTitle);
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
        orgSchedulePresenter1.printRegisterRoom(1);
        boolean doContinue = true;
        while (doContinue) {
            String roomName = validatorController.userStringInputValidation("scheduling", "command",
                    scan);
            orgSchedulePresenter1.printRegisterRoom(2);
            int capacity = validatorController.userIntInputValidation("scheduling", "command",
                    scan);
            if (roomName.equals("Zero")) {
                return;
            } else if (this.addRoom(roomName, capacity)) {
                orgSchedulePresenter1.printSuccess();
            } else {
                orgSchedulePresenter1.printRegisterRoom(3);
            } } }

    /**
     * Uses the requestUser to create a user.
     * @param scan The Scanner.
     */

    public void registerUser(Scanner scan){
        orgSchedulePresenter1.registerUserMenu(1);
        String name = scan.nextLine();
        String password = scan.nextLine();
        String email = scan.nextLine();
        while(!email.contains("@") || !userManager.checkIfValidEmail(email)){
            orgSchedulePresenter1.printTryAgain("email address");
            email = scan.nextLine();
        }
        String type = scan.nextLine();
        while (!(type.equals("Attendee") || type.equals("Speaker") || type.equals("Organizer"))){
            orgSchedulePresenter1.printTryAgain("user type");
             type = scan.nextLine();
        }
        if (type.equals("Attendee")){
            orgSchedulePresenter1.registerUserMenu(2);
            String vip = scan.nextLine();
            if (vip.equals("VIP") || vip.equals("vip")){
                this.requestUser(name, password, email, type, true);
                orgSchedulePresenter1.printSuccess();
            }
        }
        if (this.requestUser(name, password, email, type, false)){
            orgSchedulePresenter1.printSuccess();
        }

    }
    /**
     * Lists out all the events in the schedule and lets an organizer cancel an event from the schedule based on their input.
     * @param scan The Scanner
     */
    public void cancelEvent(Scanner scan){
        orgSchedulePresenter1.printEvents(eventManager.EventMapStringRepresentation());
        orgSchedulePresenter1.cancelEvent(1, "event");
        boolean doContinue = true;
        while(doContinue){
            int eventIndex = validatorController.userIntInputValidation("scheduling", "command",
                    scan);
            if (eventIndex == 0){
                orgSchedulePresenter1.printGoBack();
                return;
            }
            else if (getEventByIndex(eventIndex) == null){
                orgSchedulePresenter1.printTryAgain("event");
            }
            else{
                String eventIdToCancel= getEventByIndex(eventIndex);
                if (this.eventManager.cancelEvent(eventIdToCancel)) {
                    orgSchedulePresenter1.cancelEvent(2, eventManager.eventIdToTitle(eventIdToCancel));
                    return;
                } } } }

    public void run(){
        orgSchedulePresenter1.printHello(this.userManager.emailToName(email));
        orgSchedulePresenter1.printMenu();
        orgSchedulePresenter1.InputCommandRequest();
        boolean doContinue = true;
        while(doContinue) {
            int command = validatorController.userIntInputValidation("scheduling", "command",
                    scan);
            switch (command){
                case 1:
                    this.registerEvent(presenter,scan);
                    orgSchedulePresenter1.printMenu();
                    break;
                //If they want to see all available events
                case 2:
                    this.seeAll(presenter,scan, "events");
                    orgSchedulePresenter1.printMenu();
                    break;
                //if they want to see all the events they are currently registered for
                case 3 :
                    this.seeAll(presenter,scan, "registered");
                    orgSchedulePresenter1.printMenu();
                    break;
                // if they want to cancel a registration
                case 4:
                    this.cancelAnEvent(presenter,scan);
                    orgSchedulePresenter1.printMenu();
                    break;
                case 5:
                    this.requestEvent(scan);
                    orgSchedulePresenter1.printMenu();
                    break;
                case 6:
                    this.registerRoom(scan);
                    orgSchedulePresenter1.printMenu();
                    break;
                case 7:
                    this.registerUser(scan);
                    orgSchedulePresenter1.printMenu();
                    break;
                case 8:
                    this.cancelEvent(scan);
                    orgSchedulePresenter1.printMenu();
                    break;
                case 0:
                    doContinue = false;
                    mainMenuController.runMainMenu(email);
                    break;
        }}}

}

