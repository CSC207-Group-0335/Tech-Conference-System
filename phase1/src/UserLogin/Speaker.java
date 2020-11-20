package UserLogin;

/**
 * A class that represents a Speaker, a specific type of User that gives Talks.
 */

public class Speaker extends User {

    /**
     * A constructor for a Speaker
     * @param name the name of the Speaker
     * @param password the password of the Speaker
     * @param email the email of the Speaker
     */
    public Speaker(String name, String password, String email) {
        super(name, password, email);
    }

    /**
     * A method used to get the type of User.
     * @return a string specifying the type of the User.
     */
    @Override
    public String getType() {
        return "Speaker";
    }
}
