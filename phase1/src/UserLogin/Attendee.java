package UserLogin;

/**
 * A class that represents an Attendee, a specific type of User that attends the conference and can sign up to
 * attend Talks.
 */

public class Attendee extends User {
    public Attendee(String name, String password, String email) {
        super(name, password, email);
    }
}
