package UserLogin;

/**
 * A class that represents an Organizer, a specific type of User that gives organizes certain things around the
 * conference, including scheduling Events with Speakers and Rooms. An organizer can also create a Speaker account,
 * add a Room to the list of rooms, cancel or reschedule events (Phase 2), and message all Attendees, an individual
 * attendee, all Speakers or an individual Speaker.
 */

public class Organizer extends User{

    /**
     * A constructor for an Organizer
     * @param name the name of the Organizer
     * @param password the password of the Organizer
     * @param email the email of the Organizer
     */
    public Organizer(String name, String password, String email) {
        super(name, password, email);
    }

    /**
     * A method used to get the type of User.
     * @return a string specifying the type of the User.
     */
    @Override
    public String getType() {
        return "Organizer";
    }
}
