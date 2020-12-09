package MessagingPresenters;

import Schedule.Event;

import java.util.ArrayList;

/**
 * A Presenter class that handles messages sent and received by a speaker.
 */

public class SpeakerMessengerPresenter extends MessengerPresenter {
    @Override
    public void printWelcomeMessage() {
        System.out.println("Welcome to the speaker messenger client");
    }

    @Override
    public void printMessagesMenu() {
        System.out.println("Press 3 to message one user" + System.lineSeparator() +
                "Press 4 to message all attendees" + System.lineSeparator() +
                "Press 5 to message all attendees of a single event.");
    }

    /**
     * Prints a list of events.
     *
     * @param events an ArrayList containing events
     */

    public void viewEvents(ArrayList<Event> events) {
        if (events.size() == 0) {
            System.out.println("No events found");
        } else {
            int i = 1;
            for (Event event : events) {
                System.out.println(i + " - " + event.getEventId() + ": " + event.getTitle() + " which starts at " +
                        event.getStartTime().toString().replace("T", " "));
                i++;
            }
            System.out.println("Input the number corresponding to the event you wish to view or press 0 to exit:");
        }
    }
}