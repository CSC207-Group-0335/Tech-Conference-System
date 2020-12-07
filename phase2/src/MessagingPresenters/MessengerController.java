package MessagingPresenters;

import Schedule.EventManager;
import UserLogin.MainMenuController;
import UserLogin.Organizer;
import UserLogin.Speaker;
import UserLogin.UserStorage;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class MessengerController {
    public String email;
    public Scanner scan;
    public MainMenuController mainMenuController;
    public UserStorage userStorage;
    public ConversationStorage conversationStorage;
    public MessageManager messageManager;
    public EventManager eventManager;

    public MessengerController(String email, Scanner scan, MainMenuController mainMenuController,
                               UserStorage userStorage, ConversationStorage conversationStorage, EventManager eventManager) {
        this.email = email;
        this.scan = scan;
        this.mainMenuController = mainMenuController;
        this.userStorage = userStorage;
        this.conversationStorage = conversationStorage;
        this.eventManager = eventManager;
        if (userStorage.emailToUser(email) instanceof Organizer){
            this.messageManager = new OrganizerMessageManager(email, userStorage, conversationStorage);
        }else if (userStorage.emailToUser(email) instanceof Speaker){
            this.messageManager = new SpeakerMessageManager(email, userStorage, conversationStorage);
        }else{
            this.messageManager = new AttendeeMessageManager(email, userStorage, conversationStorage);
        }
    }

    /**
     * Sends a message containing </messageContent> to a user registered under the email </email> if and only if this
     * attendee is allowed to message that user.
     *
     * @param email          a String representing the email of the recipient
     * @param messageContent a String representing the content of the message
     */

    public void message(String email, String messageContent) {
        messageManager.messageOne(email, messageContent);
    }
    public ArrayList<Message> viewUnarchivedMessages(String email) {
        return messageManager.viewUnarchivedMessages(email);
    }

    public ArrayList<Message> viewArchivedMessages(String email) {
        return messageManager.viewArchivedMessages(email);
    }

    public void setStatus(int index, String status){
        messageManager.changeMessageStatus(email, index, status);
    }

    public void deleteMessage(int index, String senderEmail){
        messageManager.deleteMessage(senderEmail, index);
    }



    public abstract void run();
}
