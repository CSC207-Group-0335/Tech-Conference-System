package Schedule;

import UserLogin.Speaker;
import UserLogin.UserStorage;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Stores all the talks for the conference.
 */
public class EventManager{
    /**
     * A mapping of a talk to its corresponding speaker, room, and time.
     */
    LinkedHashMap<Event, Quartet> eventMap;
    /**
     * A mapping of rooms to its corresponding RoomScheduleManager which checks for double booking.
     */
    public ArrayList<Room> roomList;
    /**
     * A mapping of speakers to its corresponding SpeakerScheduleManager which checks for double booking.
     */
    /**
    /**
     * Creates a talk manager.
     */
    public ArrayList<Event> eventList;
    public ArrayList<String> eventIdsList;
    private RoomStorage roomStorage;
    private UserStorage userStorage;

    public EventManager(UserStorage userStorage, RoomStorage roomStorage){
        this.userStorage = userStorage;
        this.roomList = roomStorage.getRoomList();
        this.roomStorage = roomStorage;
        this.eventMap = new LinkedHashMap<Event, Quartet>();
        this.eventList = new ArrayList<>();
        this.eventIdsList = new ArrayList<>();
    }

    /**
     * adds a talk to talkMap.
     * @param t The talk you wish to add.
     * @param r The room corresponding to the talk.
     * @param s The speaker/speakers corresponding to the talk.
     * @param start The start time corresponding to the talk.
     * @param end The end time corresponding to the talk.
     */
    public void addEvent(Event t, Room r, ArrayList<Speaker> s, LocalDateTime start, LocalDateTime end){
        Quartet q = new Quartet(r, s, start, end);
        eventMap.put(t, q);
        eventList.add(t);
        eventIdsList.add(t.getEventId());
    }

    /**
     * gets a room from roomScheduleMap with the specified name.
     * @param roomName The name of the room you want to retrieve.
     * @return A room with the specified name or null if there is no room with the specified name.
     */
    public Room findRoom(String roomName){
        for (Room r : roomList){
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
        for (Speaker s : userStorage.getSpeakerList()){
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
     * @param speakerEmails The emails of the speaker.
     * @param roomName The name of the room.
     * @param start The start time of the talk.
     * @return A boolean notifying if the talk was successfully created and if the maps were appropriately
     * updated.
     */
    public boolean createEvent(String talkId, String talkTitle, ArrayList<String> speakerEmails, String roomName,
                               LocalDateTime start, LocalDateTime end, String vipRestricted){
        Room talkRoom = findRoom(roomName);
        ArrayList<Speaker> speakers = new ArrayList<Speaker>();
        for(String speaker: speakerEmails){
            Speaker s = findSpeaker(speaker);
            speakers.add(s);
        }
        for (Speaker s: speakers){
            if (!(checkDoubleBooking(start, end, s.getEventList()))){
                return false;
            };
        }
        if (talkRoom != null && start.getHour() >= 9 && end.getHour() <= 17  &&
                checkDoubleBooking(start, end, talkRoom.getTalkList())){
                Event event = new Event(talkTitle, start, end, talkId, roomName, speakerEmails,vipRestricted);
                this.addEvent(event, talkRoom, speakers , start, end);
                for (Speaker s: speakers){
                    userStorage.addEvent(s.getEmail(), event.getEventId());
                }
                roomStorage.addEvent(roomName, event.getEventId(), event.getStartTime(), event.getEndTime());
                return true;
            }
            else{
                return false;
            }
        }

    /**
     * Creates a talk with the specified title, Speaker (based on email), Room (based on name), and start time.
     * Adds to talkMap, roomScheduleMap, speakerScheduleMap, and signUpMap.
     * @param talkTitle The title of the talk.
     * @param speakerEmails The email of the speaker.
     * @param roomName The name of the room.
     * @param start The start time of the talk.
     * @return A boolean notifying if the talk was successfully created and if the maps were appropriately
     * updated.
     */
    public boolean createEvent(String talkTitle, ArrayList<String> speakerEmails, String roomName, LocalDateTime start,
                               LocalDateTime end, String vipRestricted){
        Room talkRoom = findRoom(roomName);
        ArrayList<Speaker> speakers = new ArrayList<Speaker>();
        for(String speaker: speakerEmails){
            Speaker s = findSpeaker(speaker);
            speakers.add(s);
        }
        for (Speaker s: speakers){
            if (!(checkDoubleBooking(start, end, s.getEventList()))){
                return false;
            };
        }
        if (talkRoom != null && start.getHour() >= 9 && end.getHour() <= 17  &&
                checkDoubleBooking(start, end, talkRoom.getTalkList())){
            Event event = new Event(talkTitle, start, end, roomName, speakerEmails,vipRestricted);
            this.addEvent(event, talkRoom, speakers , start, end);
            for (Speaker s: speakers){
                userStorage.addEvent(s.getEmail(), event.getEventId());
            }
            roomStorage.addEvent(roomName, event.getEventId(), event.getStartTime(), event.getEndTime());
            return true;
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
            this.eventList.remove(t);
            this.eventIdsList.remove(t.getEventId());
            return true;
        }
        return false;
    }

    public boolean checkIfUserAllowed(String userEmail, String id){
        boolean eventVIP = getEvent(id).getVIPStatus();
        boolean userVIP = userStorage.emailToVIPStatus(userEmail);
        if (!eventVIP){
            return true;
        }
        else{
            // if it got to here then the event is VIP no point in checking it
            return userVIP;
        }
    }

    public boolean addAttendee(String userEmail, String id){
        if (getEvent(id).getUsersSignedUp().size() + 1 > getEventRoom(id).getCapacity()
        || getEvent(id).getUsersSignedUp().contains(userEmail) ||
                !(checkIfUserAllowed(userEmail, id))){
            return false;
        }
        else{
            getEvent(id).addUser(userEmail);
            return true;
        }
    }

    public boolean removeAttendee(String userEmail, String id){
        return getEvent(id).removeUser(userEmail);
        }


    /**
     * Get the talkMap
     * @return A LinkedHashMap representing the talkMap of TalkManager.
     */
    public HashMap<Event, Quartet> getEventMap(){
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

    public boolean exists(String id){
        if (eventMap.keySet().contains(id)){return true;}
        else{return false;}
    }

    /**
     * Get the speaker for a talk.
     * @param id The talk.
     * @return A Speaker representing the speaking of the talk.
     */
    public ArrayList<Speaker> getEventSpeaker(String id){
        return this.eventMap.get(getEvent(id)).getSpeaker();
    }

    /**
     * Get the room associated with a talk.
     * @param id The talk.
     * @return A room representing the room the talk is being held at.
     */
    public Room getEventRoom(String id){
        return (Room) this.eventMap.get(getEvent(id)).getRoom();
    }

    public String eventIdToTitle(String id){
        Event e = getEvent(id);
        return e.getTitle();
    }

    public ArrayList<String> eventIdToSpeakerEmails(String id){
        Event e = getEvent(id);
        return e.getSpeakers();
    }
    public ArrayList<String> eventIdToUsersSignedUp(String id){
        Event e = getEvent(id);
        return e.getUsersSignedUp();
    }
    public String eventIdToRoomName(String id){
        Event e = getEvent(id);
        return e.getRoomName();
    }

    public String eventIdToVIPStatus(String id){
        Event e = getEvent(id);
        if(e.vipRestricted){
            return "VIP";}
        else{
            return "None";
        }
    }

    public boolean eventIdAtCapacity(String id){
        Event e = getEvent(id);
        int capacity = roomStorage.roomNameToCapacity(eventIdToRoomName(id));
        if (e.getUsersSignedUp().size() == capacity){
            return false;
        }
        return true;
    }
    public LocalDateTime eventIdToStartTime(String id){
        Event e = getEvent(id);
        return e.getStartTime();
    }
    public LocalDateTime eventIdToEndTime(String id){
        Event e = getEvent(id);
        return e.getEndTime();
    }

    public ArrayList<String> getEventIdsList(){
        return this.eventIdsList;
    }

    public boolean cancelEvent(String id){
        Event e = getEvent(id);
        return this.removeEvent(e);
    }

    /**
     * A string representation of a talk with the talk's title, room, speaker, and start time.
     * @param id id of the talk.
     * @return A string representing a talk and its room and speaker.
     */
    public String toStringEvent(String id){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String speakers = "";
        if (getEventSpeaker(id).size() != 0){
            if (getEventSpeaker(id).size() == 1){
                speakers = ", Speaker: ";
            }
            else{speakers = ", Speakers: ";}
            for (Speaker s: getEventSpeaker(id)){
                speakers += " " + s.getName() + ", ";
            }}
        String line = "Event: " + getEvent(id).getTitle() + ", Room: " +
                getEventRoom(id).getRoomName()
                + speakers + "Starts at: " + getEvent(id).getStartTime().format(formatter) + ", Ends at: " +
                getEvent(id).getEndTime().format(formatter);
        if (getEvent(id).getVIPStatus()){
            line += ", VIP restricted event";
        }
        return line;
    }

    /**
     * A string representation of talkMap, which represents all the talks being given at the conference.
     * @return A string representing the talkMap of TalkManager.
     */
    public String EventMapStringRepresentation(){
        ArrayList<String> lines = new ArrayList<String>();
        for(Event t: eventMap.keySet()){
            String line = toStringEvent(t.getEventId());
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

    public boolean checkDoubleBooking(String eventId, ArrayList<String> eventIds){
        LocalDateTime start = eventIdToStartTime(eventId);
        LocalDateTime end = eventIdToEndTime(eventId);
        for (String id: eventIds){
            if (checkOverlappingTimes(getEvent(id).getStartTime(), getEvent(id).getEndTime(),
                    start, end)){
                return false;
            }}
        return true;

        }

    public ArrayList<String> getAllEventDays(){
        ArrayList<Integer> sorted = this.getAllEventDayMonth();
        ArrayList<String> data = new ArrayList<>();
        String month = this.eventList.get(0).getStartTime().getMonth().toString();

        for (int date: sorted){
            StringBuilder string = new StringBuilder();

            string.append(month);
            string.append(" ");
            string.append(date);
            data.add(string.toString());
        }
        return data;
    }
    public ArrayList<Integer> getAllEventDayMonth(){
        ArrayList<Integer> sorted = new ArrayList<>();


        for (Event event: this.eventList) {
            if(!sorted.contains(event.getStartTime().getDayOfMonth())){
                sorted.add(event.getStartTime().getDayOfMonth());
            }


        }
        Collections.sort(sorted);
        return sorted;
    }
    public ArrayList<String> intDaytoEventIDs(int day){
        ArrayList<String> events = new ArrayList<>();
        for(Event event: this.eventList){
            if(event.getStartTime().getDayOfMonth() == day){
                events.add(event.getEventId());
            }
        }
        return events;
    }
}

