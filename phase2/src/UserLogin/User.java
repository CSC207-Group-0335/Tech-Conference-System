package UserLogin;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * An abstract class that represents a User.
 */

public abstract class User {
    private String name;
    private String password;
    private String email;
    private ArrayList<String> eventList;
    protected LinkedHashMap<String, String> requestMap;

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
        this.eventList = new ArrayList<>();
        this.requestMap = new LinkedHashMap<>();
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

    /**
     * Sets the attendees requests map.
     */
    public void setupRequests(LinkedHashMap<String, String> hash) {this.requestMap = hash;}

    public ArrayList<String> getEventList() {
        return eventList;
    }

    //NOTE TO REQUEST TEAM: I added this because i need it for writing and reading, since that invloves a general user
    //object. so this means we will need requestMaps in other users that maybe are just empty? idk exactly how you wanna
    //do that in your implementation of requests. I just need a method like this is here (it can be abstract if you want)
    // - Nathan (early morning Dec 9th)
    public LinkedHashMap<String, String> getRequestMap(){
        return requestMap;
    }

    public boolean addEvent(String eventId){
        if(eventList.contains(eventId)){
            return false;
        }
        else{eventList.add(eventId);
        return true;}
    }

    public boolean removeEvent(String eventId){
        if (eventList.contains(eventId)) {
            eventList.remove(eventId);
            return true;
        }
        else{return false;}
    }
    public boolean getVIPStatus(){
        return false;
    }
}

