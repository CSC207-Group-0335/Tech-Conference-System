package Schedule;

import Files.CSVWriter;
import Files.TxtIterator;
import UserLogin.UserManager;

import java.util.*;

/**
 * A gateway class that reads Reads a .csv file for all rooms with
 * its information (name, capacity) and requests creating accounts (from RoomStorage)
 */
public class RoomSystem extends Observable {

    public RoomStorage roomStorage;
    public EventSystem eventSystem;

    /**
     * Creates a new RoomSystem.
     */
    public RoomSystem(UserManager userManager){
        this.roomStorage = new RoomStorage();
        this.eventSystem = new EventSystem(userManager, this.roomStorage);
    }

    /**
     * main method called for RoomSystem.
     */
    public void run() throws Exception {
        TxtIterator txtIterator = new TxtIterator("src/Resources/RoomFile");
        for(String room: txtIterator.getProperties()){
            roomStorage.createRoom(room);
        }
        eventSystem.run();
    }

    /**
     * Method to write the changes to the RoomFile, called in MainMenuController.logout().
     */
    public void save() {
        CSVWriter csvWriter = new CSVWriter();
        csvWriter.writeToRooms("src/Resources/RoomFile", roomStorage);
    }
}
