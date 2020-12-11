package Schedule;

import UserLogin.Speaker;
import UserLogin.User;

import java.time.Duration;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents an Event.
 */
public class Event {
    String title;
    LocalDateTime startTime;
    LocalDateTime endTime;
    /**
     * A unique identifier for each Event.
     */
    String eventId;
    String roomName;
    ArrayList<String> usersSignedUp;
    ArrayList<String> speakers;
    int capacity;
    boolean vipRestricted;

    /**
     * Creates an Event with the specified title and time.
     * @param title The title of the Event.
     * @param startTime The start time of the Event.
     */
    public Event(String title, LocalDateTime startTime, LocalDateTime endTime,
                 String roomName, ArrayList<String> speakers, int capacity, boolean vipRestricted){
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventId = UUID.randomUUID().toString();
        this.roomName = roomName;
        this.usersSignedUp = new ArrayList<>();
        this.speakers = speakers;
        this.capacity = capacity;
        this.vipRestricted = vipRestricted; //changed this to a boolean value

    }

    /**
     * Creates a talk with the specified title, time, and id.
     * @param title The title of the talk.
     * @param startTime The start time of the talk.
     * @param talkId The id of the talk.
     */
    public Event(String title, LocalDateTime startTime, LocalDateTime endTime, String talkId, String roomName,
                 ArrayList<String> speakers, int capacity, boolean vipRestricted){
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventId = talkId;
        this.roomName = roomName;
        this.usersSignedUp = new ArrayList<>();
        this.speakers = speakers;
        this.capacity = capacity;
        this.vipRestricted = vipRestricted;
    }

    /**
     * Returns true if the user was successfully added.
     * @param userEmail the email of the user
     * @return a booleans representing whether or not the user was added
     */

    public boolean addUser(String userEmail){
        for (String user : usersSignedUp){
            if (user.equals(userEmail)){
                return false;
            }
        }
        usersSignedUp.add(userEmail);
        return true;
    }

    /**
     * Returns true if the user was successfully removed.
     * @param userEmail the email of the user
     * @return a booleans representing whether or not the user was removed
     */

    public boolean removeUser(String userEmail){
        if (usersSignedUp.contains(userEmail)) {
            usersSignedUp.remove(userEmail);
            return true;
        }
        else{return false;}
    }

    /**
     * Retrieves the tile of the talk.
     * @return A string representing the title of the talk.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the start time of the talk.
     * @return A LocalDateTime representing the start time of the talk.
     */

    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Retrieves the id of the talk.
     * @return A UUID representing the id of the talk.
     */

    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Returns the event ID.
     * @return a UUID representing the event ID
     */

    public String getEventId() {
        return eventId;
    }

    /**
     * Returns the name of the room.
     * @return a String representing the room name
     */

    public String getRoomName() {return this.roomName;}

    /**
     * Returns the capacity of this event.
     * @return an int representing the capacity of the event
     */

    public int getCapacity() {return this.capacity;}

    /**
     * Returns a list of emails of users signed up for this event
     * @return an ArrayList containing user emails
     */

    public ArrayList<String> getUsersSignedUp() {
        return usersSignedUp;
    }

    /**
     * Returns a list of emails of speakers signed up for this event
     * @return an ArrayList containing speaker emails
     */

    public ArrayList<String> getSpeakers() {
        return speakers;
    }

    /**
     * Returns true if this event is VIP restricted
     * @return a boolean representing whether this event is exclusive to VIPs
     */

    public boolean getVIPStatus(){
        return this.vipRestricted;
    }

    /**
     * Sets the capacity of this event.
     * @param cap an int representing the new capacity of this event
     */

    public void setCapacity(int cap) { this.capacity = cap;}
}
