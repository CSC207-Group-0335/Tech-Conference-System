package MessagingPresenters;

import Files.CSVReader;
import Files.JSONWriter;
import Schedule.EventManager;
import UserLogin.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

/**
 * A class that represents a messaging system.
 */

public class MessagingSystem extends Observable{
    public ConversationStorage conversationStorage;
    public String userEmail;
    public MessengerController messengerController;
    public MainMenuController mainMenuController;
    public UserManager userManager;
    public EventManager eventManager;

    /**
     * Instantiates OrganizerMessengerController, SpeakerMessengerController, MessengerController, and
     * ConversationStorage.
     */

    public MessagingSystem(UserManager userManager,
                           EventManager eventManager) {
        this.conversationStorage = new ConversationStorage();
        this.userManager = userManager;
        this.eventManager =eventManager;
    }

    /**
     * Sets an email.
     *
     * @param email a String representing an email address
     */

    public void setEmail(String email){
        this.userEmail = email;
    }

    /**
     * Instantiates controller classes.
     *
     * @param user    a User
     * @param scanner a Scanner
     */

    public void instantiateControllers(String user, Scanner scanner) {
        this.addObserver(mainMenuController);
        switch (userManager.emailToType(user)) {
            case "Attendee":
                this.messengerController = new AttendeeMessengerController(userEmail, scanner, mainMenuController,
                        userManager, conversationStorage);
                setMessengerController();
                break;
            case "Speaker":
                this.messengerController = new SpeakerMessengerController(userEmail, scanner, mainMenuController,
                        userManager, conversationStorage, eventManager);
                setMessengerController();
                break;
            case "Organizer":
                this.messengerController = new OrganizerMessengerController(userEmail, scanner, mainMenuController,
                        userManager, conversationStorage);
                setMessengerController();
                break;
        }
    }


    public void setMessengerController(){
        setChanged();
        notifyObservers(messengerController);
    }

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
    }

    /**
     * Method to write the changes to the Conversations.csv, called in MainMenuController.logout().
     */

    public void save() {
        ConversationCSVWriter csvWriter = new ConversationCSVWriter("src/Resources/Conversations.csv",
        this.conversationStorage.getConversationManagers());
        JSONWriter jsonWriter = new JSONWriter();
        jsonWriter.writeToConversations("src/Resources/Conversations.json", this.conversationStorage);
    }

    public void setMainMenuController(MainMenuController mainMenuController){
        this.mainMenuController = mainMenuController;
    }

}


