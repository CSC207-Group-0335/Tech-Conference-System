package MessagingPresenters;

/**
 * A Presenter class that handles messages sent and received by an attendee.
 */

public class AttendeeMessengerPresenter extends MessengerPresenter {
    @Override
    public void printWelcomeMessage() {
        System.out.println("Welcome to the attendee messenger client");
    }

    @Override
    public void printMessagesMenu() {
        System.out.println("Press 3 to send a message to a user");
    }
}