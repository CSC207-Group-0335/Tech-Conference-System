package MessagingPresenters;

import Schedule.Event;
import Schedule.EventManager;
import UserLogin.MainMenuController;
import UserLogin.User;
import UserLogin.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
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

    public void messageAllAttendeesOfEvent(String messageContent, String eventID){
        ((SpeakerMessageManager) messageManager).messageAllAttendeesOfEvent(messageContent, eventID);
    }

    public ArrayList<Event> viewEvents(){
        return ((SpeakerMessageManager) messageManager).getSpeakerEvents();
    }

    /**
     * Runs the presenters.
     */

    public void run() {
        boolean flag = true;
        OUTER_LOOP:
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
                    presenter.viewChats(emails);
                    int index = Integer.parseInt(scan.nextLine());
                    if (index == 0 || emails.size() == 0) {
                        continue;
                    }
                    String email = emails.get(index - 1);
                    Boolean viewingArchivedMessages = false;
                    char input = 'a';
                    while (input != '0') {
                        ArrayList<Message> messages;
                        if (viewingArchivedMessages) {
                            messages = viewArchivedMessages(email);
                        }
                        else {
                            messages = viewUnarchivedMessages(email);
                        }
                        HashMap<String, String> messageMap = new HashMap<>();
                        for (Message message: messages) {
                            messageMap.put(message.getSenderEmail(), message.getMessageContent());
                        }
                        presenter.viewConversation(messageMap, viewingArchivedMessages);
                        input = scan.nextLine().toCharArray()[0];
                        if (input == 'a') {
                            viewingArchivedMessages = !viewingArchivedMessages;
                        }
                    }
                }
                else if (option == 2) {
                    //VIEW GROUP CHATS
                    ArrayList<String> eventIDs = getEventIDS();
                    presenter.viewGroupChats(eventIDs);
                    int index = Integer.parseInt(scan.nextLine());
                    if (index == 0 || eventIDs.size() == 0) {
                        continue;
                    }
                    String groupChatID = eventIDs.get(index - 1);
                    char input = 'a';
                    while (input != '0'){
                        ArrayList<String> messages = getGroupChatMessages(groupChatID);
                        presenter.viewGroupChat(messages);
                        input = scan.nextLine().toCharArray()[0];
                    }
                }
                else if (option == 3) {
                    // MESSAGE ONE USER
                    presenter.askForEmail();
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
                            presenter.printSendMessageError();
                        }
                    }

                    presenter.askForMessageBody();
                    String body = scan.nextLine();

                    messageManager.message(email, body);
                    presenter.printMessageSentSuccess();
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
                    // MESSAGE ALL ATTENDEES OF A SINGLE EVENT
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
                        messageManager.message(user.getEmail(), body);
                    }
                    presenter.printMessageSentSuccess();
                }
            } catch (NumberFormatException nfe) {
                presenter.printInvalidOptionError();
            }
        }
    }
}

