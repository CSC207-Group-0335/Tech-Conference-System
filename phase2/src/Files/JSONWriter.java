package Files;

import MessagingPresenters.ConversationManager;
import MessagingPresenters.ConversationStorage;
import MessagingPresenters.GroupChatManager;
import Schedule.EventManager;
import Schedule.RoomStorage;
import UserLogin.UserManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Set;

public class JSONWriter {
    /**
     * Method to store events from the program.
     * @param json The string location of the JSON file.
     * @param events The EventManager.
     */
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

    /**
     * Method to store Users from the program.
     * @param json The string location of the JSON.
     * @param users The UserManager.
     */
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
            LinkedHashMap<String, String> requests = users.emailToRequests(email);
            if (!requests.isEmpty()){
                Set<String> keys = requests.keySet();

                JSONArray requestarray = new JSONArray();
            for(String request: keys){
                String status =  requests.get(request);
                JSONObject requestobj = new JSONObject();
                requestobj.put("request", request);
                requestobj.put("status", status);
                requestarray.add(requestobj);
            }
            userObject.put("requests", requestarray);
            }
            if(requests.isEmpty()){
                JSONArray requestarray = new JSONArray();
                userObject.put("requests", requestarray);}



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

    /**
     * Method to save conversations from the program.
     * @param json The sting location of the JSON.
     * @param convos The ConversationStorage.
     */
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

    /**
     * Method to save conversations from the program.
     * @param json The sting location of the JSON.
     * @param convos The ConversationStorage.
     */
    public void writeToGroupConversations(String json, ConversationStorage convos){
        JSONArray convoArray = new JSONArray();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (GroupChatManager groupConvo:
                convos.getGroupChatManagers()) {
            JSONArray messagesArray = new JSONArray();
            JSONObject convoObject = new JSONObject();
            convoObject.put("groupChatID", groupConvo.getEventID());
            for (String messageid: groupConvo.getMessageIDs()){
                JSONObject messageobj = new JSONObject();
                messageobj.put("sender", groupConvo.getSenderOfMessageWithID(messageid));
                LocalDateTime time = groupConvo.getTimestampOfMessageWithID(messageid);
                String formatted = time.format(formatter);
                messageobj.put("time", formatted);
                messageobj.put("content", groupConvo.getContentOfMessageWithID(messageid));
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

    /**
     * Method to save rooms from the program.
     * @param json The string location of the JSON.
     * @param rooms The RoomStorage.
     */
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

    /**
     * Method to save events signups.
     * @param json The string location of the JSON.
     * @param users The UserManager.
     */
    public void writeToRegistration(String json, UserManager users){
        JSONArray userArray = new JSONArray();
        for(String email: users.getUserEmailList()){
            JSONObject newobj = new JSONObject();
            newobj.put("email", email);
            newobj.put("eventList", users.emailToEventList(email));
            userArray.add(newobj);
        }
        try {
            Files.write(Paths.get(json), userArray.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


//    public void writeToGroupchat(String json, ConversationStorage storage){
//        JSONArray convoArray = new JSONArray();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//
//        for(GroupChatManager chat: storage.getGroupChatManagers()){
//            JSONArray messagesArray = new JSONArray();
//            JSONObject convoObject = new JSONObject();
//            convoObject.put("eventID", chat.getEventID());
//            for(String messageid: chat.getMessageIDs()){
//            JSONObject messageobj = new JSONObject();
//            messageobj.put("recipient", chat.getRecipientOfMessageWithID(messageid));
//            messageobj.put("sender", chat.getSenderOfMessageWithID(messageid));
//            LocalDateTime time = chat.getTimestampOfMessageWithID(messageid);
//            String formatted = time.format(formatter);
//            messageobj.put("time", formatted);
//            messageobj.put("content", chat.getContentOfMessageWithID(messageid));
//            messageobj.put("recipientstatus", chat.getRecipientStatusesOfMessageWithID(messageid));
//            messageobj.put("senderstatus", chat.getSenderStatusesOfMessageWithID(messageid));
//            messagesArray.add(messageobj);
//
//
//
//
//        }
//
//    }
//
//
//
//}
}