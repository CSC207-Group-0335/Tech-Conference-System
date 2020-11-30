package MessagingPresenters;

import Files.CSVReader;
import UserLogin.*;
import com.sun.tools.javac.Main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * A class that represents a messaging system.
 */

public class MessagingSystem{
    public ConversationStorage conversationStorage;
    public String userEmail;
    public MessengerController messengerController;
    public MainMenuController mainMenuController;
    public UserStorage userStorage;

    /**
     * Instantiates OrganizerMessengerController, SpeakerMessengerController, MessengerController, and
     * ConversationStorage.
     */

    public MessagingSystem(UserStorage userStorage, MainMenuController mainMenuController) {
        this.conversationStorage = new ConversationStorage();
        this.userStorage = userStorage;
        this.mainMenuController = mainMenuController;
    }

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
        switch (userStorage.emailToType(user)) {
            case "Attendee":
                this.messengerController = new AttendeeMessengerController(userEmail, scanner, mainMenuController,
                        userStorage, conversationStorage);
                break;
            case "Speaker":
                this.messengerController = new SpeakerMessengerController(userEmail, scanner, mainMenuController,
                        userStorage, conversationStorage);
                break;
            case "Organizer":
                this.messengerController = new OrganizerMessengerController(userEmail, scanner, mainMenuController,
                        userStorage, conversationStorage);
                break;
        }
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
        ConversationCSVWriter csvWriter = new ConversationCSVWriter("phase1/src/Resources/Conversations.csv",
                this.conversationStorage.getConversationManagers());
    }

}


