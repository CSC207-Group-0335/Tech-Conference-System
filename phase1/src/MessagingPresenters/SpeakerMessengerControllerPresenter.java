package MessagingPresenters;

import java.util.ArrayList;

/**
 * A Presenter class that handles messages sent and received by a speaker.
 */

public class SpeakerMessengerControllerPresenter{
    public void printMenu(int i) {
        switch (i) {
            case 0:
                System.out.println("\nWelcome to the speaker messenger client" + System.lineSeparator() +
                        "Press 0 to quit the speaker messenger client" + System.lineSeparator() +
                        "Press 1 to message one user" + System.lineSeparator() +
                        "Press 2 to message all attendees" + System.lineSeparator() +
                        "Press 3 to view all of your conversations");
                break;
            case 1:
                System.out.println("Quitting the speaker messenger client\n");
                break;
            case 2:
                System.out.println("Please enter the recipient's email address or 0 to go back::");
                break;
            case 3:
                System.out.println("Please enter the content of your text or 0 to go back::");
                break;
            case 4:
                System.out.println("Message successfully sent!");
                break;
            case 5:
                System.out.println("Email address not found or access denied. Please try again or press 0 to go back.");
            case 6:
                System.out.println("Invalid option. Try again.");
        }
    }

    public void viewChats(ArrayList<String> emails) {
        if (emails.size() == 0) {
            System.out.println("No chats found");
        }
        else {
            int i = 1;
            for (String email : emails) {
                System.out.println(i + " - " + email);
                i++;
            }
            System.out.println("Input the number corresponding to the email address with the conversation you wish to view:");
        }
    }

    public void viewConversation(ArrayList<Message> messages) {
        for (Message message: messages) {
            System.out.println(message.getSenderEmail() + ": " + message.getMessageContent());
        }
    }
}