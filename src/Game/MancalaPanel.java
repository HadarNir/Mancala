package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MancalaPanel extends JPanel{
    private int stoneAmount;
    private char playerMancala;

    public MancalaPanel(char playerMancala) {
        this.playerMancala = playerMancala;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.blue);
        g2.draw(new RoundRectangle2D.Double(25, 0, GameBoard.MANCALA_WIDTH, GameBoard.MANCALA_HEIGHT, 80, 150));
        g2.setColor(Color.black);
        g2.setColor(Color.BLACK);
        g2.drawString("Player " + Character.toUpperCase(playerMancala), 50, GameBoard.MANCALA_HEIGHT + 20);
    }
}