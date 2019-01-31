package Game;

import javax.swing.*;
import java.awt.*;

public class GameBoard {
    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;
    public static final int MANCALA_WIDTH = 100;
    public static final int MANCALA_HEIGHT = 400;
    public static final int PIT_WIDTH = 60;
    public static final int PIT_HEIGHT = 145;

    public GameBoard() {
        JFrame frame = new JFrame();
        JPanel top = new JPanel();
        //Initializing PitPanels and MancalaPanels and attaching it to the model
        MancalaPanel mancalaA = new MancalaPanel('a');
        PitPanel[] a = new PitPanel[6];
        for (int i = 0; i < 6; i++) {
            a[i] = new PitPanel('a', i);
        }

        MancalaPanel mancalaB = new MancalaPanel('b');
        PitPanel[] b = new PitPanel[6];
        for (int i = 0; i < 6; i++) {
            b[i] = new PitPanel('b', i);
        }

        //Group the PitPanels together to form two rows
        JPanel middlePits = new JPanel();
        GridLayout middleLayout = new GridLayout(2, 6);
        middleLayout.setHgap(17);
        middlePits.setLayout(middleLayout);
        for (int i = 5; i >= 0; i--)
            middlePits.add(b[i]);
        for (int i = 0; i < 6; i++)
            middlePits.add(a[i]);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        top.setPreferredSize(new Dimension(800, 100));
        mainPanel.add(top, BorderLayout.PAGE_START);
        mancalaB.setPreferredSize(new Dimension(150, 500));
        mainPanel.add(mancalaB, BorderLayout.LINE_START);
        middlePits.setPreferredSize(new Dimension(500, 500));
        mainPanel.add(middlePits, BorderLayout.CENTER);
        mancalaA.setPreferredSize(new Dimension(150, 500));
        mainPanel.add(mancalaA, BorderLayout.LINE_END);

        frame.add(mainPanel);

        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setTitle("Mancala");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}