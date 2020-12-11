package MessagingPresenters;

import java.util.ArrayList;

public abstract class MessengerPresenter {
    public void printMenu() {
        printWelcomeMessage();
        printGoBack();
        System.out.println("Press 1 to view all of your individual conversations" + System.lineSeparator() +
                "Press 2 to view all of your group chats");
        printMessagesMenu();
    }

    public void printGoBack() {
        System.out.println("Press 0 to go back");
    }

    /**
     * Abstract welcome message
     */
    public abstract void printWelcomeMessage();

    /**
     * Abstract print messages menu.
     */
    public abstract void printMessagesMenu();

    /**
     * Prints Asks for email
     */
    public void askForEmail() {System.out.println("Please enter the address to send to or 0 to go back:");}

    /**
     * Prints Tells users to type message.
     */
    public void askForMessageBody() {System.out.println("Do not use ';'. Please enter the content of your text or 0 to go back:");}

    /**
     * Prints Tells user the message was sent.
     */
    public void printMessageSentSuccess() {System.out.println("Message successfully sent!" + System.lineSeparator());}

    /**
     * Prints Quit message.
     */
    public void printQuitMessage() {System.out.println("Quitting messenger client" + System.lineSeparator());}

    /**
     * Prints Send message error.
     */
    public void printSendMessageError() {System.out.println("Email address not found or access denied. Please try again or press 0 to go back." + System.lineSeparator());}
    /**
     * Prints InvalidOptionError.
     */
    public void printInvalidOptionError() {System.out.println("Invalid option. Try again." + System.lineSeparator());}

    /**
     * Prints successful deletion .
     */
    public void printSuccessfulDeletion() {System.out.println("Message deleted." + System.lineSeparator());}

    /**
     * Prints the emails this user has messaged or has been messaged by
     *
     * @param emails an ArrayList containing Strings representing emails
     */
    /*8
    Method that prints the chats available for the user to select.
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

    public void viewConversation(ArrayList<String> messages, Boolean viewingArchivedMessages) {
        if (viewingArchivedMessages) {
            System.out.println("Viewing archived messages. Press 'a' to view unarchived messages");
        }
        else {
            System.out.println("Viewing unarchived messages. Enter 'a' to view archived messages");
        }
        if (messages.size() == 0) {
            System.out.println("Nothing to see here!");
        } else {
            int i = 1;
            for (String m : messages) {
                System.out.println(m);
                i++;
            }
            System.out.println("Enter the number corresponding to a message for additional options.");
        }
        System.out.println("Press 0 to go back.");
    }

    /**
     * Method that prints the viewmessagemenu.
     * @param message String.
     * @param isArchived Boolean.
     * @param isRead Boolean.
     */
    public void viewMessageMenu(String message, Boolean isArchived, Boolean isRead) {
        System.out.println(message + System.lineSeparator() +
                "Press 1 to delete");
        if (isRead) {
            System.out.println("Press 2 to mark as unread");
        }
        else {
            System.out.println("Press 2 to mark as read");
        }
        if (isArchived) {
            System.out.println("Press 3 to unarchive");
        }
        else {
            System.out.println("Press 3 to archive");
        }
        printGoBack();
    }

    /**
     * Method that prunts group chat view.
     * @param eventIDs String eventIDs.
     */
    public void viewGroupChats(ArrayList<String> eventIDs) {
        if (eventIDs.size() == 0) {
            System.out.println("No chats found");
        }
        else {
            int i = 1;
            for (String eventID : eventIDs) {
                System.out.println(i + "-" + eventID);
                i++;
            }
            System.out.println("Input the number corresponding to the email address with the conversation you wish " +
                    "to view or 0 to go back:");
        }
    }

    /**
     * Method that prints groupchat view.
     * @param messages ArrayList of Strings.
     */
    public void viewGroupChat(ArrayList<String> messages){
        if (messages.size() == 0) {
            System.out.println("Nothing to see here!");
        } else {
            for (String message : messages) {
                System.out.println(message);
            }
        }
        System.out.println(System.lineSeparator() + "Press 0 to go back.");
    }
}
