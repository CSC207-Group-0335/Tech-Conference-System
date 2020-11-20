package UserLogin;

/**
 * A class that represents an Attendee, a specific type of User that attends the conference and can sign up to
 * attend Talks.
 */

public class Attendee extends User {

    /**
     * A constructor for an Attendee
     * @param name the name of the Attendee
     * @param password the password of the Attendee
     * @param email the email of the Attendee
     */
    public Attendee(String name, String password, String email) {
        super(name, password, email);
    }

    /**
     * A method used to get the type of User.
     * @return a string specifying the type of the User.
     */
    @Override
    public String getType() {
        return "Attendee";
    }
}
