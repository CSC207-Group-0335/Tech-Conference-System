package MessagingPresenters;

import UserLogin.MainMenuController;

import java.util.Scanner;

public abstract class MessengerController {
    public String email;
    public Scanner scan;
    public MainMenuController mainMenuController;
    public MessageManager messageManager;

    public MessengerController(String email, Scanner scan, MainMenuController mainMenuController, MessageManager messageManager){
        this.email = email;
        this.scan = scan;
        this.mainMenuController = mainMenuController;
        this.messageManager = messageManager;
    }

    public abstract void run();
}
