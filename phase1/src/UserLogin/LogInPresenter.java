package UserLogin;

import MessagingPresenters.LoginPresenter;
import com.sun.prism.shader.Solid_ImagePattern_Loader;

public class LogInPresenter {

    public void printLoginInfo(int i) {
        if (i == 1) {
            System.out.println("Enter your email: ");
        } else if (i == 2) {
            System.out.println("Enter your password: ");
        } else if (i == 3) {
            System.out.println("Login Successful");
        } else if (i == 4) {
            System.out.println("Email and password combination not found, try again.");
        }
    }
}
