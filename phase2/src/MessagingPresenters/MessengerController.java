package MessagingPresenters;

import Schedule.EventManager;
import UserLogin.MainMenuController;
import UserLogin.Organizer;
import UserLogin.Speaker;
import UserLogin.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
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

    public void deleteMessage(int index, String senderEmail){
        messageManager.deleteMessage(senderEmail, index);
    }

    public ArrayList<String> getEventIDS(){return messageManager.getEventIDs();}

    public ArrayList<String> getGroupChatMessages(String eventID){return messageManager.getGroupChatMessages(eventID);}

    public void runIndividualChatMenu(MessengerPresenter presenter, ArrayList<String> emails) {
        presenter.viewChats(emails);
        int index = Integer.parseInt(scan.nextLine());
        if (index == 0 || emails.size() == 0) {
            return;
        }
        String email = emails.get(index - 1);
        Boolean viewingArchivedMessages = false;
        char input = 'a';
        while (input != '0') {
            ArrayList<Message> messages;
            if (viewingArchivedMessages) {
                messages = viewArchivedMessages(email);
            }
            else {
                messages = viewUnarchivedMessages(email);
            }
            ArrayList<String> outputMessages = new ArrayList<>();
            for (int i = 1; i <= messages.size(); i++) {
                Message message = messages.get(i - 1);
                outputMessages.add(i + " - " + message.getSenderEmail() + ": " + message.getMessageContent());
            }
            presenter.viewConversation(outputMessages, viewingArchivedMessages);
            String in = scan.nextLine();
            input = in.toCharArray()[0];
            if (input == 'a') {
                viewingArchivedMessages = !viewingArchivedMessages;
            }
            else if (input != '0') {
                int position = Integer.parseInt(in) - 1;
                String msg = messages.get(position).getMessageContent();
                presenter.viewMessageMenu(msg, viewingArchivedMessages);
                int opt = Integer.parseInt(scan.nextLine());
                if (opt == 1) {
                    // DELETION
                    deleteMessage(position, messages.get(position).getSenderEmail());
                    presenter.printSuccessfulDeletion();
                }
                else if (opt == 2) {
                    // READ/UNREAD
                    if (messageManager.hasMessageStatus(email, position, "Unread")) {
                        messageManager.deleteMessageStatus(email, position, "Unread");
                        messageManager.addMessageStatus(email, position, "Read");
                    }
                    else {
                        messageManager.deleteMessageStatus(email, position, "Read");
                        messageManager.addMessageStatus(email, position, "Unread");
                    }
                }
                else if (opt == 3) {
                    // ARCHIVAL
                    if (messageManager.hasMessageStatus(email, position, "Archived")) {
                        messageManager.deleteMessageStatus(email, position, "Archived");
                        messageManager.addMessageStatus(email, position, "Unarchived");
                    }
                }
            }
        }
    }

    public void runGroupChatMenu(MessengerPresenter presenter, ArrayList<String> talkIDS) {
        presenter.viewGroupChats(talkIDS);
        int index = Integer.parseInt(scan.nextLine());
        if (index == 0 || talkIDS.size() == 0) {
            return;
        }
        String groupChatID = talkIDS.get(index - 1);
        char input = 'a';
        while (input != '0'){
            ArrayList<String> messages = getGroupChatMessages(groupChatID);
            presenter.viewGroupChat(messages);
            input = scan.nextLine().toCharArray()[0];
        }
    }

    public abstract void run();
}
