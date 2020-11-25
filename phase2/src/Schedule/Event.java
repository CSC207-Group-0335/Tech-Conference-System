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
    boolean vipRestricted;

    /**
     * Creates an Event with the specified title and time.
     * @param title The title of the Event.
     * @param startTime The start time of the Event.
     */
    public Event(String title, LocalDateTime startTime, LocalDateTime endTime,
                 String roomName, ArrayList<String> speakers, String vipRestricted){
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventId = UUID.randomUUID().toString();
        this.roomName = roomName;
        this.usersSignedUp = new ArrayList<>();
        this.speakers = speakers;
        if (vipRestricted.equals("VIP")){
            this.vipRestricted = true;}
        else{
            this.vipRestricted = false;
        }
    }

    /**
     * Creates a talk with the specified title, time, and id.
     * @param title The title of the talk.
     * @param startTime The start time of the talk.
     * @param talkId The id of the talk.
     */
    public Event(String title, LocalDateTime startTime, LocalDateTime endTime, String talkId, String roomName,
                 ArrayList<String> speakers, String vipRestricted){
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventId = talkId;
        this.roomName = roomName;
        this.usersSignedUp = new ArrayList<>();
        this.speakers = speakers;
        if (vipRestricted.equals("VIP")){
        this.vipRestricted = true;}
        else{
            this.vipRestricted = false;
        }
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

    public String getEventId() {
        return eventId;
    }

    public String getRoomName() {return this.roomName;}

    public boolean addUser(String userId){
        for (String user : usersSignedUp){
            if (user.equals(userId)){
                return false;
            }
        }
        usersSignedUp.add(userId);
        return true;
    }
    public boolean addSpeaker(String speakerId){
        for (String speaker : speakers){
            if (speaker.equals(speakerId)){
                return false;
            }
        }
        speakers.add(speakerId);
        return true;
    }

    public ArrayList<String> getUsersSignedUp() {
        return usersSignedUp;
    }

    public ArrayList<String> getSpeakers() {
        return speakers;
    }

    public void addAttendee(String userEmail){
        this.usersSignedUp.add(userEmail);
    }
}
