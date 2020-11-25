package MessagingPresenters;

import UserLogin.MainMenuController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * A class that represents the messenger controller.
 */

public class AttendeeMessengerController {
    private String attendeeEmail;
    public MessageManager userInfo;
    private ConversationStorage conversationStorage;
    private AttendeeMessengerPresenter presenter;
    public Scanner scan;
    public MainMenuController mainMenuController;

    /**
     * A user is required to create an instance of this class.\
     */

    public AttendeeMessengerController(String attendeeEmail, Scanner scanner, MainMenuController mainMenuController) {
        this.attendeeEmail = attendeeEmail;
        this.userInfo = new MessageManager(attendeeEmail);
        this.presenter = new AttendeeMessengerPresenter();
        this.scan = scanner;
        this.mainMenuController = mainMenuController;
    }

    /**
     * Sends a message containing </messageContent> to a user registered under the email </email> if and only if this
     * attendee is allowed to message that user.
     * @param email a String representing the email of the recipient
     * @param messageContent a String representing the content of the message
     */

    public void message(String email, String messageContent){
        if (userInfo.canMessage(email)){
            if (conversationStorage.contains(attendeeEmail, email)){
                ConversationManager c = conversationStorage.getConversationManager(attendeeEmail, email);
                c.addMessage(email, attendeeEmail, LocalDateTime.now(), messageContent);
            }
            else{
                ConversationManager c = conversationStorage.addConversationManager(attendeeEmail, email);
                c.addMessage(email, attendeeEmail, LocalDateTime.now(), messageContent);
            }
        }
    }

    /**
     * Returns an arraylist containing all message history between this attendee and the user registered under the
     * email </email>.
     * @param email a String representing the email of the recipient
     * @return an arraylist containing all messages between this organizer and the user
     */

    public ArrayList<Message> viewMessages(String email){
        if (userInfo.canMessage(email)){
            if (conversationStorage.contains(attendeeEmail, email)){
                ConversationManager c = conversationStorage.getConversationManager(attendeeEmail, email);
                return c.getMessages();
            }
            else{
                ConversationManager c = conversationStorage.addConversationManager(attendeeEmail, email);
                return c.getMessages();
            }
        }
        return null;
    }

    /**
     * Returns a list containing all recipients.
     * @return an ArrayList containing all recipients
     */

    public ArrayList<String> getRecipients() {
        ArrayList<String> emails = new ArrayList<>();
        ArrayList<ConversationManager> managers = conversationStorage.getConversationManagers();
        for (ConversationManager manager: managers) {
            if (manager.getParticipants().contains(attendeeEmail)){
                ArrayList<String> participants = new ArrayList<>(manager.getParticipants());
                participants.remove(attendeeEmail);
                emails.add(participants.get(0));
            }
        }
        return emails;
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
                    mainMenuController.runMainMenu(attendeeEmail);
                } else if (option == 1) {
                    presenter.printMenu(1);
                    String email = new String();
                    boolean valid_recipient = false;

                    while (!valid_recipient) {
                        email = scan.nextLine();
                        if (email.equals("0")) {
                            continue OUTER_LOOP;
                        }
                        if (userInfo.canMessage(email)) {
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
                presenter.printMenu(6); }
        }
    }
}
