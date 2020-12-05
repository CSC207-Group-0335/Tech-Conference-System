package UserLogin;

import java.util.*;

/**
 * A Use Case class that handles the creation and storage of all users in the database.
 */

public class UserManager extends Observable {
    public ArrayList<User> userList;
    public ArrayList<Speaker> speakerList;

    /**
     * Each user in UserStorage has an associated instance of UserScheduleManager.
     */

    public UserManager() {
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

    public ArrayList<String> getUserEmailList(){
        ArrayList<String> userEmailList = new ArrayList<>();
        for(User u: userList){
            userEmailList.add(u.getEmail());
        }
        return userEmailList;
    }

    public ArrayList<Speaker> getSpeakerList() {
        return speakerList;
    }

    /**
     * Add an event to the users list of registered events, if they are not currently registered for that event.
     * @param email the email of the user who is attempting to register for an event
     * @param eventId the id of the event that the for which the user is attempting to register.
     * @return a boolean value indicating whether the registration was successful.
     */
    public boolean addEvent(String email, String eventId){
        User user = emailToUser(email);
        if (!user.getEventList().contains(eventId)) {
            user.addEvent(eventId);
            return true;
        }
        return false;
    }

    public boolean removeEvent(String userEmail, String eventId){
        return emailToUser(userEmail).removeEvent(eventId);
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

    /**
     * Get the type of the user that is associated with the email provided.
     * @param email the provided email of the user.
     * @return the user's type, or null if no such user exists.
     */
    public String emailToType(String email){
        User user = emailToUser(email);
        if (user != null) {
            return user.getType();
        }
        return null;
    }

    /**
     * Get the name of the user that is associated with the email provided.
     * @param email the provided email of the user.
     * @return the user's name, or null if no such user exists.
     */
    public String emailToName(String email){
        User user = emailToUser(email);
        if (user != null) {
            return user.getName();
        }
        return null;
    }

    /**
     * Get the password of the user that is associated with the email provided.
     * @param email the provided email of the user.
     * @return the user's password, or null if no such user exists.
     */
    public String emailToPassword(String email){
        User user = emailToUser(email);
        if (user != null) {
            return user.getPassword();
        }
        return null;
    }
    //Delete if not used

    public boolean emailToVIPStatus(String email){
        if (emailToType(email) == "Organizer"){
            return true;
        }
        if (emailToType(email) == "Speaker"){
            return false;
        }
        else{
            return ((Attendee)emailToUser(email)).getVIPStatus();
        }
    }

    /**
     * Get the TalkList of the user that is associated with the email provided.
     * @param email the provided email of the user.
     * @return the user's TalkList, or null if no such user exists.
     */
    public ArrayList<String> emailToTalkList(String email){
        User user = emailToUser(email);
        if (user != null) {
            return user.getEventList();
        }
        return null;
    }

    /**
     * Creates a list of all speakers in the program
     * @return An ArrayList represent the list of all speakers.
     */
    public ArrayList<String> getSpeakerEmailList(){
        ArrayList<String> speakerList = new ArrayList<String>();
        for(User u: this.userList){
            if (u instanceof Speaker){
                speakerList.add(u.getEmail());
            }
        }
        return speakerList;
    }

    public ArrayList<String> getSpeakerNameList(){
        ArrayList<String> speakerNameList = new ArrayList<String>();
        for (String email: getSpeakerEmailList()) {
            speakerNameList.add(emailToName(email));
        }
        return speakerNameList;
    }


    public boolean requestNotAddressed(String email) {
        return false;
    }

    public boolean requestNotRepeat(String req, String email) {
        Attendee attendee = (Attendee) this.emailToUser(email);
        if (!(attendee == null)){
            for (String r : attendee.getRequests()){
                if (req.equals(r)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean addRequest(String email, String request) {
        Attendee attendee = (Attendee) this.emailToUser(email);
        return attendee.setRequests(request);
    }

    public ArrayList<String> getRequestList(String email) {
        Attendee attendee = (Attendee) this.emailToUser(email);
        return attendee.getRequests();
    }
}



