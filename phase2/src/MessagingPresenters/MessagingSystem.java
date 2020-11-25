package MessagingPresenters;

import Files.CSVReader;
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
    public String  userEmail;
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

    /**
     * Notifies when ConversationStorage has been updated.
     */
    public void setStorage() {
        setChanged();
        notifyObservers(conversationStorage);
    }

    /**
     * Instantiates controller classes.
     *
     * @param user    a User
     * @param scanner a Scanner
     */

    public void instantiateControllers(User user, Scanner scanner) {
        this.addObserver(mainMenuController);
        if (user instanceof Attendee) {
            this.attendeeMessengerController = new AttendeeMessengerController(userEmail, scanner, mainMenuController);
            this.addObserver(this.attendeeMessengerController.userInfo);
            setStorage();
        }
        if (user instanceof Speaker) {
            this.speakerMessengerController = new SpeakerMessengerController(userEmail, scanner, mainMenuController);
            this.addObserver(this.speakerMessengerController.userInfo);
            setStorage();
        }
        if (user instanceof Organizer) {
            this.organizerMessengerController = new OrganizerMessengerController(userEmail, scanner, mainMenuController);
            this.addObserver(this.organizerMessengerController.userInfo);
            setStorage();
        }
    }

    /**
     * A method used by the Observable Design Pattern to update variables in this Observer class based on changes made
     * in linked Observable classes. This one updates the User and the MainMenuController, based on the arg type.
     *
     * @param o   the Observable class where the change is made and this function is called.
     * @param arg the argument that is being updated.
     */

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof User) {
            User user = (User) arg;
            this.userEmail = user.getEmail();
        }
        if (arg instanceof MainMenuController) {
            this.mainMenuController = (MainMenuController) arg;
        }
    }

    /**
     * Runs and loads data.
     */

    public void run() {
        CSVReader fileReader = new CSVReader("src/Resources/Conversations.csv");
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
                String messageContent = singleMessage[3].replace("commaseparator", ",");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime timestamp = LocalDateTime.parse(timestampString, formatter);
                c.addMessage(recipient, sender, timestamp, messageContent);
            }
        }
        setStorage();
    }

    /**
     * Method to write the changes to the Conversations.csv, called in MainMenuController.logout().
     */

    public void save() {
        ConversationCSVWriter csvWriter = new ConversationCSVWriter("phase1/src/Resources/Conversations.csv",
                this.conversationStorage.getConversationManagers());
    }

}


