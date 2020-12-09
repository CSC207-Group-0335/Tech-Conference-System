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
        User newUser = createUserOfInstance(usertype, name, password, email);
        if (newUser == null) {
            return false;
        }
        //Add the user to the UserList
        this.userList.add(newUser);
        //Add the Attendee/Organizer user to UserScheduleList
        if (newUser instanceof Speaker){
            this.speakerList.add((Speaker) newUser);
        }
        return true;
    }

    /**
     * Returns true if a new user account with the given credentials was successfully created.
     *
     * @param usertype a String representing the user type
     * @param name a String representing the name of the user
     * @param password a String representing a password
     * @param email a String representing an email address
     * @param vip a boolean representing whether or not this user has VIP status
     * @return a boolean representing whether or not an account has been successfully created
     */

    public boolean createUser(String usertype, String name, String password, String email, boolean vip) {
        //Create instance of user depending on usertype
        // First check if email is already in system
        if (!(checkIfValidEmail(email))){
            return false;
        }
        User newUser = createUserOfInstance(usertype, name, password, email);
        if (newUser == null) {
            return false;
        }
        if (newUser instanceof Attendee) {
            ((Attendee) newUser).setVIPStatus(vip);
        }
        //Add the user to the UserList
        this.userList.add(newUser);
        //Add the Attendee/Organizer user to UserScheduleList
        if (newUser instanceof Speaker){
            this.speakerList.add((Speaker) newUser);
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
    private User createUserOfInstance(String userType, String name, String password, String email){
        User newUser = null;

        //I think here would be a good place to see if the email is valid/has not been used before.
        switch (userType) {
            case "Attendee": {
                newUser = new Attendee(name, password, email);
                break;
            }
            case "Organizer": {
                newUser = new Organizer(name, password, email);
                break;
            }
            case "Speaker": {
                newUser = new Speaker(name, password, email);
                break;
            }
        }
        return newUser;
    }

    /**
     * Helper method to check if the provided email is valid by comparing it to other emails in the userList and
     * ensuring that the provided email is unique.
     * @param email the provided email.
     * @return a boolean value indicating whether or not the Email is valid.
     */
    public boolean checkIfValidEmail(String email){
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
        if (emailToType(email).equals("Organizer")){
            return true;
        }
        if (emailToType(email).equals("Speaker")){
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

    public LinkedHashMap<String, String> emailToRequests(String email){
        User user = emailToUser(email);
        return user.getRequestMap();
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

    /**
     * Returns a list of speakers' email addresses.
     *
     * @return an ArrayList containing speakers' email addresses
     */

    public ArrayList<String> getSpeakerNameList(){
        ArrayList<String> speakerNameList = new ArrayList<String>();
        for (String email: getSpeakerEmailList()) {
            speakerNameList.add(emailToName(email));
        }
        return speakerNameList;
    }


    /**
     * Returns true if this request has not been addressed.
     *
     * @param req a String representing a request
     * @param email a String representing an email
     * @return a boolean representing whether or not this request is yet to addressed
     */

    public boolean requestNotAddressed(String req, String email) {
        Attendee attendee = (Attendee) this.emailToUser(email);
        if (!(attendee == null)){
            if (attendee.getRequests().containsKey(req)) {
                if (attendee.getRequests().get(req).equals("pending")) {
                    return true;
                }
                else {
                    return false;
                }
            }
            return false;
        }
        return false;
    }

    /**
     * Returns true if the request is not a repeat.
     *
     * @param req a String representing a request
     * @param email a String representing an email address
     * @return a boolean representing whether or not the request has already been sent
     */

    public boolean requestNotRepeat(String req, String email) {
        Attendee attendee = (Attendee) this.emailToUser(email);
        if (!(attendee == null)){
            for (String r : attendee.getRequests().keySet()){
                if (req.equals(r)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Returns true if the request has been set.
     *
     * @param email a String representing an email address
     * @param request a String representing a request
     * @return a boolean representing whether or not a request has been successfully sent
     */

    public boolean addRequest(String email, String request) {
        Attendee attendee = (Attendee) this.emailToUser(email);
        return attendee.setRequests(request);
    }


    /**
     * Returns a list of requests sent in by the user with email </email>.
     *
     * @param email a String representing an email address
     * @return an ArrayList containing requests
     */

    public ArrayList<String> getRequestList(String email) {
        ArrayList<String> requests = new ArrayList<>();
        Attendee attendee = (Attendee) this.emailToUser(email);
        requests.addAll(attendee.getRequests().keySet());
        return requests;
    }
//tried to get getRequestList to show the status of the request too and not just print the requests
    //public ArrayList<String> getRequestList(String email) {
        //ArrayList<String> requests = new ArrayList<>();
        //Attendee attendee = (Attendee) this.emailToUser(email);
        //LinkedHashMap<String, String> attendeeRequests = attendee.getRequests();
        //requests.addAll(attendeeRequests.keySet());
        //int i = 0;
        //for (String r : requests){
            //String requestStatus = attendeeRequests.get(r);
            //requests.set(0, r + ", " + requestStatus);
            //i++;
        //}
        //return requests;
    //}

    /**
     * Returns a HashMap of email addresses paired with a list of pending requests sent in by the attendee registered
     * under that email.
     *
     * @return a HashMap with email addresses as the keys and ArrayLists of requests as the values
     */

    public LinkedHashMap<String, ArrayList<String>> emailToRequest() {
        LinkedHashMap<String, ArrayList<String>> emailRequestMap = new LinkedHashMap<>();
        for (String email : getUserEmailList()) {
            if (this.emailToUser(email) instanceof Attendee) {
                ArrayList<String> userRequests = new ArrayList<>();
                for (String req : ((Attendee) this.emailToUser(email)).requests.keySet()) {
                    if (((Attendee) this.emailToUser(email)).requests.get(req).equals("pending")) {
                        userRequests.add(req);
                    }
                }
                emailRequestMap.put(email, userRequests);
            }
        }
        return emailRequestMap;
    }

    /**
     * Checks to see if the user is an attendee and if they have requests
     * @param user a user
     * @return a boolean that says if the user is an attendee and if they have requests
     */

    public boolean hasRequests(User user) {
        String email = user.getEmail();
        String typeUser = this.emailToType(email);
        if (typeUser.equals("Attendee")) {
            if (!(((Attendee) user).getRequests().isEmpty())) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * A helper function for userRequestsPending that adds the value that is pending to a list
     * @param user a user
     * @param requestsPending the list we want to add to
     */
    public void addRequests(Attendee user, ArrayList<String> requestsPending) {
        LinkedHashMap<String, String> userRequests = user.getRequests();
        for (Map.Entry<String, String> entry : userRequests.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            // now work with key and value...
            if (value.equals("pending")){
                requestsPending.add(key);
            }
        }
    }

    /**
     * Gives a list only a user's pending requests
     * @param email the email of the user
     * @return an ArrayList of strings representing the Users Requests
     */

    public ArrayList<String> userRequestsPending(String email){
        ArrayList<String> requestsPending = new ArrayList<String>();
        User person = emailToUser(email);
        if (this.hasRequests(person)){
            this.addRequests(((Attendee) person), requestsPending);
            }
        return requestsPending;
    }

    /**
     * Returns an email based on its index.
     *
     * @param totalPending an ArrayList of user emails with pending requests
     * @param i an int representing an index
     * @return
     */

    public String findEmail(ArrayList<String> totalPending, int i){
        String emailRequestTotal = totalPending.get(i);
        int indexComma = emailRequestTotal.indexOf(",");
        return emailRequestTotal.substring(0, indexComma);
    }

    /**
     * Gives a list of every user and an integer representing the total pending requests they have
     * @return an Arraylist with a user's email and an int representing the total pending requests they have
     */

    public ArrayList<String> totalPending(){
        ArrayList<String> requestsPending = new ArrayList<String>();
        for (User a: userList){
            if (this.hasRequests(a)){
                int pending = ((Attendee) a).getNumberOfPending();
                String pending1 = Integer.toString(pending);
                String userPending = a.getEmail() + ", " + pending1;
                requestsPending.add(userPending);
            }
        }
        return requestsPending;
    }

    /**
     * Returns true if the request was successfully approved or denied.
     *
     * @param request a String representing the request
     * @param status the status of the request
     * @param email the email of the requester
     * @return
     */

    public boolean updateRequests(String request, String status, String email){
        User user = this.emailToUser(email);
        if (status.equals("rejected")){
            return ((Attendee) user).requestDeny(request);
        }
        else if (status.equals("approved")){
            return ((Attendee) user).requestComplete(request);
        }
        else{
            return false;
        }
    }


}



