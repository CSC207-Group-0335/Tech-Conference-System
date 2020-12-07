package UserLogin;

import MessagingPresenters.AttendeeMessengerController;
import MessagingPresenters.MessagingSystem;
import MessagingPresenters.OrganizerMessengerController;
import MessagingPresenters.SpeakerMessengerController;
import Schedule.*;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * A class that works with the MainMenuPresenter to display the main menu to the User, based on User type, and
 * allow the user to navigate to different "screens" based on input.
 */

public class MainMenuController implements Observer {
    public MainMenuPresenter presenter;
    public UserScheduleController userScheduleController;
    public AttendeeMessengerController attendeeMessengerController;
    public SpeakerScheduleController speakerScheduleController;
    public SpeakerMessengerController speakerMessengerController;
    public OrgScheduleController orgScheduleController;
    public OrganizerMessengerController orgMessengerController;
    public LogInController logInController;
    public Scanner scanner;
    private RoomSystem roomSystem;
    private EventSystem eventSystem;
    private MessagingSystem messagingSystem;
    private ScheduleSystem scheduleSystem;
    private TechConferenceSystem techConferenceSystem;
    private UserManager userManager;

    /**
     * A Constructor for a MainMenuController, which initializes all of the systems needed to be accessed from the
     * MainMenu.
     * @param roomSystem A RoomSystem object that is used to save data concerning Rooms.
     * @param eventSystem A TalkSystem object that is used to save data concerning Talks.
     * @param messagingSystem A MessagingSystem object that is used to save data concerning Messages and Conversations.
     * @param scheduleSystem A ScheduleSystem object that is used to save data concerning Schedules.
     * @param techConferenceSystem A TechConferenceSystem object that is used to save data concerning the UserList.
     */

    public MainMenuController(RoomSystem roomSystem, EventSystem eventSystem,
                              MessagingSystem messagingSystem, ScheduleSystem scheduleSystem, UserManager userManager,
                              TechConferenceSystem techConferenceSystem) {

        this.roomSystem = roomSystem;
        this.eventSystem = eventSystem;
        eventSystem.setMainMenuController(this);
        this.messagingSystem = messagingSystem;
        messagingSystem.setMainMenuController(this);
        this.scheduleSystem = scheduleSystem;
        this.userManager = userManager;
        this.techConferenceSystem = techConferenceSystem;
        this.presenter = new MainMenuPresenter();
    }

    /**
     * This method will run the Main Menu based on the type of the user that is provided.
     * @param useremail the users email provided, taken from userStorage
     */
    public void runMainMenu(String useremail) {
        presenter.printHello(userManager.emailToName(useremail));
        switch (userManager.emailToType(useremail)) {
            case "Attendee":
                runMainMenuAttendee();
                break;
            case "Speaker":
                runMainMenuSpeaker();
                break;
            case "Organizer":
                runMainMenuOrganizer();
                break;
        }
    }

    /**
     * Helper method for presenting a Attendee' Main Menu
     */
    private void runMainMenuAttendee() {
        presenter.printMainMenuInfo(); //Display Main Menu
        boolean check = true; // fix create while loop
        //present an option that allows the user to logout from the main menu
        //which would then turn check to false
        while (check) {
            String choice = scanner.nextLine();
            try {
                int command = Integer.parseInt(choice);
                switch (command) {
                    case 1:
                        this.userScheduleController.run();
                        return;
                    case 2:
                        this.attendeeMessengerController.run();
                        return;
                    case 0:
                        //Run a log out sequence
                        //Call all of the write signals to "save" everything that has been done by the user
                        logoutAndReRun();
                        presenter.loggingOut();
                        return; //Exit the while loop

                    default:
                        presenter.tryAgain();
                        break;
                }
            }catch (NumberFormatException nfe){
                presenter.tryAgain();
            }
        }
    }

    /**
     * Helper method for presenting a Speakers' Main Menu
     */
    private void runMainMenuSpeaker() {
        presenter.printMainMenuInfo(); //Display Main Menu
        boolean check = true; // fix create while loop
        while (check) {
            String choice = scanner.nextLine();
            try {
                int command = Integer.parseInt(choice);
                switch (command) {
                    case 1:
                        this.speakerScheduleController.run();
                        return;
                    case 2:
                        this.speakerMessengerController.run();
                        return;
                    case 0:
                        //Run a log out sequence
                        //Call all of the write signals to "save" everything that has been done by the user
                        logoutAndReRun();
                        presenter.loggingOut();
                        return; //Exit the while loop

                    default:
                        presenter.tryAgain();
                        break;
                }
            }catch (NumberFormatException nfe){
                presenter.tryAgain();
            }
        }}

    /**
     * Helper method for presenting a Organizers' Main Menu
     */
    private boolean runMainMenuOrganizer () {
        presenter.printMainMenuInfo(); //Display Main Menu
        boolean check = true; // fix create while loop
        while (check) {
            String choice = scanner.nextLine();
            try {
                int command = Integer.parseInt(choice);
                switch (command) {
                    case 1:
                        this.orgScheduleController.run();
                        return true;
                    case 2:
                        this.orgMessengerController.run();
                        return true;
                    case 0:
                        //Run a log out sequence
                        //Call all of the write signals to "save" everything that has been done by the user
                        return logoutAndReRun();
                    default:
                        presenter.tryAgain();
                        break;
                }
            }catch (NumberFormatException nfe){
                presenter.tryAgain();
            }

        }
        return false;}


    /**
     * Helper method that will call all of the save methods to update the database before logging out.
     */
    private boolean logoutAndReRun() {
        this.techConferenceSystem.save();
        this.roomSystem.save();
        this.eventSystem.save();
        this.messagingSystem.save();
        this.scheduleSystem.save();
        presenter.loggingOut();
        logInController.runLogIn();
        this.runMainMenu(logInController.getEmail());
        return true;
    }

    /**
     * A method used by the Observable Design Pattern to update variables in this Observer class based on changes made
     * in linked Observable classes. This one updates different Controllers based on which Observable is calling it
     * (and what the argument is an instance of).
     * @param o the Observable class where the change is made and this function is called.
     * @param arg the argument that is being updated.
     */

    @Override
    public void update (Observable o, Object arg){
        if (arg instanceof OrgScheduleController) {
            this.orgScheduleController = (OrgScheduleController) arg;
        } else if (arg instanceof AttendeeMessengerController) {
            this.attendeeMessengerController = (AttendeeMessengerController) arg;
        } else if (arg instanceof SpeakerScheduleController) {
            this.speakerScheduleController = (SpeakerScheduleController) arg;
        } else if (arg instanceof SpeakerMessengerController) {
            this.speakerMessengerController = (SpeakerMessengerController) arg;
        } else if (arg instanceof UserScheduleController) {
            this.userScheduleController = (UserScheduleController) arg;
        } else if (arg instanceof OrganizerMessengerController) {
            this.orgMessengerController = (OrganizerMessengerController) arg;
        }
    }

    public void setScanner(Scanner scanner){
        this.scanner = scanner;
    }

    public void setLogInController(LogInController logInController){
        this.logInController = logInController;
    }
}
