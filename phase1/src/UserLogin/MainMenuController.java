package UserLogin;

import MessagingPresenters.AttendeeMessengerController;
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

    public MainMenuController(Scanner scanner) {

        this.presenter = new MainMenuPresenter();
        this.scanner = scanner;
    }

    /**
     * This method will run the Main Menu based on the type of the user that is provided.
     *
     * @param user the user provided
     */
    public void runMainMenu(User user) {
        presenter.printHello(user);
        if (user instanceof Attendee) {
            runMainMenuAttendee();
        } else if (user instanceof Speaker) {
            runMainMenuSpeaker();
        } else if (user instanceof Organizer) {
            getRunMainMenuOrganizer();
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
                } else if (command == 2) {
                    this.attendeeMessengerController.run();
                } else if (command == 0) {
                    //Run a log out sequence
                    //Call all of the write signals to "save" everything that has been done by the user
                    System.out.println("Logging Out...");
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
                } else if (command == 2) {
                    this.speakerMessengerController.run();
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
        private void getRunMainMenuOrganizer () {
            presenter.printMainMenuInfo(); //Display Main Menu
            boolean check = true; // fix create while loop
            while (check) {
                String choice = scanner.nextLine();
                try {
                    int command = Integer.parseInt(choice);
                    if (command == 1) {
                        this.orgScheduleController.run();
                    } else if (command == 2) {
                        this.orgMessengerController.run();
                    } else {
                        presenter.tryAgain();
                    }
                }catch (NumberFormatException nfe){
                    presenter.tryAgain();
                }
        }}


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
