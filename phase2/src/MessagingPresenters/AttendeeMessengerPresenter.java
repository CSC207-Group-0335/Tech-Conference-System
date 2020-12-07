package MessagingPresenters;

import java.util.ArrayList;

/**
 * A Presenter class that handles messages sent and received by an attendee.
 */

public class AttendeeMessengerPresenter extends MessengerPresenter {
    @Override
    public void printWelcomeMessage() {
        System.out.println("Welcome to the attendee messenger client" + System.lineSeparator());
    }

    @Override
    public void printMessagesMenu() {
        System.out.println("Press 1 to send a message to a user" + System.lineSeparator());
    }
}