package MessagingPresenters;

import UserLogin.MainMenuController;
import UserLogin.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * A class that represents an organizer message controller.
 */

public class OrganizerMessengerController extends MessengerController {
    private String organizerEmail;
    public AttendeeMessageManager userInfo;
    private ConversationStorage conversationStorage;
    private OrganizerMessengerPresenter presenter;
    public Scanner scan;
    public MainMenuController mainMenuController;

    /**
     * An organizer is required to create an instance of this class.\
     */

    public OrganizerMessengerController(String orgEmail, Scanner scanner, MainMenuController mainMenuController) {
        this.organizerEmail = orgEmail;
        this.userInfo = new AttendeeMessageManager(organizerEmail);
        this.presenter = new OrganizerMessengerPresenter();
        this.scan = scanner;
        this.mainMenuController = mainMenuController;
    }

    /**
     * Sends a message containing </messageContent> to a user registered under the email </email>.
     * @param email a String representing the email of the recipient
     * @param messageContent a String representing the content of the message
     */

    public void messageOneUser(String email, String messageContent){
        if (userInfo.canMessage(email)){
            if (conversationStorage.contains(organizerEmail, email)){
                ConversationManager c = conversationStorage.getConversationManager(organizerEmail, email);
                c.addMessage(email, organizerEmail, LocalDateTime.now(), messageContent);
            }
            else{
                ConversationManager c = conversationStorage.addConversationManager(organizerEmail, email);
                c.addMessage(email, organizerEmail, LocalDateTime.now(), messageContent);
            }
        }
    }

    /**
     * Sends a message containing </messageContent> to all attendees.
     * @param messageContent a String representing the content of the message
     */

    public void messageAllAttendees(String messageContent){
        ArrayList<User> attendees = userInfo.getAttendees();
        for (User attendee: attendees){
            messageOneUser(attendee.getEmail(), messageContent);
        }
    }

    /**
     * Sends a message containing </messageContent> to all speakers.
     * @param messageContent a String representing the content of the message
     */

    public void messageAllSpeakers(String messageContent){
        ArrayList<User> speakers = userInfo.getSpeakers();
        for (User speaker: speakers){
            messageOneUser(speaker.getEmail(), messageContent);
        }
    }

    /**
     * Returns an arraylist containing all message history between this organizer and the user registered under the
     * email </email>.
     * @param email a String representing the email of the recipient
     * @return an arraylist containing all messages between this organizer and the user
     */

    public ArrayList<Message> viewMessages(String email){
        if (userInfo.canMessage(email)){
            if (conversationStorage.contains(organizerEmail, email)){
                ConversationManager c = conversationStorage.getConversationManager(organizerEmail, email);
                return c.getMessages();
            }
            else{
                ConversationManager c = conversationStorage.addConversationManager(organizerEmail, email);
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
            if (manager.getParticipants().contains(organizerEmail)) {
                ArrayList<String> participants = new ArrayList<>(manager.getParticipants());
                participants.remove(organizerEmail);
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
                    presenter.printMenu(1);
                    mainMenuController.runMainMenu(organizerEmail);
                } else if (option == 1) {
                    presenter.printMenu(2);
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
                    presenter.printMenu(3);
                    String body = scan.nextLine();
                    if (body.equals("0")) {
                        continue;
                    }

                    messageOneUser(email, body);
                    presenter.printMenu(4);
                } else if (option == 2) {
                    presenter.printMenu(3);
                    String body = scan.nextLine();
                    if (body.equals("0")) {
                        continue;
                    }
                    messageAllSpeakers(body);
                    presenter.printMenu(4);
                } else if (option == 3) {
                    presenter.printMenu(3);
                    String body = scan.nextLine();
                    if (body.equals("0")) {
                        continue;
                    }
                    messageAllAttendees(body);
                    presenter.printMenu(4);
                } else if (option == 4) {
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
