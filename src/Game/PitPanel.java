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
    }

    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    /**
     * Controller to do a turn when clicking on pit.
     */
    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }
}