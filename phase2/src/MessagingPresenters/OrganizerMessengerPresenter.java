package MessagingPresenters;

import java.util.ArrayList;

/**
 * A Presenter class that handles messages sent and received by an organizer.
 */

public class OrganizerMessengerPresenter extends MessengerPresenter {
    @Override
    public void printWelcomeMessage() {
        System.out.println("Welcome to the organizer messenger client" + System.lineSeparator());
    }

    @Override
    public void printMessagesMenu() {
        System.out.println("Press 1 to message one user" + System.lineSeparator() +
                "Press 2 to message all speakers" + System.lineSeparator() +
                "Press 3 to message all attendees" + System.lineSeparator());
    }
}
