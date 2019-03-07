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
        g2.drawOval(75 - GameBoard.PIT_SIZE/2, 85 - GameBoard.PIT_SIZE/2, GameBoard.PIT_SIZE,GameBoard.PIT_SIZE);
        g2.setBackground(Color.white);
        g2.drawString(this.stoneAmount + "  pit " + (pitNumber + 1), 20, GameBoard.PIT_SIZE+ 40);
    }

    public void setStoneAmount(int stoneAmount) {
        this.stoneAmount = stoneAmount;
        this.repaint();
    }
}