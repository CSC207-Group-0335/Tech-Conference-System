package MessagingPresenters;

/**
 * A Presenter class that handles messages sent and received by a speaker.
 */

public class SpeakerMessengerControllerPresenter{
    public void printMenu(int i) {
        switch (i) {
            case 0:
                System.out.println("Welcome to the speaker messenger client" + System.lineSeparator() +
                        "Press 0 to quit the speaker messenger client" + System.lineSeparator() +
                        "Press 1 to message one user" + System.lineSeparator() +
                        "Press 2 to message all attendees");
                break;
            case 1:
                System.out.println("Quitting the speaker messenger client");
                break;
            case 2:
                System.out.println("Please enter the recipient's email address:");
                break;
            case 3:
                System.out.println("Please enter the content of your text:");
                break;
            case 4:
                System.out.println("Message successfully sent!");
                break;
            case 5:
                System.out.println("Email address not found. Please try again.");
        }
    }
}