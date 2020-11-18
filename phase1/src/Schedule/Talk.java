package Schedule;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class Talk {
    String title;
    LocalDateTime startTime;
    String talkId;

    public Talk(String title, LocalDateTime startTime){
        this.title = title;
        this.startTime = startTime;
        this.talkId = UUID.randomUUID().toString();
    }

    public Talk(String title, LocalDateTime startTime, String talkId){
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

    public String getTalkId() {
        return talkId;
    }

    @Override
    public String toString() {
        return "Talk{" +
                "title='" + title + '\'' +
                ", startTime=" + startTime +
                '}';
    }
}
