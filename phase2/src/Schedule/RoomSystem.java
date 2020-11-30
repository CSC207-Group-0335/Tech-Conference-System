package Schedule;

import Files.CSVWriter;
import Files.TxtIterator;
import UserLogin.MainMenuController;
import UserLogin.UserStorage;

import java.util.*;

/**
 * A gateway class that reads Reads a .csv file for all rooms with
 * its information (name, capacity) and requests creating accounts (from RoomStorage)
 */
public class RoomSystem extends Observable {

    public ArrayList<Room> roomList;
    public HashMap<Room, RoomScheduleManager> roomScheduleManagerList;
    public RoomStorage roomStorage;
    public EventSystem eventSystem;
    public ScheduleSystem scheduleSystem;

    /**
     * Creates a new RoomSystem.
     */
    public RoomSystem(UserStorage userStorage, MainMenuController mainMenuController){
        this.roomStorage = new RoomStorage();
        this.eventSystem = new EventSystem(userStorage, this.roomStorage, mainMenuController);
        this.roomList = new ArrayList<Room>();
        this.roomScheduleManagerList = new HashMap<Room, RoomScheduleManager>();
    }

    /**
     * main method called for RoomSystem.
     */
    public void run(){
        TxtIterator txtIterator = new TxtIterator("src/Resources/RoomFile");
        for(String room: txtIterator.getProperties()){
            roomStorage.createRoom(room);
        }
        eventSystem.run();
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }

    /**
     * Method to write the changes to the RoomFile, called in MainMenuController.logout().
     */
    public void save() {
        CSVWriter csvWriter = new CSVWriter();
        csvWriter.writeToRooms("phase1/src/Resources/RoomFile", this.getRoomList());
    }
}
