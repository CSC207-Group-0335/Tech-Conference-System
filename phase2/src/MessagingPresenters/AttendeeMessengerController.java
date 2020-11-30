package MessagingPresenters;

import UserLogin.MainMenuController;
import UserLogin.UserStorage;

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
                                       UserStorage userStorage, ConversationStorage conversationStorage) {
        super(attendeeEmail, scanner, mainMenuController, userStorage, conversationStorage);
        messageManager = new AttendeeMessageManager(attendeeEmail, userStorage, conversationStorage);
        this.presenter = new AttendeeMessengerPresenter();
        this.userStorage = userStorage;
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

    /**
     * Returns an arraylist containing all message history between this attendee and the user registered under the
     * email </email>.
     *
     * @param email a String representing the email of the recipient
     * @return an arraylist containing all messages between this organizer and the user
     */

    public ArrayList<Message> viewMessages(String email) {
        return messageManager.viewMessages(email);
    }

    /**
     * Returns a list containing all recipients.
     *
     * @return an ArrayList containing all recipients
     */

    public ArrayList<String> getRecipients() {
        return messageManager.getRecipients();
    }

    /**
     * Runs the presenter.
     */

    public void run() {
        boolean flag = true;
        OUTER_LOOP:
        while (flag) {
            presenter.printMenu(0);
            int option = Integer.parseInt(scan.nextLine());
            try {
                if (option == 0) {
                    flag = false;
                    presenter.printMenu(4);
                    mainMenuController.runMainMenu(email);
                } else if (option == 1) {
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
                } else if (option == 2) {
                    ArrayList<String> emails = getRecipients();
                    presenter.viewChats(emails);
                    int index = Integer.parseInt(scan.nextLine());
                    if (index == 0 || emails.size() == 0) {
                        continue;
                    }
                    String email = emails.get(index - 1);
                    ArrayList<Message> messages = viewMessages(email);
                    presenter.viewConversation(messages);
                }
            } catch (NumberFormatException nfe) {
                presenter.printMenu(6);
            }
        }
    }
}
