package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage {

    public MainPage(MancalaClient m) {
        //create a frame
        JFrame frame = new JFrame();
        // Create a panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        // Create component for panel
        JLabel topic = new JLabel("<html>Welcome To <br/>Mancala Online</html>");
        topic.setFont(new Font("Serif", Font.PLAIN, 30));
        JLabel id = new JLabel("please enter your name");
        id.setFont(new Font("Serif", Font.PLAIN, 20));
        JTextField nameToSend = new JTextField();
        JButton presentButton = new JButton("play");
        // Add components to panel
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 0.1;
        c.weighty = 0.1;

        c.gridx = 0;
        c.gridy = 0;
        panel.add(topic, c);


        c.gridx = 0;
        c.gridy = 1;
        panel.add(id, c);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(nameToSend, c);


        c.gridx = 0;
        c.gridy = 2;
        panel.add(presentButton, c);
        c.gridx = 1;
        c.gridy = 2;
        // Add panel to frame
        frame.add(panel);
        frame.setSize(400, 600);
        frame.setTitle("Mancala");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Create anonymous classes
        presentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m.setPlayerName(nameToSend.getText());
                frame.dispose();
                WaitingPage wp = new WaitingPage();
                m.startClient(wp);
            }
        });
    }
}