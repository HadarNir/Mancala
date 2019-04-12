package Game;

import javax.swing.*;
import java.awt.*;

public class GameBoard {
    public static final int DEFAULT_WIDTH = 1200;
    public static final int DEFAULT_HEIGHT = 600;
    public static final int MANCALA_WIDTH = 100;
    public static final int MANCALA_HEIGHT = 350;
    public static final int PIT_SIZE = 130;
    private MancalaPanel mancalaA;
    private PitPanel[] a;
    private MancalaPanel mancalaB;
    private PitPanel[] b;
    private JFrame frame;
    private JLabel nowTurn;

    public GameBoard(String first, String second) {
        frame = new JFrame();
        JPanel top = new JPanel();
        nowTurn = new JLabel();
        nowTurn.setFont(new Font("Serif", Font.PLAIN, 20));
        top.add(nowTurn);
        //Initializing PitPanels and MancalaPanels and attaching it to the model
        mancalaA = new MancalaPanel(first);
        a = new PitPanel[6];
        for (int i = 0; i < 6; i++) {
            a[i] = new PitPanel('1', i);
        }

        mancalaB = new MancalaPanel(second);
        b = new PitPanel[6];
        for (int i = 0; i < 6; i++) {
            b[i] = new PitPanel('2', i);
        }

        //Group the PitPanels together to form two rows
        JPanel middlePits = new JPanel();
        GridLayout middleLayout = new GridLayout(2, 6);
        middlePits.setLayout(middleLayout);
        for (int i = 5; i >= 0; i--)
            middlePits.add(b[i]);
        for (int i = 0; i < 6; i++)
            middlePits.add(a[i]);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        top.setPreferredSize(new Dimension(1200, 100));
        mainPanel.add(top, BorderLayout.PAGE_START);
        mancalaB.setPreferredSize(new Dimension(150, 500));
        mainPanel.add(mancalaB, BorderLayout.LINE_START);
        middlePits.setPreferredSize(new Dimension(900, 500));
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

    public void setName(String n){ //setting  the name of the current player who is playing
        this.nowTurn.setText("now it's " + n + " turn");
    }

    public void setOver(String n){ //setting the name of the winner
        this.nowTurn.setText(n);
    }

    public PitPanel[] getA() { //return first pit panel
        return this.a;
    }

    public PitPanel[] getB() { //return second pit panel
        return this.b;
    }

    public JFrame getFrame() { //return the frame of the game
        return frame;
    }

    public MancalaPanel getMancalaA() { //return first mancala
        return mancalaA;
    }

    public MancalaPanel getMancalaB() { //return second mancala
        return mancalaB;
    }

}