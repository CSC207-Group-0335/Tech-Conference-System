package Schedule;

import Files.CSVWriter;
import Files.TxtIterator;

import java.util.*;

/**
 * A gateway class that reads Reads a .csv file for all rooms with
 * its information (name, capacity) and requests creating accounts (from RoomStorage)
 */
public class RoomSystem extends Observable {

    public ArrayList<Room> roomList;
    public HashMap<Room, RoomScheduleManager> roomScheduleManagerList;
    public RoomStorage roomStorage;
    public TalkSystem talkSystem;
    public ScheduleSystem scheduleSystem;

    /**
     * Creates a new RoomSystem.
     */
    public RoomSystem(){
        this.roomStorage = new RoomStorage();
        this.talkSystem = new TalkSystem();
        this.roomList = new ArrayList<Room>();
        this.roomScheduleManagerList = new HashMap<Room, RoomScheduleManager>();
    }

    /**
     * main method called for RoomSystem.
     */
    public void run(){
        this.addObserver(this.talkSystem.talkManager);
        if (this.talkSystem.orgScheduleController != null) {
            this.addObserver(this.talkSystem.orgScheduleController);
        }
        TxtIterator txtIterator = new TxtIterator("phase1/src/Resources/RoomFile");
        for(String room: txtIterator.getProperties()){
            roomStorage.createRoom(room);
        }
        setRoomStorage();
        setRoomList(this.roomStorage.getRoomList());
        setRoomScheduleManagerList(this.roomStorage.getScheduleList());

        talkSystem.run();
    }

    /**
     * Sets room list.
     * @param roomlst The roomlist.
     */
    public void setRoomList(ArrayList<Room> roomlst) {
        this.roomList = roomlst;
        setChanged();
        notifyObservers(roomList);
    }

    /**
     * Sets room schedule manager list.
     * @param roomSchedList the HashMap representing the room schedule list.
     */
    public void setRoomScheduleManagerList(HashMap<Room, RoomScheduleManager> roomSchedList) {
        this.roomScheduleManagerList = roomSchedList;
        setChanged();
        notifyObservers(roomScheduleManagerList);
    }

    /**
     * Sets room storage.
     */
    public void setRoomStorage(){
        setChanged();
        notifyObservers(this.roomStorage);
    }

    /**
     * Sets room list.
     * @return An ArrayList representing the room list.
     */
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
