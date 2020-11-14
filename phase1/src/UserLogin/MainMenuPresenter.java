package UserLogin;

/**
 * A class that works with the MainMenuController to display the main menu to the User, based on User type, and
 * allow the user to navigate to different "screens" based on input.
 */

public class MainMenuPresenter {

    public void printMainMenuInfo() {
        System.out.println("Welcome to our Tech Conference. This is the main menu. Navigate to different " +
                "screens by typing: \n\"1 -> Schedules\" \n\"2 -> Messages\""); //\n equals new line
    }
}
