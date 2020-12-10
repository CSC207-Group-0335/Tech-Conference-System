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
        String recipientEmail = emails.get(index - 1);
        Boolean viewingArchivedMessages = false;
        char input = 'a';
        while (input != '0') {
            ArrayList<Message> messages;
            if (viewingArchivedMessages) {
                messages = viewArchivedMessages(recipientEmail);
            }
            else {
                messages = viewUnarchivedMessages(recipientEmail);
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
                Boolean isRead = messages.get(position).hasStatus(email, "Read");
                presenter.viewMessageMenu(msg, viewingArchivedMessages, isRead);
                int opt = Integer.parseInt(scan.nextLine());
                if (opt == 1) {
                    // DELETION
                    deleteMessage(position, messages.get(position).getSenderEmail());
                    presenter.printSuccessfulDeletion();
                }
                else if (opt == 2) {
                    // READ/UNREAD
                    if (messageManager.hasMessageStatus(recipientEmail, position, "Unread")) {
                        messageManager.deleteMessageStatus(recipientEmail, position, "Unread");
                        messageManager.addMessageStatus(recipientEmail, position, "Read");
                    }
                    else {
                        messageManager.deleteMessageStatus(recipientEmail, position, "Read");
                        messageManager.addMessageStatus(recipientEmail, position, "Unread");
                    }
                }
                else if (opt == 3) {
                    // ARCHIVAL
                    if (messageManager.hasMessageStatus(recipientEmail, position, "Archived")) {
                        messageManager.deleteMessageStatus(recipientEmail, position, "Archived");
                        messageManager.addMessageStatus(recipientEmail, position, "Unarchived");
                    }
                    else {
                        messageManager.deleteMessageStatus(recipientEmail, position, "Unarchived");
                        messageManager.addMessageStatus(recipientEmail, position, "Archived");
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
