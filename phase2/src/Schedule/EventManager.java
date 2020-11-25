package Schedule;

import UserLogin.Speaker;
import UserLogin.TechConferenceSystem;


import java.time.LocalDateTime;
import java.util.*;

/**
 * Stores all the talks for the conference.
 */
public class EventManager implements Observer {
    /**
     * A mapping of a talk to its corresponding speaker, room, and time.
     */
    LinkedHashMap<Event, ArrayList<Object>> eventMap;
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
    public HashMap<Event, SignUpAttendeesManager> signUpMap;

    /**
     * Creates a talk manager.
     */
    public EventManager(){
        this.roomScheduleMap = new HashMap<Room, RoomScheduleManager>();
        this.speakerScheduleMap = new HashMap<Speaker, SpeakerScheduleManager>();
        this.eventMap = new LinkedHashMap<Event, ArrayList<Object>>();
        this.signUpMap = new HashMap<Event, SignUpAttendeesManager>();
    }

    /**
     * adds a talk to talkMap.
     * @param t The talk you wish to add.
     * @param r The room corresponding to the talk.
     * @param s The speaker corresponding to the talk.
     * @param d The start time corresponding to the talk.
     */
    public void addEvent(Event t, Room r, Speaker s, LocalDateTime d){
        ArrayList<Object> tup = new ArrayList<Object>();
        tup.add(s);
        tup.add(r);
        tup.add(d);
        eventMap.put(t, tup);
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
    public boolean createEvent(String talkId, String talkTitle, ArrayList<String> speakerEmails, String roomName,
                               LocalDateTime start, LocalDateTime end){
        Room talkRoom = findRoom(roomName);
        ArrayList<Speaker> speakers = new ArrayList<Speaker>();
        for(String speaker: speakerEmails){
            Speaker s = findSpeaker(speaker);
            speakers.add(s);
        }
        boolean allAvailable = true;
        for (Speaker s: speakers){
            if (!(checkDoubleBooking(start, end, s.getschedule()))){
                return false;
            };
        }
        if (talkRoom != null){
            if ( start.getHour() >= 9 && end.getHour() <= 17 && this.speakerScheduleMap.get(talkSpeaker).checkDoubleBooking(start) &&
                this.roomScheduleMap.get(talkRoom).checkDoubleBooking(start)){
                Event t = new Event(talkTitle, d, talkId, roomName, speakerEmail);
                this.addEvent(t, talkRoom, talkSpeaker, d);
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
    public boolean createEvent(String talkTitle, String speakerEmail, String roomName, LocalDateTime d){
        Room talkRoom = findRoom(roomName);
        Speaker talkSpeaker = findSpeaker(speakerEmail);
        if (talkRoom != null && talkSpeaker != null){
            if ( d.getHour() >= 9 && d.getHour() < 17 &&
                    this.speakerScheduleMap.get(talkSpeaker).checkDoubleBooking(d) &&
                    this.roomScheduleMap.get(talkRoom).checkDoubleBooking(d)){
                Event t = new Event(talkTitle, d);
                this.addEvent(t, talkRoom, talkSpeaker, d);
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
    public boolean removeEvent(Event t){
        boolean found = this.eventMap.containsKey(t) ;
        if (found){
            this.eventMap.remove(t);
            return true;
        }
        return false;
    }

    /**
     * Get the talkMap
     * @return A LinkedHashMap representing the talkMap of TalkManager.
     */
    public HashMap<Event, ArrayList<Object>> getEventMap(){
        return this.eventMap;
    }

    public Event getEvent(String id) {
        for (Event e : eventMap.keySet()) {
            if (e.getEventId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Get the speaker for a talk.
     * @param id The talk.
     * @return A Speaker representing the speaking of the talk.
     */
    public Speaker getEventSpeaker(String id){
        return (Speaker) this.eventMap.get(getEvent(id)).get(0);
    }

    /**
     * Get the room associated with a talk.
     * @param id The talk.
     * @return A room representing the room the talk is being held at.
     */
    public Room getEventRoom(String id){
        return (Room) this.eventMap.get(getEvent(id)).get(1);
    }

    /**
     * Get the start time of a talk.
     * @param id The talk.
     * @return A LocalDateTime representing the start time of the talk.
     */
    public LocalDateTime getEventTime(String id){
        return (LocalDateTime) this.eventMap.get(getEvent(id)).get(2);
    }

    /**
     * Get the signUpMap
     * @return A HashMap representing the signUpMap of the TalkManager.
     */
    public HashMap<Event, SignUpAttendeesManager> getSignUpMap() {
        return signUpMap;
    }

    /**
     * A string representation of a talk with the talk's title, room, speaker, and start time.
     * @param t The talk.
     * @return A string representing a talk and its room and speaker.
     */
    public String toStringEvent(String id){
        String line = "Talk: " + getEvent(id).getTitle() + ", Room: " + this.getEventRoom(id).getRoomName() + ", Speaker: "
                + this.getEventSpeaker(id).getName() + ", Time: " + dateToString(this.getEventTime(id));
        return line;
    }

    /**
     * A string representation of talkMap, which represents all the talks being given at the conference.
     * @return A string representing the talkMap of TalkManager.
     */
    public String EventMapStringRepresentation(){
        ArrayList<String> lines = new ArrayList<String>();
        for(Event t: eventMap.keySet()){
            String line = "Talk: " + t.getTitle() + ", Room: " + this.getEventRoom(t.getEventId()).getRoomName() + ", Speaker: "
                    + this.getEventSpeaker(t.getEventId()).getName() + ", Time: " +
                    dateToString(this.getEventTime(t.getEventId()));
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

    public boolean checkOverlappingTimes(LocalDateTime startA, LocalDateTime endA,
                                         LocalDateTime startB, LocalDateTime endB){
        return (endB == null || startA == null || !startA.isAfter(endB))
                && (endA == null || startB == null || !endA.isBefore(startB));
    }


    public boolean checkDoubleBooking(LocalDateTime start, LocalDateTime end, ArrayList<String> eventIds){
        for (String id: eventIds){
            if (checkOverlappingTimes(getEvent(id).getStartTime(), getEvent(id).getEndTime(),
                    start, end)){
                return false;
            }}
        return true;
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
