package UserLogin;

import MessagingPresenters.LoginPresenter;
import com.sun.prism.shader.Solid_ImagePattern_Loader;

public class LogInPresenter {

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
