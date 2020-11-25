package MessagingPresenters;

import Schedule.Talk;
import UserLogin.MainMenuController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * A class that represents a speaker message controller.
 */

public class SpeakerMessengerController extends MessengerController{
    public SpeakerMessageManager userInfo;
    public String email;
    private SpeakerMessengerPresenter presenter;
    public Scanner scan;
    public MainMenuController mainMenuController;


    /**
     * A speaker is required to create an instance of this class.
     *
     * @param speakerEmail the speaker
     */

    public SpeakerMessengerController(String speakerEmail, Scanner scanner, MainMenuController mainMenuController) {
        super();
        this.userInfo = new SpeakerMessageManager(speakerEmail);
        this.presenter = new SpeakerMessengerPresenter();
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
                    mainMenuController.runMainMenu(email);
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

                    userInfo.message(email, body);
                    presenter.printMenu(4);
                } else if (option == 2) {
                    presenter.printMenu(3);
                    String body = scan.nextLine();
                    if (body.equals("0")) {
                        continue;
                    }
                    userInfo.messageAllAttendees(body);
                    presenter.printMenu(4);
                } else if (option == 3) {
                    ArrayList<String> emails = userInfo.getRecipients();
                    presenter.viewChats(emails);
                    int index = Integer.parseInt(scan.nextLine());
                    if (index == 0 || emails.size() == 0) {
                        continue;
                    }
                    String email = emails.get(index - 1);
                    ArrayList<Message> messages = userInfo.viewMessages(email);
                    presenter.viewConversation(messages);
                } else if (option == 4) {
                    ArrayList<Talk> talks = userInfo.getSpeakerTalks();
                    presenter.viewTalks(talks);
                    int index = Integer.parseInt(scan.nextLine());
                    if (index == 0 || talks.size() == 0) {
                        continue;
                    }
                    Talk talk = talks.get(index - 1);
                    ArrayList<String> emails = userInfo.getAttendeesOfTalk(talk);
                    presenter.printMenu(3);
                    String body = scan.nextLine();
                    if (body.equals("0")) {
                        continue;
                    }
                    for (String email : emails) {
                        userInfo.message(email, body);
                    }
                    presenter.printMenu(4);
                }
            } catch (NumberFormatException nfe) {
                presenter.printMenu(6);
            }
        }
    }
}

