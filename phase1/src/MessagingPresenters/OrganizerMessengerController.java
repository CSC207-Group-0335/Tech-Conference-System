package MessagingPresenters;

import UserLogin.Organizer;
import UserLogin.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;


/**
 * A class that represents an organizer message controller.
 */

public class OrganizerMessengerController implements Observer {
    private Organizer organizer;
    public CanMessageManager userInfo;
    private ConversationStorage conversationStorage;
    private OrgMessengerControllerPresenter presenter;
    public Scanner scan;

    /**
     * An organizer is required to create an instance of this class.
     * @param organizer the organizer
     */

    public OrganizerMessengerController(Organizer organizer, Scanner scanner) {
        this.userInfo = new CanMessageManager(organizer);
        this.presenter = new OrgMessengerControllerPresenter();
        this.scan = scanner;
        this.organizer = organizer;
    }

    /**
     * Sends a message containing </messageContent> to a user registered under the email </email>.
     * @param email a String representing the email of the recipient
     * @param messageContent a String representing the content of the message
     */

    public void messageOneUser(String email, String messageContent){
        if (userInfo.canMessage(email)){
            if (conversationStorage.contains(organizer.getEmail(), email)){
                ConversationManager c = conversationStorage.getConversationManager(organizer.getEmail(), email);
                c.addMessage(email, organizer.getEmail(), LocalDateTime.now(), messageContent);
            }
            else{
                ConversationManager c = conversationStorage.addConversationManager(organizer.getEmail(), email);
                c.addMessage(email, organizer.getEmail(), LocalDateTime.now(), messageContent);
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
            if (conversationStorage.contains(organizer.getEmail(), email)){
                ConversationManager c = conversationStorage.getConversationManager(organizer.getEmail(), email);
                return c.getMessages();
            }
            else{
                ConversationManager c = conversationStorage.addConversationManager(organizer.getEmail(), email);
                return c.getMessages();
            }
        }
        return null;
    }

    public ArrayList<String> getRecipients() {
        ArrayList<String> emails = new ArrayList<>();
        ArrayList<ConversationManager> managers = conversationStorage.getConversationManagers();
        for (ConversationManager manager: managers) {
            if (manager.getParticipants().contains(organizer.getEmail())) {
                ArrayList<String> participants = new ArrayList<>(manager.getParticipants());
                participants.remove(organizer.getEmail());
                emails.add(participants.get(0));
            }
        }
        return emails;
    }

    public void run() {
        boolean flag = true;
        OUTER_LOOP:
        while (flag) {
            presenter.printMenu(0);
            int option = Integer.parseInt(scan.nextLine());

            if (option == 0) {
                flag = false;
                presenter.printMenu(1);
                //THIS SHOULD RETURN THE USER TO THE MAIN MENU - NOTE NOV 18
            }
            else if (option == 1) {
                presenter.printMenu(2);
                String email = new String();
                boolean valid_recipient = false;
                while (!valid_recipient) {
                    email = scan.nextLine();
                    if (email.equals("0")) { continue OUTER_LOOP; }
                    if (userInfo.canMessage(email)) {
                        valid_recipient = true;
                    }
                    else { presenter.printMenu(5); }
                }
                presenter.printMenu(3);
                String body = scan.nextLine();
                if (body.equals("0")) { continue; }

                messageOneUser(email, body);
                presenter.printMenu(4);
            }
            else if (option == 2) {
                presenter.printMenu(3);
                String body = scan.nextLine();
                if (body.equals("0")) { continue; }
                messageAllSpeakers(body);
                presenter.printMenu(4);
            }
            else if (option == 3) {
                presenter.printMenu(3);
                String body = scan.nextLine();
                if (body.equals("0")) { continue; }
                messageAllAttendees(body);
                presenter.printMenu(4);
            }
            else if (option == 4) {
                ArrayList<String> emails = getRecipients();
                presenter.viewChats(emails);
                if (emails.size() == 0) { continue; }
                int index = Integer.parseInt(scan.nextLine());
                String email = emails.get(index - 1);
                ArrayList<Message> messages = viewMessages(email);
                presenter.viewConversation(messages);
            }
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
