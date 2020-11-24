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
    private User user; //This user is gotten from LogInController
    public MainMenuPresenter presenter;
    public UserScheduleController userScheduleController;
    public AttendeeMessengerController attendeeMessengerController;
    public SpeakerScheduleController speakerScheduleController;
    public SpeakerMessengerController speakerMessengerController;
    public OrgScheduleController orgScheduleController;
    public OrganizerMessengerController orgMessengerController;
    public Scanner scanner;
    private RoomSystem roomSystem;
    private TalkSystem talkSystem;
    private MessagingSystem messagingSystem;
    private ScheduleSystem scheduleSystem;
    private TechConferenceSystem techConferenceSystem;

    /**
     * A Constructor for a MainMenuController, which initializes all of the systems needed to be accessed from the
     * MainMenu.
     * @param scanner A Scanner object that allows the user to interact with the console.
     * @param roomSystem A RoomSystem object that is used to save data concerning Rooms.
     * @param talkSystem A TalkSystem object that is used to save data concerning Talks.
     * @param messagingSystem A MessagingSystem object that is used to save data concerning Messages and Conversations.
     * @param scheduleSystem A ScheduleSystem object that is used to save data concerning Schedules.
     * @param techConferenceSystem A TechConferenceSystem object that is used to save data concerning the UserList.
     */

    public MainMenuController(Scanner scanner, RoomSystem roomSystem, TalkSystem talkSystem,
                              MessagingSystem messagingSystem, ScheduleSystem scheduleSystem,
                              TechConferenceSystem techConferenceSystem) {

        this.roomSystem = roomSystem;
        this.talkSystem = talkSystem;
        this.messagingSystem = messagingSystem;
        this.scheduleSystem = scheduleSystem;
        this.techConferenceSystem = techConferenceSystem;
        this.presenter = new MainMenuPresenter();
        this.scanner = scanner;
    }

    /**
     * This method will run the Main Menu based on the type of the user that is provided.
     * @param user the user provided
     */
    public void runMainMenu(User user) {
        presenter.printHello(user);
        if (user instanceof Attendee) {
            runMainMenuAttendee();
        } else if (user instanceof Speaker) {
            runMainMenuSpeaker();
        } else if (user instanceof Organizer) {
            runMainMenuOrganizer();
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
                if (command == 1) {
                    this.userScheduleController.run();
                    return;
                } else if (command == 2) {
                    this.attendeeMessengerController.run();
                    return;
                } else if (command == 0) {
                    //Run a log out sequence
                    //Call all of the write signals to "save" everything that has been done by the user
                    logout();
                    presenter.loggingOut();
                    return; //Exit the while loop
                } else {
                    presenter.tryAgain();
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
                if (command == 1) {
                    this.speakerScheduleController.run();
                    return;
                } else if (command == 2) {
                    this.speakerMessengerController.run();
                    return;
                } else if (command == 0) {
                    //Run a log out sequence
                    //Call all of the write signals to "save" everything that has been done by the user
                    logout();
                    presenter.loggingOut();
                    return; //Exit the while loop
                } else {
                    presenter.tryAgain();
                }
            }catch (NumberFormatException nfe){
                presenter.tryAgain();
            }
        }}

    /**
     * Helper method for presenting a Organizers' Main Menu
     */
    private void runMainMenuOrganizer () {
        presenter.printMainMenuInfo(); //Display Main Menu
        boolean check = true; // fix create while loop
        while (check) {
            String choice = scanner.nextLine();
            try {
                int command = Integer.parseInt(choice);
                if (command == 1) {
                    this.orgScheduleController.run();
                    return;
                } else if (command == 2) {
                    this.orgMessengerController.run();
                    return;
                } else if (command == 0) {
                    //Run a log out sequence
                    //Call all of the write signals to "save" everything that has been done by the user
                    logout();
                    presenter.loggingOut();
                    return; //Exit the while loop
                } else {
                    presenter.tryAgain();
                }
            }catch (NumberFormatException nfe){
                presenter.tryAgain();
            }
    }}


    /**
     * Helper method that will call all of the save methods to update the database before logging out.
     */
    private void logout() {
        this.techConferenceSystem.save();
        this.roomSystem.save();
        this.talkSystem.save();
        this.messagingSystem.save();
        this.scheduleSystem.save();
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
        if (arg instanceof User) {
            this.user = (User) arg;
        } else if (arg instanceof OrgScheduleController) {
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
    }
