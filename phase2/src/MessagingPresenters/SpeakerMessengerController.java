package MessagingPresenters;

import Schedule.Event;
import UserLogin.MainMenuController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * A class that represents a speaker message controller.
 */

public class SpeakerMessengerController implements Observer{
    public SpeakerMessageManager userInfo;
    private ConversationStorage conversationStorage;
    private String  speakerEmail;
    private SpeakerMessengerControllerPresenter presenter;
    public Scanner scan;
    public MainMenuController mainMenuController;


    /**
     * A speaker is required to create an instance of this class.
     * @param speakerEmail the speaker
     */

    public SpeakerMessengerController(String speakerEmail, Scanner scanner, MainMenuController mainMenuController) {
        this.userInfo = new SpeakerMessageManager(speakerEmail);
        this.presenter = new SpeakerMessengerControllerPresenter();
        this.speakerEmail = speakerEmail;
        this.scan = scanner;
        this.mainMenuController = mainMenuController;
    }

    /**
     * Sends a message containing </messageContent> to a user registered under the email </email>.
     * @param email a String representing the email of the recipient
     * @param messageContent a String representing the content of the message
     */

    private void message(String email, String messageContent){
        if (conversationStorage.contains(speakerEmail, email)){
            ConversationManager c = conversationStorage.getConversationManager(speakerEmail, email);
            c.addMessage(email, speakerEmail, LocalDateTime.now(), messageContent);
        }
        else{
            ConversationManager c = conversationStorage.addConversationManager(speakerEmail, email);
            c.addMessage(email, speakerEmail, LocalDateTime.now(), messageContent);
        }

    }


    /**
     * Sends a message containing </messageContent> to all attendees.
     * @param messageContent a String representing the content of the message
     */

    public void messageAllAttendees(String messageContent){
        for (String email: userInfo.getAllAttendees()){
            message(email, messageContent);
        }
    }

    /**
     * Returns a list containing all recipients.
     * @return an ArrayList containing all recipients
     */

    public ArrayList<String> getRecipients() {
        ArrayList<String> emails = new ArrayList<>();
        ArrayList<ConversationManager> managers = conversationStorage.getConversationManagers();
        for (ConversationManager manager: managers) {
            if (manager.getParticipants().contains(speakerEmail)){
                ArrayList<String> participants = new ArrayList<>(manager.getParticipants());
                participants.remove(speakerEmail);
                emails.add(participants.get(0));
            }
        }
        return emails;
    }

    /**
     * Returns a list of messages between this speaker and the user with email </email>.
     * @param email a String representing an email
     * @return an ArrayList containing messages
     */

    public ArrayList<Message> viewMessages(String email){
        if (conversationStorage.contains(speakerEmail, email)){
            ConversationManager c = conversationStorage.getConversationManager(speakerEmail, email);
            return c.getMessages();
            }
        return null;
    }

    /**
     * Runs the presenters.
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
                    mainMenuController.runMainMenu(speakerEmail);
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

                    message(email, body);
                    presenter.printMenu(4);
                } else if (option == 2) {
                    presenter.printMenu(3);
                    String body = scan.nextLine();
                    if (body.equals("0")) {
                        continue;
                    }
                    messageAllAttendees(body);
                    presenter.printMenu(4);
                } else if (option == 3) {
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
                else if (option == 4){
                    ArrayList<Event> events = userInfo.getSpeakerTalks();
                    presenter.viewTalks(events);
                    int index = Integer.parseInt(scan.nextLine());
                    if (index == 0 || events.size() == 0) {
                        continue;
                    }
                    Event event = events.get(index - 1);
                    ArrayList<String> emails = userInfo.getAttendeesOfTalk(event);
                    presenter.printMenu(3);
                    String body = scan.nextLine();
                    if (body.equals("0")) {
                        continue;
                    }
                    for (String email: emails){
                        message(email, body);
                    }
                    presenter.printMenu(4);
                }
            } catch (NumberFormatException nfe) {
                presenter.printMenu(6); }
        }
    }

    /**
     * Updates </conversationStorage> if and only if </arg> is an instance of ConversationStorage.
     * @param o an observable parameter
     * @param arg an Object
     */

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ConversationStorage) {
            this.conversationStorage = (ConversationStorage) arg;
        }
    }
}
