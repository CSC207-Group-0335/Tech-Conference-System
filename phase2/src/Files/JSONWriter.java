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
import java.lang.Object;
import java.nio.file.Files;
import java.util.ArrayList;

public class JSONWriter {

    public void writeToEvents(String json, EventManager events) {
        JSONArray array = new JSONArray();

        for (Event event:
             events.eventList) {
            JSONObject newobject = new JSONObject();
            newobject.put("title", event.getTitle());
            LocalDateTime startTime = event.getStartTime();
            LocalDateTime endTime = event.getStartTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formatted = startTime.format(formatter);
            String formatted2 = endTime.format(formatter);
            newobject.put("startTime", formatted);
            newobject.put("endTime", formatted2);
            newobject.put("eventId", event.getEventId());
            newobject.put("roomName", event.getRoomName());
            newobject.put("usersSignedUp", event.getUsersSignedUp());
            newobject.put("speakers", event.getSpeakers());
            newobject.put("vipRestricted", event.getVIPStatus());
            array.add(newobject);

        }
        try {
            Files.write(Paths.get(json), array.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void writeToUsers(String json, UserManager users) {
        JSONArray array = new JSONArray();

        for (User user:
                users.getUserList()) {
            JSONObject newobject = new JSONObject();
            newobject.put("type", user.getType());
            newobject.put("vip", user.getVIPStatus());
            newobject.put("name", user.getName());
            newobject.put("password", user.getPassword());
            newobject.put("email", user.getEmail());
            newobject.put("ListOfTalkIDs", user.getEventList());
            array.add(newobject);

        }

        try {
            Files.write(Paths.get(json), array.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void writeToConversations(String json, ConversationStorage convos){
        JSONArray array = new JSONArray();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (ConversationManager convo:
                convos.getConversationManagers()) {
            JSONArray nestedarray = new JSONArray();
            JSONObject newobject = new JSONObject();
            newobject.put("participants", convo.getParticipants());
            for (Message message: convo.getMessages()){
                JSONObject messageobj = new JSONObject();
                messageobj.put("recipient", message.getRecipientEmail());
                messageobj.put("sender", message.getSenderEmail());
                LocalDateTime startTime = message.getTimestamp();
                String formatted = startTime.format(formatter);
                messageobj.put("time", formatted);
                messageobj.put("content", message.getMessageContent());
                nestedarray.add(messageobj);
            }
            newobject.put("chatLog", nestedarray);
            array.add(newobject);
        }

        try {
            Files.write(Paths.get(json), array.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}