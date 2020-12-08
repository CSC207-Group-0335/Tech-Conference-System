package MessagingPresenters;

import Schedule.EventManager;
import UserLogin.MainMenuController;
import UserLogin.Organizer;
import UserLogin.Speaker;
import UserLogin.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class MessengerController {
    public String email;
    public Scanner scan;
    public MainMenuController mainMenuController;
    public UserManager userManager;
    public ConversationStorage conversationStorage;
    public MessageManager messageManager;
    public EventManager eventManager;

    public MessengerController(String email, Scanner scan, MainMenuController mainMenuController,
                               UserManager userManager, ConversationStorage conversationStorage, EventManager eventManager) {
        this.email = email;
        this.scan = scan;
        this.mainMenuController = mainMenuController;
        this.userManager = userManager;
        this.conversationStorage = conversationStorage;
        this.eventManager = eventManager;
        if (userManager.emailToUser(email) instanceof Organizer){
            this.messageManager = new OrganizerMessageManager(email, userManager, conversationStorage, eventManager);
        }else if (userManager.emailToUser(email) instanceof Speaker){
            this.messageManager = new SpeakerMessageManager(email, userManager, conversationStorage, eventManager);
        }else{
            this.messageManager = new AttendeeMessageManager(email, userManager, conversationStorage, eventManager);
        }
    }

    /**
     * Sends a message containing </messageContent> to a user registered under the email </email> if and only if this
     * attendee is allowed to message that user.
     *
     * @param recipient          a String representing the email of the recipient
     * @param messageContent a String representing the content of the message
     */

    public void message(String recipient, String messageContent) {
        messageManager.message(recipient, messageContent);
    }
    public ArrayList<Message> viewUnarchivedMessages(String email) {
        return messageManager.getUnarchivedMessages(email);
    }

    public ArrayList<Message> viewArchivedMessages(String email) {
        return messageManager.getArchivedMessages(email);
    }

    public void setStatus(int index, String status){
        messageManager.changeMessageStatus(email, index, status);
    }

    public void deleteMessage(int index, String senderEmail){
        messageManager.deleteMessage(senderEmail, index);
    }

    public ArrayList<String> getEventIDS(){return messageManager.getEventIDs();}

    public ArrayList<String> getGroupChatMessages(String talkID){return messageManager.getGroupChatMessages(talkID);}



    public abstract void run();
}
