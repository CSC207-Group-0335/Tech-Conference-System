package UserLogin;

import java.util.Observable;
import java.util.Observer;

/**
 * A class that works with the MainMenuPresenter to display the main menu to the User, based on User type, and
 * allow the user to navigate to different "screens" based on input.
 */

public class MainMenuController implements Observer {
    private User user; //This user is gotten from LogInController

    /**
     * This method will run the Main Menu based on the type of the user that is provided.
     * @param user the user provided
     */
    public void runMainMenu(User user) {
        if (user instanceof Attendee) {

        }
    }


    @Override
    public void update(Observable o, Object arg) {
        this.user = (User) arg;
    }
}
