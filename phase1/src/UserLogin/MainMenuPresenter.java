package UserLogin;

/**
 * A class that works with the MainMenuController to display the main menu to the User, based on User type, and
 * allow the user to navigate to different "screens" based on input.
 */

public class MainMenuPresenter {

    public void printMainMenuInfoAttendee(int i) {
        switch (i) {
            case 0: {
                System.out.println("Welcome to our Tech Conference. This is the main menu. Navigate to different " +
                        "screens by typing \"1 -> Conference Schedule\", \"2 -> Personal Schedule\", " +
                        "\"3 -> Sign up for a Talk\", \"4 -> Cancel Talk registration\",  \"5 -> New Message\" " +
                        "\"View Messages\"");
                break;
            }
            case 1: {
                System.out.println("Here is the current Conference Schedule: ");
                break;
            }
            case 2: {
                System.out.println("Here is your Personal Conference Schedule");
                break;
            }
            case 3: {
                System.out.println("Which Talk would you like to sign up for?");
                break;
            }
            case 4: {
                System.out.println("Which Talk would you like to cancel?");
                break;
            }
            case 5: {
                System.out.println("Please specify the user that you would like to message: ");
                break;
            }
            case 6: {
                System.out.println("Here are all your current messages: ");
                break;
            }
        }
    }

    public void printMainMenuInfoSpeaker(int i) {
        if (i == 1) {
            System.out.println("Welcome to our Tech Conference. This is the main menu. Navigate to different screens" +
                    "by typing \"Messages\", \"Schedule\" ");
        } else if (i == 2) {
            System.out.println("Enter your password: ");
        } else if (i == 3) {
            System.out.println("Login Successful");
        } else if (i == 4) {
            System.out.println("Email and password combination not found, try again.");
        }
    }

    public void printMainMenuInfoOrganizer(int i) {
        if (i == 1) {
            System.out.println("Welcome to our Tech Conference. This is the main menu. Navigate to different screens" +
                    "by typing \"Messages\", \"Schedule\" ");
        } else if (i == 2) {
            System.out.println("Enter your password: ");
        } else if (i == 3) {
            System.out.println("Login Successful");
        } else if (i == 4) {
            System.out.println("Email and password combination not found, try again.");
        }
    }
}
