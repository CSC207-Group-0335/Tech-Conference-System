package UserLogin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogIn {
    private JButton logInButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel loginPanel;
    private LogInInterface logInController;

    public LogIn() {
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = usernameField.getText();
                String password = passwordField.getText();
                logInController.loginButtonClicked(email, password);
            }
        });
    }

    public void setListener(LogInInterface listener) {
        this.logInController = listener;
    }

    public JPanel getPanel() {
        return loginPanel;
    }
}
