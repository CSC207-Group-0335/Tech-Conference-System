package UserLogin;

/**
 * An abstract class that represents a User.
 */

public abstract class User {
    private String name;
    private String password;
    private String email;

    /**
     * A user is identified by a name, password and email address (the email will be used as a unique identifier.
     * @param name the name of the user.
     * @param password the password of the user.
     * @param email a unique string representing the email address of the user that they have used to
     *              register for the conference.
     */

    public User(String name, String password, String email){
        this.name = name;
        this.password = password;
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
