package UserLogin;

import javax.swing.*;

/**
 * A Demo class used to run the entire program.
 */

public class Demo {

    public static void main(String[] args) {
        JFrame f = new JFrame();
        JButton b = new JButton("click");
        b.setBounds(130,100,100, 40);
        f.add(b);
        f.setSize(400, 500);
        f.setLayout(null);
        f.setVisible(true);

        TechConferenceSystem tCS = new TechConferenceSystem();
        tCS.run();
    }
}
