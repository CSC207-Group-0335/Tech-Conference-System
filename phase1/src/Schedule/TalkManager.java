package Schedule;

import UserLogin.Speaker;
import UserLogin.TechConferenceSystem;


import java.time.LocalDateTime;
import java.util.*;

/**
 * Stores all the talks for the conference.
 */
public class TalkManager implements Observer {
    /**
     * A mapping of a talk to its corresponding speaker, room, and time.
     */
    LinkedHashMap<Talk, ArrayList<Object>> talkMap;
    /**
     * A mapping of rooms to its corresponding RoomScheduleManager which checks for double booking.
     */
    public HashMap<Room, RoomScheduleManager> roomScheduleMap;
    /**
     * A mapping of speakers to its corresponding SpeakerScheduleManager which checks for double booking.
     */
    public HashMap<Speaker, SpeakerScheduleManager> speakerScheduleMap;
    /**
     * A mapping of talks to its corresponding SignUpAttendeesManager which checks for enrollment and cancellation.
     */
    public HashMap<Talk, SignUpAttendeesManager> signUpMap;

    /**
     * Creates a talk manager.
     */
    public TalkManager(){
        this.roomScheduleMap = new HashMap<Room, RoomScheduleManager>();
        this.speakerScheduleMap = new HashMap<Speaker, SpeakerScheduleManager>();
        this.talkMap = new LinkedHashMap<Talk, ArrayList<Object>>();
        this.signUpMap = new HashMap<Talk, SignUpAttendeesManager>();
    }

    /**
     * adds a talk to talkMap.
     * @param t The talk you wish to add.
     * @param r The room corresponding to the talk.
     * @param s The speaker corresponding to the talk.
     * @param d The start time corresponding to the talk.
     */
    public void addTalk(Talk t, Room r, Speaker s, LocalDateTime d){
        ArrayList<Object> tup = new ArrayList<Object>();
        tup.add(s);
        tup.add(r);
        tup.add(d);
        talkMap.put(t, tup);
    }

    /**
     * gets a room from roomScheduleMap with the specified name.
     * @param roomName The name of the room you want to retrieve.
     * @return A room with the specified name or null if there is no room with the specified name.
     */
    public Room findRoom(String roomName){
        for (Room r : this.roomScheduleMap.keySet()){
            if (r.getRoomName().equals(roomName)){
                return r;
            }
        }
        return null;
    }

    /**
     * gets a room from speakerScheduleMap with the specified email.
     * @param speakerEmail The email of the speaker you want to retrieve.
     * @return A speaker with the specified email or null if there is no speaker with that email.
     */
    public Speaker findSpeaker(String speakerEmail){
        for (Speaker s : speakerScheduleMap.keySet()){
            if (s.getEmail().equals(speakerEmail)){
                return s;
            }
        }
        return null;
    }

    /**
     * Creates a talk with the specified id, title, Speaker (based on email), Room (based on name), and start time.
     * Adds to talkMap, roomScheduleMap, speakerScheduleMap, and signUpMap.
     * @param talkId The id of the talk.
     * @param talkTitle The title of the talk.
     * @param speakerEmail The email of the speaker.
     * @param roomName The name of the room.
     * @param d The start time of the talk.
     * @return A boolean notifying if the talk was successfully created and if the maps were appropriately
     * updated.
     */
    public boolean createTalk(String talkId, String talkTitle, String speakerEmail, String roomName, LocalDateTime d){
        Room talkRoom = findRoom(roomName);
        Speaker talkSpeaker = findSpeaker(speakerEmail);
        if (talkRoom != null && talkSpeaker != null){
            if ( d.getHour() >= 9 && d.getHour() < 17 && this.speakerScheduleMap.get(talkSpeaker).checkDoubleBooking(d) &&
                this.roomScheduleMap.get(talkRoom).checkDoubleBooking(d)){
                Talk t = new Talk(talkTitle, d, talkId);
                this.addTalk(t, talkRoom, talkSpeaker, d);
                this.speakerScheduleMap.get(talkSpeaker).addTalk(t);
                this.roomScheduleMap.get(talkRoom).addTalk(t);
                SignUpAttendeesManager signUpAttendeesManager = new SignUpAttendeesManager(t, talkRoom.capacity);
                this.signUpMap.put(t, signUpAttendeesManager);
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    /**
     * Creates a talk with the specified title, Speaker (based on email), Room (based on name), and start time.
     * Adds to talkMap, roomScheduleMap, speakerScheduleMap, and signUpMap.
     * @param talkTitle The title of the talk.
     * @param speakerEmail The email of the speaker.
     * @param roomName The name of the room.
     * @param d The start time of the talk.
     * @return A boolean notifying if the talk was successfully created and if the maps were appropriately
     * updated.
     */
    public boolean createTalk(String talkTitle, String speakerEmail, String roomName, LocalDateTime d){
        Room talkRoom = findRoom(roomName);
        Speaker talkSpeaker = findSpeaker(speakerEmail);
        if (talkRoom != null && talkSpeaker != null){
            if ( d.getHour() >= 9 && d.getHour() < 17 &&
                    this.speakerScheduleMap.get(talkSpeaker).checkDoubleBooking(d) &&
                    this.roomScheduleMap.get(talkRoom).checkDoubleBooking(d)){
                Talk t = new Talk(talkTitle, d);
                this.addTalk(t, talkRoom, talkSpeaker, d);
                this.speakerScheduleMap.get(talkSpeaker).addTalk(t);
                this.roomScheduleMap.get(talkRoom).addTalk(t);
                SignUpAttendeesManager signUpAttendeesManager = new SignUpAttendeesManager(t, talkRoom.capacity);
                this.signUpMap.put(t, signUpAttendeesManager);
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    /**
     * Remove a talk from talkMap.
     * @param t The talk you wish to remove.
     * @return A boolean notifying if the talk was successfully removed.
     */
    public boolean removeTalk(Talk t){
        boolean found = this.talkMap.containsKey(t) ;
        if (found){
            this.talkMap.remove(t);
            return true;
        }
        return false;
    }

    /**
     * Get the talkMap
     * @return A LinkedHashMap representing the talkMap of TalkManager.
     */
    public HashMap<Talk, ArrayList<Object>> getTalkMap(){
        return this.talkMap;
    }

    /**
     * Get the speaker for a talk.
     * @param t The talk.
     * @return A Speaker representing the speaking of the talk.
     */
    public Speaker getTalkSpeaker(Talk t){
        return (Speaker) this.talkMap.get(t).get(0);
    }

    /**
     * Get the room associated with a talk.
     * @param t The talk.
     * @return A room representing the room the talk is being held at.
     */
    public Room getTalkRoom(Talk t){
        return (Room) this.talkMap.get(t).get(1);
    }

    /**
     * Get the start time of a talk.
     * @param t The talk.
     * @return A LocalDateTime representing the start time of the talk.
     */
    public LocalDateTime getTalkTime(Talk t){
        return (LocalDateTime) this.talkMap.get(t).get(2);
    }

    /**
     * Get the signUpMap
     * @return A HashMap representing the signUpMap of the TalkManager.
     */
    public HashMap<Talk, SignUpAttendeesManager> getSignUpMap() {
        return signUpMap;
    }

    /**
     * A string representation of a talk with the talk's title, room, speaker, and start time.
     * @param t The talk.
     * @return A string representing a talk and its room and speaker.
     */
    public String toStringTalk(Talk t){
        String line = "Talk: " + t.getTitle() + ", Room: " + this.getTalkRoom(t).getRoomName() + ", Speaker: "
                + this.getTalkSpeaker(t).getName() + ", Time: " + dateToString(this.getTalkTime(t));
        return line;
    }

    /**
     * A string representation of talkMap, which represents all the talks being given at the conference.
     * @return A string representing the talkMap of TalkManager.
     */
    public String talkMapStringRepresentation(){
        ArrayList<String> lines = new ArrayList<String>();
        for(Talk t: talkMap.keySet()){
            String line = "Talk: " + t.getTitle() + ", Room: " + this.getTalkRoom(t).getRoomName() + ", Speaker: "
                    + this.getTalkSpeaker(t).getName() + ", Time: " + dateToString(this.getTalkTime(t));
            lines.add(line);
        }
        String totalString = "";
        Integer i = 1;
        for(String line: lines){
            totalString += Integer.toString(i) + ") " + line + System.lineSeparator();
            i++;
        }
        return totalString;
    }

    /**
     * A string representation of the start time
     * @param localDateTime The start time.
     * @return A string representing the start time.
     */
    public String dateToString(LocalDateTime localDateTime){
        String str = localDateTime.toString();
        String[] split = str.split("T");
        String newString = split[0] + " " +split[1];
        return newString;
    }

    /**
     * Updating talkManager's roomScheduleMap and speakerScheduleMap.
     * @param o An Observable.
     * @param arg An Object.
     */
    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof RoomSystem){
            if (arg instanceof HashMap) {
                this.roomScheduleMap = (HashMap<Room, RoomScheduleManager>) arg;
            }
        }
        else if(o instanceof TechConferenceSystem){
            if (arg instanceof HashMap) {
                    this.speakerScheduleMap = (HashMap<Speaker, SpeakerScheduleManager>) arg;
                }
            }
        }

    }
