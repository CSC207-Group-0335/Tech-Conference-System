package MessagingPresenters;

import UserLogin.MainMenuController;

import java.util.Scanner;

public abstract class MessengerController {
    public String email;
    public Scanner scan;
    public MainMenuController mainMenuController;

    public MessengerController(String email, Scanner scan, MainMenuController mainMenuController) {
        this.email = email;
        this.scan = scan;
        this.mainMenuController = mainMenuController;
    }

    public abstract void run();
}
