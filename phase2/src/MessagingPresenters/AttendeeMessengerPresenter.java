package MessagingPresenters;

/**
 * A Presenter class that handles messages sent and received by an attendee.
 */

public class AttendeeMessengerPresenter extends MessengerPresenter {
    @Override
    public void printWelcomeMessage() {
        System.out.println(System.lineSeparator() + "Welcome to the attendee messenger client");
    }

    @Override
    /**
     * Method to print the messaging menu prompt.
     */
    public void printMessagesMenu() {
        System.out.println("Press 3 to send a message to a user");
    }
}