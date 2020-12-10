package Files;

import MessagingPresenters.ConversationManager;
import MessagingPresenters.ConversationStorage;
import MessagingPresenters.Message;
import Schedule.Event;
import Schedule.EventManager;
import Schedule.RoomStorage;
import UserLogin.User;
import UserLogin.UserManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JSONWriter {

    public void writeToEvents(String json, EventManager events) {
        JSONArray eventArray = new JSONArray();

        for (String eventid:
             events.getEventIdsList()) {
            JSONObject eventObject = new JSONObject();
            eventObject.put("title", events.eventIdToTitle(eventid));
            LocalDateTime startTime = events.eventIdToStartTime(eventid);
            LocalDateTime endTime = events.eventIdToEndTime(eventid);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formatted = startTime.format(formatter);
            String formatted2 = endTime.format(formatter);
            eventObject.put("startTime", formatted);
            eventObject.put("endTime", formatted2);
            eventObject.put("eventId", eventid);
            eventObject.put("roomName", events.eventIdToRoomName(eventid));
            eventObject.put("usersSignedUp", events.eventIdToUsersSignedUp(eventid));
            eventObject.put("speakers", events.eventIdToSpeakerEmails(eventid));
            eventObject.put("vipRestricted", events.eventIdToVIPboolean(eventid));
            eventObject.put("capacity", events.eventIdToCapacity(eventid));
            eventArray.add(eventObject);

        }
        try {
            Files.write(Paths.get(json), eventArray.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void writeToUsers(String json, UserManager users) {
        JSONArray userArray = new JSONArray();

        for (String email:
                users.getUserEmailList()) {
            JSONObject userObject = new JSONObject();
            userObject.put("type", users.emailToType(email));
            userObject.put("vip", users.emailToVIPStatus(email));
            userObject.put("name", users.emailToName(email));
            userObject.put("password", users.emailToPassword(email));
            userObject.put("email", email);
            userObject.put("ListOfTalkIDs", users.emailToEventList(email));
            userObject.put("requests", users.emailToRequests(email));

            userArray.add(userObject);

        }

        //I found an alternative way to write to files here, but both ways seem to work the same - Nathan
//        try (FileWriter file = new FileWriter(json)) {
//            file.write(userArray.toJSONString());
//            file.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            Files.write(Paths.get(json), userArray.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void writeToConversations(String json, ConversationStorage convos){
        JSONArray convoArray = new JSONArray();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (ConversationManager convo:
                convos.getConversationManagers()) {
            JSONArray messagesArray = new JSONArray();
            JSONObject convoObject = new JSONObject();
            convoObject.put("participants", convo.getParticipants());
            for (String messageid: convo.getMessageIDs()){
                JSONObject messageobj = new JSONObject();
                messageobj.put("recipient", convo.getRecipientOfMessageWithID(messageid));
                messageobj.put("sender", convo.getSenderOfMessageWithID(messageid));
                LocalDateTime time = convo.getTimestampOfMessageWithID(messageid);
                String formatted = time.format(formatter);
                messageobj.put("time", formatted);
                messageobj.put("content", convo.getContentOfMessageWithID(messageid));
                messageobj.put("recipientstatus", convo.getRecipientStatusesOfMessageWithID(messageid));
                messageobj.put("senderstatus", convo.getSenderStatusesOfMessageWithID(messageid));
                messagesArray.add(messageobj);
            }
            convoObject.put("chatLog", messagesArray);
            convoArray.add(convoObject);
        }

        try {
            Files.write(Paths.get(json), convoArray.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void writeToRooms(String json, RoomStorage rooms){
        JSONArray roomsArray = new JSONArray();
        for (String name: rooms.getRoomNameList()){
            JSONObject newobj = new JSONObject();
            newobj.put("roomname", name);
            roomsArray.add(newobj);
        }
        try {
            Files.write(Paths.get(json), roomsArray.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToRegistration(String json, UserManager users){
        JSONArray userArray = new JSONArray();
        for(String email: users.getUserEmailList()){
            JSONObject newobj = new JSONObject();
            newobj.put(email, users.emailToEventList(email));
            userArray.add(newobj);
        }
        try {
            Files.write(Paths.get(json), userArray.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}