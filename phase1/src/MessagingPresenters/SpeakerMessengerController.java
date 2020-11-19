package MessagingPresenters;

import UserLogin.Speaker;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * A class that represents a speaker message controller.
 */

public class SpeakerMessengerController implements Observer{
    public SpeakerMessageManager userInfo;
    private ConversationStorage conversationStorage;
    private Speaker speaker;
    private SpeakerMessengerControllerPresenter presenter;
    public Scanner scan;


    /**
     * A speaker is required to create an instance of this class.
     * @param speaker the speaker
     */

    public SpeakerMessengerController(Speaker speaker, Scanner scanner) {
        this.userInfo = new SpeakerMessageManager(speaker);
        this.presenter = new SpeakerMessengerControllerPresenter();
        this.scan = scanner;
    }

    /**
     * Sends a message containing </messageContent> to a user registered under the email </email>.
     * @param email a String representing the email of the recipient
     * @param messageContent a String representing the content of the message
     */

    private void message(String email, String messageContent){
        ConversationManager c = conversationStorage.getConversationManager(speaker.getEmail(), email);
        c.addMessage(email, speaker.getEmail(), LocalDateTime.now(), messageContent);
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
     * Returns a boolean representing whether or not a reply containing </messageContent> was sent.
     * @param email a String representing the email of the recipient
     * @param messageContent a String representing the content of the message
     * @return a boolean representing whether or not the reply was sent
     */

    public boolean reply(String email, String messageContent){
        if (userInfo.canReply(email)){
            ConversationManager c = conversationStorage.getConversationManager(speaker.getEmail(), email);
            c.addMessage(email, speaker.getEmail(), LocalDateTime.now(), messageContent);
            return true;
        }
        return false;
    }

    public void run() {
        boolean flag = true;
        while (flag) {
            presenter.printMenu(0);
            int option = Integer.parseInt(scan.nextLine());

            if (option == 0) {
                flag = false;
                presenter.printMenu(1);
            }
            else if (option == 1) {
                presenter.printMenu(2);
                String email = new String();
                boolean valid_recipient = false;
                while (!valid_recipient) {
                    email = scan.nextLine();
                    if (userInfo.canReply(email)) {
                        valid_recipient = true;
                    }
                    else { presenter.printMenu(5); }
                }
                presenter.printMenu(3);
                String body = scan.nextLine();

                message(email, body);
                presenter.printMenu(4);
            }
            else if (option == 2) {
                presenter.printMenu(3);
                String body = scan.nextLine();
                messageAllAttendees(body);
                presenter.printMenu(4);
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
