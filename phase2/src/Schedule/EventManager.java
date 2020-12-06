package Schedule;

import UserLogin.Speaker;
import UserLogin.UserManager;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Stores all the events for the conference.
 */
public class EventManager{
    /**
     * A mapping of a event to its corresponding speaker, room, and time.
     */
    LinkedHashMap<Event, EventFeatures> eventMap;
    /**
     * A mapping of rooms to its corresponding RoomScheduleManager which checks for double booking.
     */
    public ArrayList<Room> roomList;
    /**
     * A mapping of speakers to its corresponding SpeakerScheduleManager which checks for double booking.
     */
    /**
    /**
     * Creates a event manager.
     */
    public ArrayList<Event> eventList;
    public ArrayList<String> eventIdsList;
    private RoomStorage roomStorage;
    private UserManager userManager;

    public EventManager(UserManager userManager, RoomStorage roomStorage){
        this.userManager = userManager;
        this.roomList = roomStorage.getRoomList();
        this.roomStorage = roomStorage;
        this.eventMap = new LinkedHashMap<Event, EventFeatures>();
        this.eventList = new ArrayList<>();
        this.eventIdsList = new ArrayList<>();
    }

    /**
     * adds an event to eventMap.
     * @param t The event you wish to add.
     * @param r The room corresponding to the event.
     * @param s The speaker/speakers corresponding to the event.
     * @param start The start time corresponding to the event.
     * @param end The end time corresponding to the event.
     */
    public void addEvent(Event t, Room r, ArrayList<Speaker> s, LocalDateTime start, LocalDateTime end){
        EventFeatures q = new EventFeatures(r, s, start, end);
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
        for (Speaker s : userManager.getSpeakerList()){
            if (s.getEmail().equals(speakerEmail)){
                return s;
            }
        }
        return null;
    }

    /**
     * Creates an event with the specified id, title, Speaker (based on email), Room (based on name), and start time.
     * Adds to eventMap, roomScheduleMap, speakerScheduleMap, and signUpMap.
     * @param eventId The id of the event.
     * @param eventTitle The title of the event.
     * @param speakerEmails The emails of the speaker.
     * @param roomName The name of the room.
     * @param start The start time of the event.
     * @param end The end time of the event.
     * @param vipRestricted The string signifying if the event is VIP restricted or not.
     * @return A boolean notifying if the event was successfully created and if the maps were appropriately
     * updated.
     */
    public boolean createEvent(String eventId, String eventTitle, ArrayList<String> speakerEmails, String roomName,
                               LocalDateTime start, LocalDateTime end, String vipRestricted){
        Room eventRoom = findRoom(roomName);
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
        if (eventRoom != null && start.getHour() >= 9 && end.getHour() <= 17  &&
                checkDoubleBooking(start, end, eventRoom.getEventList())){
                Event event = new Event(eventTitle, start, end, eventId, roomName, speakerEmails,vipRestricted);
                this.addEvent(event, eventRoom, speakers , start, end);
                for (Speaker s: speakers){
                    userManager.addEvent(s.getEmail(), event.getEventId());
                }
                roomStorage.addEvent(roomName, event.getEventId(), event.getStartTime(), event.getEndTime());
                return true;
            }
            else{
                return false;
            }
        }

    /**
     * Creates an event with the specified title, Speaker (based on email), Room (based on name), and start time.
     * Adds to eventMap, roomScheduleMap, speakerScheduleMap, and signUpMap.
     * @param eventTitle The title of the event.
     * @param speakerEmails The email of the speaker.
     * @param roomName The name of the room.
     * @param start The start time of the event.
     * @param end The end time of the event.
     * @param vipRestricted The string signifying if the event is VIP restricted or not.
     * @return A boolean notifying if the event was successfully created and if the maps were appropriately
     * updated.
     */
    public boolean createEvent(String eventTitle, ArrayList<String> speakerEmails, String roomName, LocalDateTime start,
                               LocalDateTime end, String vipRestricted){
        Room eventRoom = findRoom(roomName);
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
        if (eventRoom != null && start.getHour() >= 9 && end.getHour() <= 17  &&
                checkDoubleBooking(start, end, eventRoom.getEventList())){
            Event event = new Event(eventTitle, start, end, roomName, speakerEmails,vipRestricted);
            this.addEvent(event, eventRoom, speakers , start, end);
            for (Speaker s: speakers){
                userManager.addEvent(s.getEmail(), event.getEventId());
            }
            roomStorage.addEvent(roomName, event.getEventId(), event.getStartTime(), event.getEndTime());
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Remove an event from eventMap.
     * @param t The event you wish to remove.
     * @return A boolean notifying if the event was successfully removed.
     */
    public boolean removeEvent(Event t){
        boolean found = this.eventMap.containsKey(t) ;
        if (found){
            this.eventMap.remove(t);
            this.eventList.remove(t);
            this.eventIdsList.remove(t.getEventId());
            for (String user : t.getUsersSignedUp()){
                userManager.removeEvent(user, t.eventId);
            }
            for (String speaker : t.getSpeakers()){
                userManager.removeEvent(speaker, t.eventId);
            }
            this.roomStorage.removeEvent(t.roomName, t.eventId);
            return true;
        }
        return false;
    }

    /**
     * Takes in an email and event id, and returns a boolean signifying if this specific user is allowed to attend this
     * specific event.
     * @param userEmail The user email.
     * @param id The event id.
     * @return A boolean that tells you if the user corresponding to this email is allowed to attend the event.
     */
    public boolean checkIfUserAllowed(String userEmail, String id){
        boolean eventVIP = getEvent(id).getVIPStatus();
        boolean userVIP = userManager.emailToVIPStatus(userEmail);
        if (!eventVIP){
            return true;
        }
        else{
            // if it got to here then the event is VIP no point in checking it
            return userVIP;
        }
    }

    /**
     * Takes in an email and event id, and adds the user to the Attendee list if they are allowed and there is room for
     * them.
     * @param userEmail The user email.
     * @param id The event id.
     * @return Boolean signifying if the user was added to the event Attendee list.
     */
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
     * Get the eventMap
     * @return A LinkedHashMap representing the eventMap of EventManager.
     */
    public HashMap<Event, EventFeatures> getEventMap(){
        return this.eventMap;
    }

    /**
     * Takes in an event id and returns the corresponding Event object.
     * @param id The event id.
     * @return Returns the Event corresponding to the event id.
     */
    public Event getEvent(String id) {
        for (Event e : eventMap.keySet()) {
            if (e.getEventId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Takes in an event id and returns a a boolean if there exists an Event object with the same id.
     * @param id The event id.
     * @return Returns boolean signifying if the event exists.
     */
    public boolean exists(String id){
        if (eventMap.keySet().contains(id)){return true;}
        else{return false;}
    }

    /**
     * Get the speaker for a event.
     * @param id The event.
     * @return A Speaker representing the speaking of the event.
     */
    public ArrayList<Speaker> getEventSpeaker(String id){
        return this.eventMap.get(getEvent(id)).getSpeaker();
    }

    /**
     * Get the room associated with a event.
     * @param id The event.
     * @return A room representing the room the event is being held at.
     */
    public Room getEventRoom(String id){
        return (Room) this.eventMap.get(getEvent(id)).getRoom();
    }

    /**
     * Takes in event id and returns the corresponding event name.
     * @param id The event id.
     * @return Returns the string name.
     */
    public String eventIdToTitle(String id){
        Event e = getEvent(id);
        return e.getTitle();
    }

    /**
     * Takes in event id and returns an ArrayList of the speakers' emails.
     * @param id The event id.
     * @return Returns the ArrayList.
     */

    public ArrayList<String> eventIdToSpeakerEmails(String id){
        Event e = getEvent(id);
        return e.getSpeakers();
    }

    /**
     * Takes in event id and returns an ArrayList of the people attending's emails.
     * @param id The event id.
     * @return ArrayList of user emails.
     */
    public ArrayList<String> eventIdToUsersSignedUp(String id){
        Event e = getEvent(id);
        return e.getUsersSignedUp();
    }

    /**
     * Takes in event id and returns the name of the room where the event is taking place.
     * @param id The event id.
     * @return The string room name.
     */
    public String eventIdToRoomName(String id){
        Event e = getEvent(id);
        return e.getRoomName();
    }

    /**
     * Takes in event id and returns the VIP status of the corresponding event.
     * @param id The event id.
     * @return The string VIP status.
     */
    public String eventIdToVIPStatus(String id){
        Event e = getEvent(id);
        if(e.vipRestricted){
            return "VIP";}
        else{
            return "None";
        }
    }

    /**
     * Takes in an event ID and returns True if it has room for new users to attend.
     * @param id The event id.
     * @return The boolean signifier.
     */
    public boolean eventIdAtCapacity(String id){
        Event e = getEvent(id);
        int capacity = roomStorage.roomNameToCapacity(eventIdToRoomName(id));
        if (e.getUsersSignedUp().size() == capacity){
            return false;
        }
        return true;
    }

    /**
     * Takes in an event id and returns the start time of the corresponding Event.
     * @param id The event id.
     * @return The LocalDateTime start time.
     */
    public LocalDateTime eventIdToStartTime(String id){
        Event e = getEvent(id);
        return e.getStartTime();
    }
    /**
     * Takes in an event id and returns the end time of the corresponding Event.
     * @param id The event id.
     * @return The LocalDateTime end time.
     */
    public LocalDateTime eventIdToEndTime(String id){
        Event e = getEvent(id);
        return e.getEndTime();
    }

    /**
     * Returns an ArrayList of all event ids.
     * @return ArrayList of all String event ids
     */
    public ArrayList<String> getEventIdsList(){
        return this.eventIdsList;
    }

    /**
     * Takes in an event id and returns true if the event was cancelled.
     * @param id The event id.
     * @return The boolean signifier.
     */
    public boolean cancelEvent(String id){
        Event e = getEvent(id);
        return this.removeEvent(e);
    }

    /**
     * A string representation of a event with the event's title, room, speaker, and start time.
     * @param id id of the event.
     * @return A string representing a event and its room and speaker.
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
            }
        String line = "Event: " + getEvent(id).getTitle() + ", Room: " +
                getEventRoom(id).getRoomName()
                + speakers + "Starts at: " + getEvent(id).getStartTime().format(formatter) + ", Ends at: " +
                getEvent(id).getEndTime().format(formatter);
        if (getEvent(id).getVIPStatus()){
            line += ", VIP restricted event";
        }
        return line;}
        else { String line = "Event: " + getEvent(id).getTitle() + ", Room: " +
                getEventRoom(id).getRoomName()
                + ", Starts at: " + getEvent(id).getStartTime().format(formatter) + ", Ends at: " +
                getEvent(id).getEndTime().format(formatter);
            if (getEvent(id).getVIPStatus()){
                line += ", VIP restricted event";
            }
            return line;
        }
    }

    /**
     * A string representation of eventMap, which represents all the events being given at the conference.
     * @return A string representing the eventMap of EventManager.
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

    /**
     * Checks if two times are overlapping, returns true if they are.
     * @param startA The first start time.
     * @param endA  The first end time.
     * @param startB The second start time.
     * @param endB  The second end time
     * @return The boolean signifier.
     */
    public boolean checkOverlappingTimes(LocalDateTime startA, LocalDateTime endA,
                                         LocalDateTime startB, LocalDateTime endB){
        return (endB == null || startA == null || !startA.isAfter(endB))
                && (endA == null || startB == null || !endA.isBefore(startB));
    }

    /**
     * Checks if any events in the list of event id's are taking place at the same time. Returns true if there is.
     * @param start The start time.
     * @param end The start time.
     * @param eventIds The ArrayList of event ids.
     * @return The boolean signifier.
     */
    public boolean checkDoubleBooking(LocalDateTime start, LocalDateTime end, ArrayList<String> eventIds){
        for (String id: eventIds){
            if (checkOverlappingTimes(getEvent(id).getStartTime(), getEvent(id).getEndTime(),
                    start, end)){
                return false;
            }}
        return true;
    }
    /**
     * Checks if any events in the list of event id's are taking place at the same time. Returns true if there is.
     * @param start The start time.
     * @param end The start time.
     * @param eventIds The ArrayList of event ids.
     * @return The boolean signifier.
     */
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

    /**
     * Returns an arrayList of strings, each of which is a string corresponding to a day which the conference takes
     * place, in the form MONTH DAY.
     * @return An ArrayList of strings.
     */
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

    /**
     * Returns a list of integers corresponding to all the possible days that events can take place on during the
     * conference.
     * @return An ArrayList of integers.
     */
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

    /** Takes in a n integer and returns a list of event id's of which take place on the day corresponding to that
     * integer.
     *
     * @param day The integer representing the day of the month
     * @return An array list of integers representing days of the conference
     */
    public ArrayList<String> intDayToEventIDs(int day){
        ArrayList<String> events = new ArrayList<>();
        for(Event event: this.eventList){
            if(event.getStartTime().getDayOfMonth() == day){
                events.add(event.getEventId());
            }
        }
        return events;
    }
}

