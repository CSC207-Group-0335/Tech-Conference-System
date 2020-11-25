package Schedule;

import java.time.LocalDateTime;

import java.util.UUID;

/**
 * Represents a talk.
 */
public class Talk {
    String title;
    LocalDateTime startTime;
    LocalDateTime endTime;
    /**
     * A unique identifier for each talk.
     */
    String talkId;

    /**
     * Creates a talk with the specified title and time.
     * @param title The title of the talk.
     * @param startTime The start time of the talk.
     */
    public Talk(String title, LocalDateTime startTime, LocalDateTime endTime){
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.talkId = UUID.randomUUID().toString();
    }

    /**
     * Creates a talk with the specified title, time, and id.
     * @param title The title of the talk.
     * @param startTime The start time of the talk.
     * @param talkId The id of the talk.
     */
    public Talk(String title, LocalDateTime startTime, LocalDateTime endTime, String talkId){
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.talkId = talkId;
    }

    /**
     * Retrieves the tile of the talk.
     * @return A string representing the title of the talk.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the start time of the talk.
     * @return A LocalDateTime representing the start time of the talk.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Retrieves the id of the talk.
     * @return A UUID representing the id of the talk.
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public String getTalkId() {
        return talkId;
    }

    /**
     * A string representation of the talk entity.
     * @return A string representing the talk entity.
     */
    @Override
    public String toString() {
        return "Talk{" +
                "title='" + title + '\'' +
                ", startTime=" + startTime + '\'' +
                ", endTime=" + endTime +
                '}';
    }
}
