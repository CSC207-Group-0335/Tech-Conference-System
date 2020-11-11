import java.util.Date;

public class Talk {
    String title;
    Date startTime;

    public Talk(String title, Date startTime){
        this.title = title;
        this.startTime = startTime;
    }

    public String getTitle() {
        return title;
    }

    public Date getStartTime() {
        return startTime;
    }
}
