package UserLogin;

/**
 * A class that works with the MainMenuController to display the main menu to the User, based on User type, and
 * allow the user to navigate to different "screens" based on input.
 */

public class MainMenuPresenter {

    /**
     * A method used to display the main menu information and options to the user.
     */

    public void printMainMenuInfo() {
        System.out.println("Welcome to our Tech Conference. This is the main menu. Navigate to different " +
                "screens by typing: \"1 -> Schedules\", \"2 -> Messages\", \"0 -> Log Out\"");
    }

    /**
     * A method to display a personal greeting to the User.
     * @param user the User that has logged in to the system.
     */

    public void printHello(String user){
        System.out.println("Hello, " + user);
    }

    /**
     * A method to ask the user to input something else when there has been an input error.
     */

    public void tryAgain(){
        System.out.println("Invalid command, please try again");
    }

    /**
     * A method used to let the User know that logging out was successful.
     */

    public void loggingOut() { System.out.println("Log Out Successful. Goodbye!"); }
}
