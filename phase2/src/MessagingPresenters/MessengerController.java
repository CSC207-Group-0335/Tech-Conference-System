package MessagingPresenters;

import UserLogin.MainMenuController;
import UserLogin.UserStorage;

import java.util.Scanner;

/**
 * A class representing a MessengerController.
 */

public abstract class MessengerController {
    public String email;
    public Scanner scan;
    public MainMenuController mainMenuController;
    public UserStorage userStorage;
    public ConversationStorage conversationStorage;

    /**
     * An email, Scanner object, MainMenuController object, UserStorage, and ConversationStorage are required to
     * create an instance of MessengerController.
     */

    public MessengerController(String email, Scanner scan, MainMenuController mainMenuController,
                               UserStorage userStorage, ConversationStorage conversationStorage) {
        this.email = email;
        this.scan = scan;
        this.mainMenuController = mainMenuController;
        this.userStorage = userStorage;
        this.conversationStorage = conversationStorage;
    }

    public abstract void run();
}
