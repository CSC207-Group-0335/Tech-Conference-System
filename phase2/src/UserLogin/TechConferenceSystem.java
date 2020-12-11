package UserLogin;

import Files.JSONReader;
import Files.JSONWriter;
import Schedule.RoomSystem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
            Object reqobj = (Object) user.get("requests");
            //code for reading and store the requests
            JSONArray requestList = (JSONArray) reqobj;
            LinkedHashMap<String, String> requestMap = new LinkedHashMap<String, String>();
            requestList.forEach(req -> {
                JSONObject request = (JSONObject) req;
                String reqName = (String) request.get("request");
                String status = (String) request.get("status");
                requestMap.put(reqName, status);
            });

            this.userManager.createUser(type, name, password, email, vip, requestMap);
        });
        roomSystem.run();
        logInController.runLogIn();
        mainMenuController.runMainMenu(logInController.getEmail());
    }

    //"requests":[{"request":"Vegan","status":"pending"},{"request":"Disabled","status":"pending"}]

    /**
     * Method to write the changes to the Users.json.
     */
    public void save() {
        JSONWriter jsonWriter = new JSONWriter();
        jsonWriter.writeToUsers("src/Resources/Users.json", this.userManager);
    }
}
