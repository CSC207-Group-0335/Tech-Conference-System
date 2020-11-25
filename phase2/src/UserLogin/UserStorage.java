package UserLogin;
import Schedule.SpeakerScheduleManager;
import Schedule.UserScheduleManager;

import java.util.*;

/**
 * A Use Case class that handles the creation and storage of all users in the database.
 */

public class UserStorage extends Observable {
    public ArrayList<User> userList;
    public ArrayList<Speaker> speakerList;

    /**
     * Each user in UserStorage has an associated instance of UserScheduleManager.
     */

    public UserStorage() {
        this.userList = new ArrayList<>();
        this.speakerList = new ArrayList<>();

    }

    /**
     * creates a new user object and places it in UserList. Generates an associated UserScheduleManager with the new
     * user as the argument.
     * @return true if a new user is created, false otherwise.
     */

    public boolean createUser(String usertype, String name, String password, String email) {
        //Create instance of user depending on usertype
        // First check if email is already in system
        if (!(checkIfValidEmail(email))){
            return false;
        }
        User newuser = createUserOfInstance(usertype, name, password, email);
        if (newuser == null) {
            return false;
        }
        //Add the user to the UserList
        this.userList.add(newuser);
        //Add the Attendee/Organizer user to UserScheduleList
        if (newuser instanceof Speaker){
            this.speakerList.add((Speaker) newuser);
        }
        return true;
    }

    /**
     * @return the list of users registered in the UserStorage
     */
    public ArrayList<User> getUserList() {
        return userList;
    }
    public ArrayList<Speaker> getSpeakerList() {
        return speakerList;
    }

    /**
     * @return the UserScheduleMap for the User, specfically when the user is not a Speaker and has the
     * ability to sign up to attend talks.
     */

    /**
     * @return the SpeakerSchedule map for the User, specifically when the user is a Speaker.
     */

    /**
     * Used to find the user object that is associated with the inputted email.
     * @param email the inputted email (A string).
     * @return the user associated with the given email, or null if no such user is found.
     */
    public User emailToUser(String email){
        for(User user: this.userList){
            if (user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }

    public boolean addEvent(String email, String talkid){
        User user = emailToUser(email);
        if (!user.getTalklist().contains(talkid)) {
            user.getTalklist().add(talkid);
            return true;
        }
        return false;
    }
    /**
     * Used to help create a new user object. A new user is created based on the type that is specified in the
     * usertype parameter.
     * @return a new user object (note that it could be null)
     */
    private User createUserOfInstance(String usertype, String name, String password, String email){
        User newuser = null;

        //I think here would be a good place to see if the email is valid/has not been used before.
        switch (usertype) {
            case "Attendee": {
                newuser = new Attendee(name, password, email);
                break;
            }
            case "Organizer": {
                newuser = new Organizer(name, password, email);
                break;
            }
            case "Speaker": {
                newuser = new Speaker(name, password, email);
                break;
            }
        }
        return newuser;
    }

    /**
     * Helper method to check if the provided email is valid by comparing it to other emails in the userList and
     * ensuring that the provided email is unique.
     * @param email the provided email.
     * @return a boolean value indicating whether or not the Email is valid.
     */
    private boolean checkIfValidEmail(String email){
        for (User account: this.userList){
            if((account.getEmail()).equals(email)){
                return false;
            }
        }
        return true;

    }

}



