package Schedule;

import Files.JSONReader;
import Files.JSONWriter;
import UserLogin.UserManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
        JSONReader jsonReader = new JSONReader();
        Object obj = jsonReader.readJson("src/Resources/Rooms.json");
        JSONArray roomList = (JSONArray) obj;
        roomList.forEach(roo -> {
            JSONObject room = (JSONObject) roo; //cast roo as a JSONObject
            String roomName = (String) room.get("roomname"); //fetch the name of the room
            roomStorage.createRoom(roomName); //create a room with the fetched roomName
        });
        eventSystem.run();
    }

    /**
     * Method to write the changes to the RoomFile, called in MainMenuController.logout().
     */
    public void save() {
        JSONWriter jsonWriter = new JSONWriter();
        jsonWriter.writeToRooms("src/Resources/Rooms.json", roomStorage);
    }
}
