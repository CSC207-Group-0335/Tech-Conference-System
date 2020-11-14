package UserLogin;

/**
 * A class that represents a Speaker, a specific type of User that gives Talks.
 */

public class Speaker extends User {
    public Speaker(String name, String password, String email) {
        super(name, password, email);
    }

    @Override
    public String getType() {
        return "Speaker";
    }
}
