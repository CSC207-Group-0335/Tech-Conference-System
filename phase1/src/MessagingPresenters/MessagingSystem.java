package MessagingPresenters;

import Schedule.CSVReader;
import UserLogin.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * A class that represents a messaging system.
 */

public class MessagingSystem extends Observable implements Observer {
    public ConversationStorage conversationStorage;
    public User user;
    public AttendeeMessengerController attendeeMessengerController;
    public SpeakerMessengerController speakerMessengerController;
    public OrganizerMessengerController organizerMessengerController;
    public MainMenuController mainMenuController;

    /**
     * Instantiates OrganizerMessengerController, SpeakerMessengerController, MessengerController, and
     * ConversationStorage.
     */

    public MessagingSystem() {
        this.conversationStorage = new ConversationStorage();
        setStorage();
    }

    //Method to notify that ConversationStorage has been updated
    public void setStorage() {
        setChanged();
        notifyObservers(conversationStorage);
    }

    public void instantiateControllers(User user, Scanner scanner) {
        this.addObserver(mainMenuController);
        if (user instanceof Attendee) {
            this.attendeeMessengerController = new AttendeeMessengerController((Attendee) user, scanner);
            this.addObserver(this.attendeeMessengerController);
            setAttendeeMessengerController();
            setStorage();
        }
        if (user instanceof Speaker) {
            this.speakerMessengerController = new SpeakerMessengerController((Speaker) user, scanner);
            this.addObserver(this.speakerMessengerController);
            this.addObserver(this.speakerMessengerController.userInfo);
            setSpeakerMessengerController();
            setStorage();
        }
        if (user instanceof Organizer) {
            this.organizerMessengerController = new OrganizerMessengerController((Organizer) user, scanner);
            this.addObserver(this.organizerMessengerController);
            setOrganizerMessengerController();
            setStorage();
        }
        //Moved AddObservers NOV 15
    }

    @Override
    public void update(Observable o, Object arg) {
        //Probably no longer needed
        if (arg instanceof User) {
            this.user = (User) arg;
        }
        if (arg instanceof MainMenuController){
            this.mainMenuController = (MainMenuController) arg;
        }
    }

    /* runs and loads all old data */

    public void run() {
        CSVReader fileReader = new CSVReader("phase1/src/Resources/Conversations.csv");
        for (ArrayList<String> scheduleData : fileReader.getData()) {
            String participantOne = scheduleData.get(0);
            String participantTwo = scheduleData.get(1);
            ConversationManager c = conversationStorage.addConversationManager(participantOne, participantTwo);
            String messages = scheduleData.get(2);
            String[] individualMessages = messages.split(";");
            for (String entry : individualMessages) {
                String[] singleMessage = entry.split("~");
                String recipient = singleMessage[0];
                String sender = singleMessage[1];
                String timestampString = singleMessage[2];
                String messageContent = singleMessage[3];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime timestamp = LocalDateTime.parse(timestampString, formatter);
                c.addMessage(recipient, sender, timestamp, messageContent);
            }
        }
        setStorage();
    }

    public void setAttendeeMessengerController() {
        setChanged();
        notifyObservers(attendeeMessengerController);
    }

    public void setSpeakerMessengerController(){
        setChanged();
        notifyObservers(speakerMessengerController);
    }

    public void setOrganizerMessengerController(){
        setChanged();
        notifyObservers(organizerMessengerController);
    }
}


