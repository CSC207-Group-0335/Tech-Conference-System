package MessagingPresenters;

import java.util.ArrayList;

/**
 * A Presenter class that handles messages sent and received by an attendee.
 */

public class AttendeeMessengerPresenter extends MessengerPresenter {

    /**
     * Prints the messaging menu.
     */

    public void p(int i) {
        switch (i) {
            case 0:
                System.out.println("\nWelcome to the attendee messenger client" + System.lineSeparator() +
                        "Press 0 to quit the attendee messenger client" + System.lineSeparator() +
                        "Press 1 to send a message to a user" + System.lineSeparator() +
                        "Press 2 to view all of your conversations" + System.lineSeparator());
                break;
            case 1:
                System.out.println();
                break;
            case 2:
                System.out.println("Do not use ';'. Please enter the content of your text or 0 to go back:");
                break;
            case 3:
                System.out.println("Message successfully sent!");
                break;
            case 4:
                System.out.println("Quitting the attendee messenger client\n");
                break;
            case 5:
                System.out.println("Email address not found or access denied. Please try again or press 0 to go back.");
            case 6:
                System.out.println("Invalid option. Try again.");
        }
    }

    @Override
    public void printMessagesMenu() {
        System.out.println("Press 1 to send a message to a user" + System.lineSeparator());
    }

    /**
     * Prints the emails this user has messaged or has been messaged by
     *
     * @param emails an ArrayList containing Strings representing emails
     */

    public void viewChats(ArrayList<String> emails) {
        if (emails.size() == 0) {
            System.out.println("No chats found");
        } else {
            int i = 1;
            for (String email : emails) {
                System.out.println(i + " - " + email);
                i++;
            }
            System.out.println("Input the number corresponding to the email address with the conversation you wish " +
                    "to view or 0 to go back:");
        }
    }

    /**
     * Prints a conversation.
     *
     * @param messages an ArrayList containing messages sent to or by this user.
     */

    public void viewConversation(ArrayList<Message> messages) {
        for (Message message : messages) {
            System.out.println(message.getSenderEmail() + ": " + message.getMessageContent());
        }
    }
}