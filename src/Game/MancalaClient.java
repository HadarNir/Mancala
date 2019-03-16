package Game;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MancalaClient extends JFrame {
    private boolean gameOver = false;
    private GameBoard board;
    private PitPanel pitPanelArrPlayer1[];
    private PitPanel pitPanelArrPlayer2[];
    private JFrame frame;
    private int turn;
    private int currentPitMove;

    public MancalaClient() {
        initialBoard();

    }

    public void startClient(){

    }

    public void addMouseClick(){
        this.frame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int firstX = 225, firstY = 447;
                if (!gameOver) {
                    if (turn == 1) {
                        for (int i = 0; i < pitPanelArrPlayer1.length; i++) {
                            if (Math.hypot(firstX - e.getX(), firstY - e.getY()) <= 65) {
                                currentPitMove = i + 1;
                                System.out.println(Math.hypot(firstX - e.getX(), firstY - e.getY()));
                            }
                            firstX += 150;
                        }
                        if (currentPitMove != 0) {

                            turn++;
                        }
                        System.out.println("player number " + turn + " turn");
                    } else if (turn == 2) {
                        firstY = 211;
                        firstX = 225;
                        for (int i = pitPanelArrPlayer2.length - 1; i >= 0; i--) {
                            if (Math.hypot(firstX - e.getX(), firstY - e.getY()) <= 65) {
                                currentPitMove = i + 1;
                                System.out.println(Math.hypot(firstX - e.getX(), firstY - e.getY()));
                            }
                            firstX += 150;
                        }
                        if (currentPitMove != 0) {

                            turn--;
                        }
                        System.out.println("player number " + turn + " turn");
                    }
                    currentPitMove = 0;
                }
            }
        });
    }

    public void initialBoard() { // initial the Mancala board
        this.board = new GameBoard();
        this.currentPitMove = 0;
        this.pitPanelArrPlayer1 = board.getA();
        this.pitPanelArrPlayer2 = board.getB();
        this.turn = 1;
        this.frame = this.board.getFrame();
        System.out.println("player number " + this.turn + " turn");
    } //ready for use

}
