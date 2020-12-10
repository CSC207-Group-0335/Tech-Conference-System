package MessagingPresenters;

/**
 * A Presenter class that handles messages sent and received by an organizer.
 */

public class OrganizerMessengerPresenter extends MessengerPresenter {
    @Override
    public void printWelcomeMessage() {
        System.out.println(System.lineSeparator() + "Welcome to the organizer messenger client");
    }

    @Override
    public void printMessagesMenu() {
        System.out.println("Press 3 to message one user" + System.lineSeparator() +
                "Press 4 to message all speakers" + System.lineSeparator() +
                "Press 5 to message all attendees");
    }
}
