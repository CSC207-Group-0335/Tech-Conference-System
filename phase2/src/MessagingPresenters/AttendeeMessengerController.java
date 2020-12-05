package MessagingPresenters;

import UserLogin.MainMenuController;
import UserLogin.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class that represents the messenger controller.
 */

public class AttendeeMessengerController extends MessengerController {
    private final AttendeeMessengerPresenter presenter;
    public final AttendeeMessageManager messageManager;

    /**
     * A user is required to create an instance of this class.\
     */

    public AttendeeMessengerController(String attendeeEmail, Scanner scanner, MainMenuController mainMenuController,
                                       UserManager userManager, ConversationStorage conversationStorage) {
        super(attendeeEmail, scanner, mainMenuController, userManager, conversationStorage);
        messageManager = new AttendeeMessageManager(attendeeEmail, userManager, conversationStorage);
        this.presenter = new AttendeeMessengerPresenter();
        this.userManager = userManager;
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

    /**
     * Returns a list containing all recipients.
     *
     * @return an ArrayList containing all recipients
     */

    public ArrayList<String> getRecipients() {
        return messageManager.getRecipients();
    }

    public void setStatus(int index, String status){
        messageManager.changeMessageStatus(email, index, status);
    }

    public void deleteMessage(int index, String senderEmail){
        messageManager.deleteMessage(senderEmail, index);
    }

    public void run() {
        boolean flag = true;
        OUTER_LOOP:
        while (flag) {
            presenter.printMenu(0);
            int option = Integer.parseInt(scan.nextLine());
            try {
                switch (option) {
                    case 0:
                        flag = false;
                        presenter.printMenu(4);
                        mainMenuController.runMainMenu(email);
                        break;
                    case 1: {
                        presenter.printMenu(1);
                        String email = "";
                        boolean valid_recipient = false;

                        while (!valid_recipient) {
                            email = scan.nextLine();
                            if (email.equals("0")) {
                                continue OUTER_LOOP;
                            }
                            if (messageManager.canMessage(email)) {
                                valid_recipient = true;
                            } else {
                                presenter.printMenu(5);
                            }
                        }

                        presenter.printMenu(2);
                        String body = scan.nextLine();
                        if (body.equals("0")) {
                            continue;
                        }

                        message(email, body);
                        presenter.printMenu(3);
                        break;
                    }
                    case 2: {
                        ArrayList<String> emails = getRecipients();
                        presenter.viewChats(emails);
                        int index = Integer.parseInt(scan.nextLine());
                        if (index == 0 || emails.size() == 0) {
                            continue;
                        }
                        String email = emails.get(index - 1);
                        ArrayList<Message> messages = viewUnarchivedMessages(email);
                        presenter.viewConversation(messages);
                        break;
                    }
                }
            } catch (NumberFormatException nfe) {
                presenter.printMenu(6);
            }
        }
    }
}
