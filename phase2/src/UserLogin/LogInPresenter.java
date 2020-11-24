package UserLogin;

/**
 * A Presenter Class used to prompt the user for login credentials
 */

public class LogInPresenter {

    /**
     * contains all of the prompts required to obtain the login credentials for the user.
     * @param i takes in an int value to determine which prompt to present to the user.
     */

    public void printLoginInfo(int i) {
        switch (i) {
            case 1: {
                System.out.println("Enter your email: ");
                break;
            }
            case 2: {
                System.out.println("Enter your password: ");
                break;
            }
            case 3: {
                System.out.println("Login Successful");
                break;
            }
            case 4: {
                System.out.println("Email and password combination not found, try again.");
                break;
            }
        }
    }
}
