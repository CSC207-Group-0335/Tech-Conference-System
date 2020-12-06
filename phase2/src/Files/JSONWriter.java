package Files;

import MessagingPresenters.ConversationManager;
import MessagingPresenters.ConversationStorage;
import MessagingPresenters.Message;
import Schedule.Event;
import Schedule.EventManager;
import UserLogin.User;
import UserLogin.UserManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.Files;

public class JSONWriter {

    public void writeToEvents(String json, EventManager events) {
        JSONArray eventArray = new JSONArray();

        for (Event event:
             events.eventList) {
            JSONObject eventObject = new JSONObject();
            eventObject.put("title", event.getTitle());
            LocalDateTime startTime = event.getStartTime();
            LocalDateTime endTime = event.getStartTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formatted = startTime.format(formatter);
            String formatted2 = endTime.format(formatter);
            eventObject.put("startTime", formatted);
            eventObject.put("endTime", formatted2);
            eventObject.put("eventId", event.getEventId());
            eventObject.put("roomName", event.getRoomName());
            eventObject.put("usersSignedUp", event.getUsersSignedUp());
            eventObject.put("speakers", event.getSpeakers());
            eventObject.put("vipRestricted", event.getVIPStatus());
            eventObject.put("capacity", event.getCapacity());
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

        for (User user:
                users.getUserList()) {
            JSONObject userObject = new JSONObject();
            userObject.put("type", user.getType());
            userObject.put("vip", user.getVIPStatus());
            userObject.put("name", user.getName());
            userObject.put("password", user.getPassword());
            userObject.put("email", user.getEmail());
            userObject.put("ListOfTalkIDs", user.getEventList());
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
            for (Message message: convo.getMessages()){
                JSONObject messageobj = new JSONObject();
                messageobj.put("recipient", message.getRecipientEmail());
                messageobj.put("sender", message.getSenderEmail());
                LocalDateTime startTime = message.getTimestamp();
                String formatted = startTime.format(formatter);
                messageobj.put("time", formatted);
                messageobj.put("content", message.getMessageContent());
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

}