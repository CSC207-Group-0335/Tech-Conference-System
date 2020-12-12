package MessagingPresenters;

import Files.JSONReader;
import Files.JSONWriter;
import Schedule.EventManager;
import UserLogin.MainMenuController;
import UserLogin.UserManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
        this.eventManager = eventManager;
    }

    /**
     * Method to set Email.
     * @param email
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
                        userManager, conversationStorage, eventManager);
                setMessengerController();
                break;
            case "Speaker":
                this.messengerController = new SpeakerMessengerController(userEmail, scanner, mainMenuController,
                        userManager, conversationStorage, eventManager);
                setMessengerController();
                break;
            case "Organizer":
                this.messengerController = new OrganizerMessengerController(userEmail, scanner, mainMenuController,
                        userManager, conversationStorage, eventManager);
                setMessengerController();
                break;
        }
    }

    /**
     * Method to notify observers.
     */
    public void setMessengerController(){
        setChanged();
        notifyObservers(messengerController);
    }

    /**
     * Run method that runs the messaging system in the program
     * @throws Exception
     */
    public void run() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        JSONReader jsonReader = new JSONReader();
        Object obj = jsonReader.readJson("src/Resources/Conversations.json");
        JSONArray convoList = (JSONArray) obj;
        convoList.forEach(con -> {
            JSONObject convo = (JSONObject) con; //cast con as a JSONObject
            //get all of the necessary elements to create an convo from the object
            ArrayList<String> participants = (ArrayList<String>) convo.get("participants");
            //access each participant from the stored participants arrayList
            String participantOne = participants.get(0);
            String participantTwo = participants.get(1);
            //create a conversation manager with the participants
            ConversationManager c = conversationStorage.addConversationManager(participantOne, participantTwo);
            //create a new JSONArray from chatLog. Similar process to above because of nested arrays
            Object messObj = convo.get("chatLog");
            JSONArray messagesList = (JSONArray) messObj;
            messagesList.forEach(mes -> {
                JSONObject message = (JSONObject) mes;
                String sender = (String) message.get("sender");
                String recipient = (String) message.get("recipient");
                LocalDateTime time = (LocalDateTime.parse((CharSequence) message.get("time"), formatter));
                String content = (String) message.get("content");
                ArrayList<String> recipientStatus = (ArrayList<String>) message.get("recipientstatus");
                ArrayList<String> senderStatus = (ArrayList<String>) message.get("senderstatus");
                c.addMessage(recipient, sender, time, content, senderStatus, recipientStatus);
                });
            });
        Object obj2 = jsonReader.readJson("src/Resources/GroupConversations.json");
        JSONArray convoList2 = (JSONArray) obj2;
        convoList2.forEach(con -> {
            JSONObject convo2 = (JSONObject) con; //cast con as a JSONObject
            //get all of the necessary elements to create an convo from the object
            String eventID = (String) convo2.get("groupChatID");
            //create a conversation manager with the participants
            GroupChatManager g = conversationStorage.addGroupChatManager(eventID);
            //create a new JSONArray from chatLog. Similar process to above because of nested arrays
            Object messObj2 = convo2.get("chatLog");
            JSONArray messagesList2 = (JSONArray) messObj2;
            messagesList2.forEach(mes -> {
                JSONObject message = (JSONObject) mes;
                String sender = (String) message.get("sender");
                LocalDateTime time = (LocalDateTime.parse((CharSequence) message.get("time"), formatter));
                String content = (String) message.get("content");
                g.addMessage(sender, time, content);
            });
        });
        };
    /**
     * Method to write the changes to the Conversations.json, called in MainMenuController.logout().
     */

    public void save() {
        JSONWriter jsonWriter = new JSONWriter();
        jsonWriter.writeToConversations("src/Resources/Conversations.json", conversationStorage);
        jsonWriter.writeToGroupConversations("src/Resources/GroupConversations.json", conversationStorage);
    }

    /**
     * Method to set the MainMenuController.
     * @param mainMenuController The MainMenuController.
     */
    public void setMainMenuController(MainMenuController mainMenuController){
        this.mainMenuController = mainMenuController;
    }

}


