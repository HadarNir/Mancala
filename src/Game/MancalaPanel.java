package Game;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MancalaPanel extends JPanel {
    private int stoneAmount;
    private String playerName;
    ArrayList<JLabel> stones;

    public MancalaPanel(String playerMancala) {
        this.playerName = playerMancala;
        this.stones = new ArrayList<JLabel>();
    }

    public void paintComponent(Graphics g) {
        this.setLayout(null);
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.blue);
        g2.draw(new RoundRectangle2D.Double(25, 30, GameBoard.MANCALA_WIDTH, GameBoard.MANCALA_HEIGHT, 80, 150));
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        g2.drawString(this.stoneAmount + " stones", 50, 400);
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2.drawString(playerName, 50, 25);
    }

    public void addLabel(JLabel imageLabel) {
        this.stoneAmount++;
        Random r = new Random();
        int lowX = 75 - 20;
        int highX = 75 + 10;
        int lowY = 250 - 140;
        int highY = 250 + 80;
        int centerX = r.nextInt(highX - lowX) + lowX;
        int centerY = r.nextInt(highY - lowY) + lowY;
        imageLabel.setBounds(centerX, centerY, imageLabel.getPreferredSize().width, imageLabel.getPreferredSize().height);
        this.stones.add(imageLabel);
        this.add(imageLabel);
        this.repaint();
    }
}