package Game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PitPanel extends JPanel {
    private int stoneAmount;
    private char pitSide;
    private int pitNumber;


    public PitPanel(char pitSide, int pitNumber) {
        this.pitSide = pitSide;
        this.pitNumber = pitNumber;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.blue);
        g2.draw(new RoundRectangle2D.Double(5, 5, GameBoard.PIT_SIZE, GameBoard.PIT_SIZE, 150, 150));
        g2.setColor(Color.black);
        g2.drawString(this.stoneAmount + "  pit " + (pitNumber + 1), 10, GameBoard.PIT_SIZE + 20);
    }

    public int getStoneAmount() {
        return stoneAmount;
    }

    public void setStoneAmount(int stoneAmount) {
        this.stoneAmount = stoneAmount;
        this.repaint();
    }
}