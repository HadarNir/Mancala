package Game;

import javax.swing.*;
import java.awt.*;

public class GameBoard {
    public static final int DEFAULT_WIDTH = 1200;
    public static final int DEFAULT_HEIGHT = 600;
    public static final int MANCALA_WIDTH = 100;
    public static final int MANCALA_HEIGHT = 400;
    public static final int PIT_SIZE = 130;
    private MancalaPanel mancalaA;
    private PitPanel[] a;
    private MancalaPanel mancalaB;
    private PitPanel[] b;
    private JFrame frame;

    public GameBoard() {
        frame = new JFrame();
        JPanel top = new JPanel();
        //Initializing PitPanels and MancalaPanels and attaching it to the model
        mancalaA = new MancalaPanel('1');
        a = new PitPanel[6];
        for (int i = 0; i < 6; i++) {
            a[i] = new PitPanel('1', i);
        }

        mancalaB = new MancalaPanel('2');
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

    public PitPanel[] getA() {
        return this.a;
    }

    public PitPanel[] getB() {
        return this.b;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void SetStonesAmountInMancalaAPanel(int stones) {
        this.mancalaA.setStoneAmount(stones);
    }

    public void SetStonesAmountInMancalaBPanel(int stones) {
        this.mancalaB.setStoneAmount(stones);
    }

    public MancalaPanel getMancalaA() {
        return mancalaA;
    }

    public MancalaPanel getMancalaB() {
        return mancalaB;
    }

}