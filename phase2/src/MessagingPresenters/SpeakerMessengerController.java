package MessagingPresenters;

import Schedule.Event;
import Schedule.EventManager;
import UserLogin.MainMenuController;
import UserLogin.User;
import UserLogin.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class that represents a speaker message controller.
 */

public class SpeakerMessengerController extends MessengerController {
    private final SpeakerMessengerPresenter presenter;

    /**
     * A speaker is required to create an instance of this class.
     *
     * @param speakerEmail the speaker
     */

    public SpeakerMessengerController(String speakerEmail, Scanner scanner, MainMenuController mainMenuController,
                                      UserManager userManager, ConversationStorage conversationStorage,
                                      EventManager eventManager) {
        super(speakerEmail, scanner, mainMenuController, userManager, conversationStorage, eventManager);
        this.presenter = new SpeakerMessengerPresenter();
    }

    /**
     * Sends a message containing </messageContent> to all attendees.
     * @param messageContent a String representing the content of the message
     */

    public void messageAllAttendees(String messageContent){
        ((SpeakerMessageManager) messageManager).messageAllAttendees(messageContent);
    }

    public void messageAllAttendeesOfTalk(String messageContent, String talkID){
        ((SpeakerMessageManager) messageManager).messageAllAttendeesOfEvent(messageContent, talkID);
    }

    public ArrayList<Event> viewTalks(){
        return ((SpeakerMessageManager) messageManager).getSpeakerEvents();
    }

    /**
     * Runs the presenters.
     */

    public void run() {
        boolean flag = true;
        while (flag) {
            presenter.printMenu();
            int option = Integer.parseInt(scan.nextLine());
            try {
                if (option == 0) {
                    // QUIT
                    flag = false;
                    presenter.printQuitMessage();
                    mainMenuController.runMainMenu(email);
                }
                else if (option == 1) {
                    // VIEW INDIVIDUAL CHATS
                    ArrayList<String> emails = messageManager.getRecipients();
                    runIndividualChatMenu(presenter, emails);
                }
                else if (option == 2) {
                    //VIEW GROUP CHATS
                    ArrayList<String> talkIDS = getEventIDS();
                    runIndividualChatMenu(presenter, talkIDS);
                }
                else if (option == 3) {
                    // MESSAGE ONE USER
                    runMessageIndividualUserMenu(presenter);
                }
                else if (option == 4) {
                    // MESSAGE ALL ATTENDEES
                    presenter.askForMessageBody();
                    String body = scan.nextLine();
                    if (body.equals("0")) {
                        continue;
                    }
                    ((SpeakerMessageManager) messageManager).messageAllAttendees(body);
                    presenter.printMessageSentSuccess();
                }
                else if (option == 5) {
                    // MESSAGE ALL ATTENDEES OF A SINGLE TALK
                    ArrayList<Event> events = ((SpeakerMessageManager) messageManager).getSpeakerEvents();
                    presenter.viewEvents(events);
                    int index = Integer.parseInt(scan.nextLine());
                    if (index == 0 || events.size() == 0) {
                        continue;
                    }
                    Event event = events.get(index - 1);
                    ArrayList<User> emails = ((SpeakerMessageManager) messageManager).getAttendeesOfEvent(event);
                    presenter.askForMessageBody();
                    String body = scan.nextLine();
                    if (body.equals("0")) {
                        continue;
                    }
                    for (User user : emails) {
                        messageManager.message(user.getEmail(), body, true);
                    }
                    presenter.printMessageSentSuccess();
                }
            } catch (NumberFormatException nfe) {
                presenter.printInvalidOptionError();
            }
        }
    }
}

