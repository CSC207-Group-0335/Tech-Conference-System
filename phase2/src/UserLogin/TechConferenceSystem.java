package UserLogin;

import Files.CSVWriter;
import Files.JSONReader;
import Files.JSONWriter;
import Schedule.RoomSystem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * A Gateway class that interacts with an external file (.csv) that stores a collection of all accounts currently
 * registered in the database.
 */

public class TechConferenceSystem {

    public UserManager userManager;
    public LogInController logInController;
    public RoomSystem roomSystem;
    public MainMenuController mainMenuController;

    /**
     * A constructor for the TechConferenceSystem that contains an instance of everything needed to run the program,
     * and everything that should be "common" to all instances of TechConferenceSystem.
     */

    public TechConferenceSystem() {
        this.userManager = new UserManager();
        this.roomSystem = new RoomSystem(userManager);
        this.mainMenuController = new MainMenuController(roomSystem,
                roomSystem.eventSystem, roomSystem.eventSystem.messagingSystem, roomSystem.eventSystem.scheduleSystem,
                userManager,this);
        this.logInController = new LogInController(this.mainMenuController, this.roomSystem.eventSystem,
                this.roomSystem.eventSystem.messagingSystem, userManager);
    }

    /**
     * The main run method for the entire program.
     * The system is run starting with Login, which, if successful, prompts a main menu, which the user can navigate
     * to prompt the different screens and do specific actions. The program will quit when the user logs out, and
     * update any files with possible changes that were made during the active session.
     */

    public void run() throws Exception {
        JSONReader jsonReader = new JSONReader();
        Object obj = jsonReader.readJson("src/Resources/Users.json");
        JSONArray userList = (JSONArray) obj;
        userList.forEach(use -> {
            JSONObject user = (JSONObject) use; //cast use as a JSONObject
            //get all of the necessary elements to create a user from the user object
            String type = (String) user.get("type");
            String name = (String) user.get("name");
            String password = (String) user.get("password");
            String email = (String) user.get("email");
            boolean vip = (boolean) user.get("vip");
            //LinkedHashMap<String, String> requests = (LinkedHashMap<String, String>) user.get("requests");
            //Just here to get the program running
            LinkedHashMap<String, String> requestMap = new LinkedHashMap<String, String>();
            this.userManager.createUser(type, name, password, email, vip, requestMap);
        });
        roomSystem.run();
        logInController.runLogIn();
        mainMenuController.runMainMenu(logInController.getEmail());
    }

    /**
     * Method to write the changes to the Users.json.
     */
    public void save() {
        JSONWriter jsonWriter = new JSONWriter();
        jsonWriter.writeToUsers("src/Resources/Users.json", this.userManager);
    }
}
