package MessagingPresenters;

import java.util.ArrayList;

/**
 * A Presenter class that handles messages sent and received by an attendee.
 */

public class AttendeeMessengerControllerPresenter{
    public void printMenu(int i) {
        switch (i) {
            case 0:
                System.out.println("Welcome to the attendee messenger client" + System.lineSeparator() +
                        "Press 1 to send a message to a user" + System.lineSeparator() +
                        "Press 2 to view all of your conversations");
                break;
            case 1:
                System.out.println("Please enter the recipient's email address:");
                break;
            case 2:
                System.out.println("Please enter the content of your text:");
                break;
            case 3:
                System.out.println("Message successfully sent!");
        }
    }

    public void viewChats(ArrayList<String> emails) {
        int i = 1;
        for (String email: emails) {
            System.out.println(i + " - " + email);
            i++;
        }
        System.out.println("Input the number corresponding to the email address with the conversation you wish to view:");
    }

    public void viewConversation(ArrayList<Message> messages) {
        for (Message message: messages) {
            System.out.println(message.getSenderEmail() + ": " + message.getMessageContent());
        }
    }
}