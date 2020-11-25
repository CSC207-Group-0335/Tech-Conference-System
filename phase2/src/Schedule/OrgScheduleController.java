package Schedule;

import UserLogin.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * A controller class describing the actions an organizer can perform in the program
 */
public class OrgScheduleController extends UserScheduleController implements Observer {
    /**
     * An organizer for the conference.
     */
    UserScheduleManager organizer;
    /**
     * Stores all the talks for the conference
     */
    EventManager eventManager;
    /**
     * Stores all the rooms for the conference
     */
    RoomStorage roomStorage;
    /**
     * Stores all the uses for the conference
     */
    UserStorage userStorage;
    /**
     * The menu for the conference.
     */
    MainMenuController mainMenuController;
    /**
     * A mapping of talks to its corresponding SignUpAttendeesManager.
     */
    HashMap<Event, SignUpAttendeesManager> signUpMap;
    /**
     * The presenter of the organizer controller.
     */
    OrgSchedulePresenter orgSchedulePresenter;
    /**
     * The presenter of the user controller.
     */
    UserSchedulePresenter userSchedulePresenter;
    /**
     * The scanner for the conference.
     */
    Scanner scanner;

    /**
     * Creates a new controller for the organizer.
     * @param organizer The organizer.
     * @param eventManager The talkManager.
     * @param mainMenuController The mainMenuController.
     * @param scanner The scanner.
     */
    public OrgScheduleController(UserScheduleManager organizer, EventManager eventManager,
                                 MainMenuController mainMenuController, Scanner scanner){
        super(organizer, eventManager, mainMenuController, scanner);
        this.eventManager = eventManager;
        this.mainMenuController = mainMenuController;
        this.scanner = scanner;
        this.organizer = organizer;
        orgSchedulePresenter = new OrgSchedulePresenter();
        userSchedulePresenter = new UserSchedulePresenter();
    }

    /**
     * gets the list of all speakers giving a talk.
     * @return An ArrayList represent the list of all speakers.
     */
    public ArrayList<Speaker> getSpeakerList(){
        ArrayList<Speaker> speakerList = new ArrayList<Speaker>();
        for(User u: userStorage.getUserList()){
            if (u instanceof Speaker){
                speakerList.add((Speaker) u);
            }
        }
        return speakerList;
    }

    /**
     * Allows the organizer to choose a speaker from a list of speakers.
     * @param scan The scanner.
     * @return Returns a Speaker representing the speaker chosen by the organizer.
     */
    public Speaker pickSpeaker(Scanner scan) {
        // first they pick a speaker, then they pick a room, then they pick a time and check if it works
        orgSchedulePresenter.printAllSpeakers(getSpeakerList());
        orgSchedulePresenter.printMenu(8);
        boolean doContinue  = true;
        while (doContinue){
            String choice = scan.nextLine();
            try { int speakerIndex = Integer.parseInt(choice);
            if (speakerIndex == 0){
                orgSchedulePresenter.printMenu(16);
                return null;
            }
            else if (speakerIndex -1 >= getSpeakerList().size()){
                orgSchedulePresenter.printMenu(14);
            }
            else{
                Speaker chosenSpeaker = getSpeakerList().get(speakerIndex - 1);
                orgSchedulePresenter.printSchedule(
                        userStorage.getSpeakerScheduleMap().get(chosenSpeaker).getTalkList(), eventManager, 2);
                return chosenSpeaker;
            }}catch (NumberFormatException nfe){
                userSchedulePresenter.printMenu(8);}}
    return null;}
    /**
     * Allows the organizer to choose a room from a list of rooms.
     * @param scan The scanner.
     * @return Returns a Room representing the room chosen by the organizer.
     */
    public Room pickRoom(Scanner scan) {
        // first they pick a speaker, then they pick a room, then they pick a time and check if it works
        orgSchedulePresenter.printAllRooms(roomStorage.getRoomList());
        orgSchedulePresenter.printMenu(7);
        boolean doContinue  = true;
        while (doContinue){
            String choice = scan.nextLine();
            try { int roomIndex = Integer.parseInt(choice);
            if (roomIndex == 0){
                orgSchedulePresenter.printMenu(16);
                return null;
            }
            else if (roomIndex -1 >= roomStorage.getRoomList().size()){
                orgSchedulePresenter.printMenu(12);
            }
            else{
                Room chosenRoom = roomStorage.getRoomList().get(roomIndex - 1);
                orgSchedulePresenter.printSchedule(
                        roomStorage.getScheduleList().get(chosenRoom).getTalkList(), eventManager, 1);
                return chosenRoom;
            }} catch (NumberFormatException nfe){
            userSchedulePresenter.printMenu(8);}}
    return null;}
    /**
     * Allows the organizer to choose a day - in this case our conference is three-days long.
     * @param scan The scanner.
     * @return Returns a int representing the day chosen by the organizer.
     */
    public Integer pickDay(Scanner scan){
        boolean doContinue  = true;
        while(doContinue){
            try {orgSchedulePresenter.PrintRequestTalkProcess(1);
                String dayChoice = scan.nextLine();
                int days = Integer.parseInt(dayChoice);
                if (days == 0){
                    orgSchedulePresenter.printMenu(16);
                    return null;
                }
                else if (days > 3 || days < 0){
                    orgSchedulePresenter.printMenu(18);
                }
                else {
                    return days;
                }}catch (NumberFormatException nfe){
                userSchedulePresenter.printMenu(8);}}
    return null;}
    /**
     * Allows the organizer to choose an hour between 9am and 5pm.
     * @param scan The scanner.
     * @return Returns a int representing the hour chosen by the organizer.
     */
    public Integer pickHour(Scanner scan){
        boolean doContinue = true;
        while(doContinue) {
            try{
                orgSchedulePresenter.PrintRequestTalkProcess(2);
                String hourChoice = scan.nextLine();
                int hours = Integer.parseInt(hourChoice);
                if (hours == 0){
                    orgSchedulePresenter.printMenu(16);
                    return null;
                }
                else if (hours > 16 || hours < 9) {
                    orgSchedulePresenter.printMenu(19);
                }
                else {
                    return hours;
                }}catch (NumberFormatException nfe){
                userSchedulePresenter.printMenu(8);}}
    return null;}

    /**
     * Allows the organizer to choose a day and hour for the start time of the talk.
     * @param scan The scanner.
     * @param speaker The speaker chosen by the organizer.
     * @param room The room chosen by the organizer.
     * @return A LocalDateTime representing the start time chosen by the organizer.
     */
    public LocalDateTime pickTime(Scanner scan, Speaker speaker, Room room) {
        // first they pick a speaker, then they pick a room, then they pick a time and check if it works
        Integer day = pickDay(scan);
        if (day == null){return null;}
        Integer hour = pickHour(scan);
        if(hour == null){return null;}
        LocalDateTime dateTime = LocalDateTime.of(2020, 11,20+day,hour,0);
        return dateTime;}

    /**
     * Check if the room and speaker are double-booked, or if just the speaker is double-booked, or if just the room
     * is double booked.
     * @param speaker The speaker.
     * @param room The room.
     * @param dateTime The start time.
     * @return An int representing one of the three aforementioned options.
     */
    public int checkDoubleBooking(Speaker speaker, Room room, LocalDateTime dateTime){
         if(!userStorage.getSpeakerScheduleMap().get(speaker).checkDoubleBooking(dateTime)
                && !roomStorage.getScheduleList().get(room).checkDoubleBooking(dateTime)){return 1;}
        else if(!userStorage.getSpeakerScheduleMap().get(speaker).checkDoubleBooking(dateTime)){
            return 2;
        }
        else if(!roomStorage.getScheduleList().get(room).checkDoubleBooking(dateTime)){return 3;}
        else{return 0;}
    }

    /**
     * Allows the organizer to create talk.
     * @param scan The scanner.
     * @return A boolean notifying the organizer that they have successfully created a talk.
     */
    public boolean requestTalk(Scanner scan){
        Speaker speaker = pickSpeaker(scan);
        if (speaker == null){return false;}
        Room room = pickRoom(scan);
        if (room == null){return false;}
        LocalDateTime dateTime = pickTime(scan, speaker, room);
        if (dateTime==null){ return false;}
        int doubleBookingChecker = checkDoubleBooking(speaker, room, dateTime);
        while (doubleBookingChecker !=0){
        if(doubleBookingChecker== 1) {
            orgSchedulePresenter.PrintRequestTalkProcess(6);
            orgSchedulePresenter.PrintRequestTalkProcess(5);
        }
        else if(doubleBookingChecker==2){
        orgSchedulePresenter.PrintRequestTalkProcess(3);
        orgSchedulePresenter.PrintRequestTalkProcess(5);}
        else if(doubleBookingChecker==3){
        orgSchedulePresenter.PrintRequestTalkProcess(4);
        orgSchedulePresenter.PrintRequestTalkProcess(5);
        }
        speaker = pickSpeaker(scan);
            if (speaker == null){return false;}
        room = pickRoom(scan);
            if (room == null){return false;}
        dateTime = pickTime(scan,speaker,room);
            if (dateTime==null){ return false;}
        doubleBookingChecker = checkDoubleBooking(speaker, room, dateTime);
        };
        orgSchedulePresenter.PrintRequestTalkProcess(9);
        String talkTitle = scan.nextLine();
        if (eventManager.createTalk(talkTitle, speaker.getEmail(), room.roomName, dateTime)){
            orgSchedulePresenter.PrintRequestTalkProcess(7);
            return true;
        }
        else{return  false;}
    }

    /**
     * Allows the organizer to create a room with the specified name.
     * @param roomName The name of the room.
     * @return A boolean notifying the organizer if they have successfully created a room.
     */
    public boolean addRoom(String roomName) {
        return this.roomStorage.createRoom(roomName);
    }

    /**
     * Allows the organizer to create a speaker with the specified name, password, and email.
     * @param name The name of the speaker.
     * @param password The password of the speaker.
     * @param email The email of the speaker.
     * @return A boolean notifying the organizer if they have successfully created a speaker.
     */
    public boolean requestSpeaker(String name, String password, String email) {
        return this.userStorage.createUser("Speaker", name, password, email);
    }

    /**
     * Uses the addRoom method to register a room.
     * @param scan The scanner.
     */
    public void registerRoom(Scanner scan){
        orgSchedulePresenter.printMenu(9);
        boolean doContinue = true;
        while(doContinue){
        String roomName = scan.nextLine();
        try{
        if (Integer.parseInt(roomName) == 0){
            return;
        }
    }catch (NumberFormatException nfe){
            if (this.addRoom(roomName)){
                orgSchedulePresenter.printMenu(11);
                return;}
            else{
                orgSchedulePresenter.printMenu(20);
            }}
        }}

    /**
     * Uses the requestSpeaker to create a speaker.
     * @param scan The Scanner.
     */
    public void registerSpeaker(Scanner scan){
        orgSchedulePresenter.printMenu(10);
        String name = scan.nextLine();
        String password = scan.nextLine();
        String email = scan.nextLine();
        while(!email.contains("@")){
            orgSchedulePresenter.printMenu(21);
            email = scan.nextLine();
        }
        if (this.requestSpeaker(name, password, email)){
            orgSchedulePresenter.printMenu(11);
        }
    }
    /**
     * Lists all the available actions an organizer can perform and choose from, takes their input and outputs a text UI.
     */
    public void run(){
        orgSchedulePresenter.printHello(organizer);
        orgSchedulePresenter.printMenu(1);
        orgSchedulePresenter.printMenu(2);
        Scanner scan = new Scanner(System.in);
        boolean doContinue = true;
        while(doContinue) {
            String choice = scan.nextLine();
            try {
                int command = Integer.parseInt(choice);
            //if they want to register for a talk
            if (command == 1) {
                this.registerTalk(userSchedulePresenter, scan, organizer, signUpMap);
                orgSchedulePresenter.printMenu(1);
                //If they want to see all available talks
            }else if (command == 2) {
                this.seeAllTalks(userSchedulePresenter, scan);
                orgSchedulePresenter.printMenu(1);
                //if they want to see all the talks they are currently registered for
            }else if (command == 3) {
                this.seeAllRegistered(userSchedulePresenter, scan, organizer);
                orgSchedulePresenter.printMenu(1);
                // if they want to cancel a registration
            }else if (command == 4) {
                this.cancelATalk(userSchedulePresenter, scan, organizer, signUpMap);
                orgSchedulePresenter.printMenu(1);
            }else if (command == 5){
                this.requestTalk(scan);
                orgSchedulePresenter.printMenu(1);
            }else if (command == 6){
                this.registerRoom(scan);
                orgSchedulePresenter.printMenu(1);
            }else if (command == 7){
                this.registerSpeaker(scan);
                orgSchedulePresenter.printMenu(1);
            }
            else if (command ==0){
                doContinue = false;
                mainMenuController.runMainMenu(organizer.getUser());
            }
            else{orgSchedulePresenter.printMenu(15);}}
            catch (NumberFormatException nfe){
                orgSchedulePresenter.printMenu(15);;
            }

        }
    }
    /**
     * Updating OrScheduleController's roomStorage and userStorage.
     * @param o An Observable.
     * @param arg An Object.
     */
    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof RoomStorage){
            this.roomStorage = (RoomStorage) arg;
        }
        if (arg instanceof  UserStorage){
            this.userStorage = (UserStorage) arg;
        }

    }
    /**
     * Sets the signUpMap for OrgScheduleController.
     * @param signUpMap The signUpMap.
     */
    public void setSignUpMap(HashMap<Event, SignUpAttendeesManager> signUpMap){
        this.signUpMap = signUpMap;
    }
}
