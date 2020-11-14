package UserLogin;

/**
 * A class that represents an Organizer, a specific type of User that gives organizes certain things around the
 * conference, including scheduling Talks with Speakers and Rooms. An organizer can also create a Speaker account,
 * add a Room to the list of rooms, cancel or reschedule events (Phase 2), and message all Attendees, an individual
 * attendee, all Speakers or an individual Speaker.
 */

public class Organizer extends User{
    public Organizer(String name, String password, String email) {
        super(name, password, email);
    }

    @Override
    public String getType() {
        return "Organizer";
    }
}
