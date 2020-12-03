package UserLogin;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * A Demo class used to run the entire program.
 */

public class Demo extends Application {

    public static void main(String[] args) {
        //launch(args);
        TechConferenceSystem tCS = new TechConferenceSystem();
        tCS.run();
    }

    @Override
    public void start(Stage primaryStage) {
        Scene logInScene = new LogInView().display();
        primaryStage.setScene(logInScene);
        primaryStage.setTitle("Log In");
        primaryStage.show();
    }
}
