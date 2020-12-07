package MessagingPresenters;

public abstract class MessengerPresenter {
    public void printMenu() {
        printGoBack();
        System.out.println("Press 1 to view all of your individual conversations" + System.lineSeparator() +
                "Press 2 to view all of your group chats" + System.lineSeparator());
        printMessagesMenu();
    }

    public void printGoBack() {
        System.out.println("Press 0 to go back" + System.lineSeparator());
    }

    public abstract void printMessagesMenu();

    public void askForEmail() {System.out.println("Please enter the recipient's email address or 0 to go back:");}

    public void askForMessageBody() {System.out.println("Do not use ';'. Please enter the content of your text or 0 to go back:");}

    public void printMessageSentSuccess() {System.out.println("Message successfully sent!");}

    public void printQuitMessage() {System.out.println("Quitting messenger client");}

    public void printMessageError() {System.out.println("Email address not found or access denied. Please try again or press 0 to go back.");}

    public void printInvalidOptionError() {System.out.println("Invalid option. Try again.");}
}
