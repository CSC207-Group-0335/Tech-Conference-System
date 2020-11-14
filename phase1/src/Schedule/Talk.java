package Schedule;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class Talk {
    String title;
    LocalDateTime startTime;
    UUID talkId;

    public Talk(String title, LocalDateTime startTime){
        this.title = title;
        this.startTime = startTime;
        this.talkId = UUID.randomUUID();
    }

    public Talk(String title, LocalDateTime startTime, UUID talkId){
        this.title = title;
        this.startTime = startTime;
        this.talkId = talkId;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public UUID getTalkId() {
        return talkId;
    }
}
