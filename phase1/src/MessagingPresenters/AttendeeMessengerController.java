package MessagingPresenters;

import Schedule.OrgScheduleController;
import Schedule.SpeakerScheduleController;
import Schedule.UserScheduleController;
import UserLogin.Attendee;
import UserLogin.User;

import java.lang.reflect.Array;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * A class that represents the messenger controller.
 */

public class AttendeeMessengerController implements Observer{ //NOTE, MADE NOT ABSTRACT NOV 14 EARLY MORNING - NATHAN
    private Attendee attendee;
    public CanMessageManager userInfo;
    private ConversationStorage conversationStorage;
    private AttendeeMessengerControllerPresenter presenter;

    /**
     * A user is required to create an instance of this class.
     * @param attendee the attendee
     */

    public AttendeeMessengerController(Attendee attendee) {
        this.attendee = attendee;
        this.userInfo = new CanMessageManager(attendee);
        presenter = new AttendeeMessengerControllerPresenter();
    }

    /**
     * Sends a message containing </messageContent> to a user registered under the email </email> if and only if this
     * attendee is allowed to message that user.
     * @param email a String representing the email of the recipient
     * @param messageContent a String representing the content of the message
     */

    public void message(String email, String messageContent){
        if (userInfo.canMessage(email)){
            if (conversationStorage.contains(attendee.getEmail(), email)){
                ConversationManager c = conversationStorage.getConversationManager(attendee.getEmail(), email);
                c.addMessage(email, attendee.getEmail(), LocalDateTime.now(), messageContent);
            }
            else{
                ConversationManager c = conversationStorage.addConversationManager(attendee.getEmail(), email);
                c.addMessage(email, attendee.getEmail(), LocalDateTime.now(), messageContent);
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
            if (conversationStorage.contains(attendee.getEmail(), email)){
                ConversationManager c = conversationStorage.getConversationManager(attendee.getEmail(), email);
                return c.getMessages();
            }
            else{
                ConversationManager c = conversationStorage.addConversationManager(attendee.getEmail(), email);
                return c.getMessages();
            }
        }
        return null;
    }

    public ArrayList<String> getRecipients() {
        ArrayList<String> emails = new ArrayList<>();
        ArrayList<ConversationManager> managers = conversationStorage.getConversationManagers();
        for (ConversationManager manager: managers) {
            ArrayList<String> participants = manager.getParticipants();
            participants.remove(attendee.getEmail());
            emails.add(participants.get(0));
        }
        return emails;
    }

    public void run() {
        boolean flag = true;
        Scanner scan = new Scanner(System.in);
        while (flag) {
            presenter.printMenu(0);
            int option = scan.nextInt();

            if (option == 1) {
                presenter.printMenu(1);
                String email = scan.nextLine();
                presenter.printMenu(2);
                String body = scan.nextLine();

                message(email, body);
            }
            else if (option == 2) {
                ArrayList<String> emails = getRecipients();
                presenter.viewChats(emails);
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
