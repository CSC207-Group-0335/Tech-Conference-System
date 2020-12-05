package Files;

import Schedule.Event;
import Schedule.EventManager;
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
}