package MessagingPresenters;

import Schedule.EventManager;
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
                                       UserStorage userStorage, ConversationStorage conversationStorage, EventManager eventManager) {
        super(attendeeEmail, scanner, mainMenuController, userStorage, conversationStorage, eventManager);
        messageManager = new AttendeeMessageManager(attendeeEmail, userStorage, conversationStorage);
        this.presenter = new AttendeeMessengerPresenter();
        this.userStorage = userStorage;
    }


    /**
     * Returns a list containing all recipients.
     *
     * @return an ArrayList containing all recipients
     */

    public ArrayList<String> getRecipients() {
        return messageManager.getRecipients();
    }

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
                    ArrayList<Message> messages = viewUnarchivedMessages(email);
                    presenter.viewConversation(messages);
                }
            } catch (NumberFormatException nfe) {
                presenter.printMenu(6);
            }
        }
    }
}
