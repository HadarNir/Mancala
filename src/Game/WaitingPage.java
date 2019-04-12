package Game;

import javax.swing.*;
import java.awt.*;

public class WaitingPage {
    private JFrame frame;
    public WaitingPage() {
        // creating a waiting page for the user which connected
        //create a frame
        frame = new JFrame();
        // Create a panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        // Create component for panel
        JLabel topic = new JLabel("<html>waiting for <br/>another opponent</html>");
        topic.setFont(new Font("Serif", Font.PLAIN, 30));
        panel.add(topic);
        frame.add(panel);
        frame.setSize(600, 600);
        frame.setTitle("Mancala");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void closeFrame(){
        // close the frame after another user has connected
        frame.dispose();
    }
}
