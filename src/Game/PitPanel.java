package Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class PitPanel extends JPanel {
    private int stoneAmount;
    private char pitSide;
    private int pitNumber;
    ArrayList<JLabel> stones;
    private String imageNames[];
    private boolean old = false;


    public PitPanel(char pitSide, int pitNumber) {
        this.pitSide = pitSide;
        this.pitNumber = pitNumber;
        this.stones = new ArrayList<>();
        this.imageNames = new String[5];
        imageNames[0] = "stones/blackStone.png";
        imageNames[1] = "stones/blueStone.png";
        imageNames[2] = "stones/greenStone.png";
        imageNames[3] = "stones/redStone.png";
        imageNames[4] = "stones/yellowStone.png";
    }

    public void paintComponent(Graphics g) {
        this.setLayout(null);
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.blue);
        g2.drawOval(75 - GameBoard.PIT_SIZE / 2, 85 - GameBoard.PIT_SIZE / 2, GameBoard.PIT_SIZE, GameBoard.PIT_SIZE);
        g2.setBackground(Color.white);
        g2.drawString(this.stoneAmount + "  pit " + (pitNumber + 1), 20, GameBoard.PIT_SIZE + 40);
        if (this.stones.isEmpty() && !old) {
            old = true;
            Random r = new Random();
            int lowX = 75 - 30;
            int highX = 75 + 30;
            int lowY = 85 - 30;
            int highY = 85 + 30;
            for (int j = 0; j < 5; j++) {
                int centerX = r.nextInt(highX - lowX) + lowX;
                int centerY = r.nextInt(highY - lowY) + lowY;
                ImageIcon image = new ImageIcon(imageNames[j]);
                JLabel imageLabel = new JLabel(image);
                imageLabel.setBounds(centerX, centerY, imageLabel.getPreferredSize().width, imageLabel.getPreferredSize().height);
                this.add(imageLabel);
                this.stones.add(imageLabel);
            }
        }
    }

    public void setStoneAmount(int stoneAmount) {
        this.stoneAmount = stoneAmount;
    }

    public ArrayList<JLabel> getStones() {
        if (!this.stones.isEmpty())
            return stones;
        else return null;
    }

    public JLabel lastStoneInserted() {
        this.stoneAmount--;
        JLabel imageLabel = this.stones.remove(this.stones.size() - 1);
        this.removeAll();
        this.repaint();
        return imageLabel;
    }

    public void addLabel(JLabel imageLabel) {
        this.stoneAmount++;
        this.stones.add(imageLabel);
        this.add(imageLabel);
        this.repaint();
    }
}