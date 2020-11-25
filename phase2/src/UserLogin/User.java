package UserLogin;

import java.util.ArrayList;

/**
 * An abstract class that represents a User.
 */

public abstract class User {
    private String name;
    private String password;
    private String email;
    private ArrayList<String> talklist;

    /**
     * A user is identified by a name, password and email address (the email will be used as a unique identifier).
     * @param name the name of the user.
     * @param password the password of the user.
     * @param email a unique string representing the email address of the user that they have used to
     *              register for the conference.
     */

    public User(String name, String password, String email){
        this.name = name;
        this.password = password;
        this.email = email;
        this.talklist = new ArrayList<>();
    }

    /**
     * a getter for the Name of the User
     * @return the name of the user.
     */

    public String getName() {
        return name;
    }

    /**
     * a getter for the email of the User
     * @return the email of the user.
     */

    public String getEmail() {
        return email;
    }

    /**
     * a getter for the password of the User
     * @return the password of the user.
     */

    public String getPassword() {
        return password;
    }

    /**
     * an abstract getter for the type of the User
     * @return the type of the user.
     */

    public abstract String getType();

    public ArrayList<String> getTalklist() {
        return talklist;
    }
}
