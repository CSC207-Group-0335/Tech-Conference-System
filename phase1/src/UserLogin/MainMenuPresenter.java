package UserLogin;

/**
 * A class that works with the MainMenuController to display the main menu to the User, based on User type, and
 * allow the user to navigate to different "screens" based on input.
 */

public class MainMenuPresenter {

    public void printMainMenuInfo() {
        System.out.println("Welcome to our Tech Conference. This is the main menu. Navigate to different " +
                "screens by typing: \"1 -> Schedules\", \"2 -> Messages\", \"0 -> Log Out\"");
    }

    public void printHello(User user){
        System.out.println("Hello, " + user.getName());
    }

    public void tryAgain(){
        System.out.println("Invalid command, please try again");
    }
}
