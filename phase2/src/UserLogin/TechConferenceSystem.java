package UserLogin;

import Files.CSVReader;
import Files.CSVWriter;
import Schedule.RoomSystem;

import java.util.ArrayList;
import java.util.Observable;

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

    public void run() {
        CSVReader file = new CSVReader("src/Resources/Users.csv");
        for(ArrayList<String> user: file.getData()){
            this.userManager.createUser(user.get(0), user.get(1), user.get(2), user.get(3));}
        roomSystem.run();
        logInController.runLogIn();
        mainMenuController.runMainMenu(logInController.getEmail());
    }

    /**
     * Method to write the changes to the RoomFile, called in MainMenuController.logout().
     */
    public void save() {
        CSVWriter csvWriter = new CSVWriter();
        csvWriter.writeToUsers("src/Resources/Users.csv", this.userManager.getUserList());
    }
}
