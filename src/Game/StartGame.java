package Game;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class StartGame extends JFrame {
    private boolean gameOver = false;
    private int player1Mancala;
    private int player2Mancala;
    private int player1Pits[];
    private int player2Pits[];
    private int zeroArray[];
    private GameBoard board;
    private PitPanel pitPanelArrPlayer1[];
    private PitPanel pitPanelArrPlayer2[];
    private JFrame frame;
    private int turn;
    private int currentPitMove;

    public StartGame() {
        initialBoard();
        frame.addMouseListener(new MouseAdapter() {
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
                            player1Mancala = makeMove(player1Pits, player2Pits, player1Mancala, pitPanelArrPlayer1, pitPanelArrPlayer2, board.getMancalaA());
                            printBoard();
                            updateBoard();
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
                            player2Mancala = makeMove(player2Pits, player1Pits, player2Mancala, pitPanelArrPlayer2, pitPanelArrPlayer1, board.getMancalaB());
                            printBoard();
                            updateBoard();
                            turn--;
                        }
                        System.out.println("player number " + turn + " turn");
                    }
                    checkIfGameOver();
                    currentPitMove = 0;
                }
            }
        });
    } //ready for use

    public void initialBoard() { // initial the Mancala board
        this.board = new GameBoard();
        this.currentPitMove = 0;
        this.pitPanelArrPlayer1 = board.getA();
        this.pitPanelArrPlayer2 = board.getB();
        this.zeroArray = new int[6];
        this.player1Pits = new int[6];
        this.player2Pits = new int[6];
        this.turn = 1;
        for (int i = 0; i < this.player2Pits.length; i++) {
            this.player1Pits[i] = 5;
            this.player2Pits[i] = 5;
        }
        this.player1Mancala = 0;
        this.player2Mancala = 0;
        this.frame = this.board.getFrame();
        printBoard();
        updateBoard();
        System.out.println("player number " + this.turn + " turn");
    } //ready for use

    public void printBoard() {
        System.out.print(player2Mancala);
        System.out.print('[');
        for (int i = this.player2Pits.length - 1; i > -1; i--) {
            System.out.print(this.player2Pits[i]);
            if (i != 0)
                System.out.print(", ");
        }
        System.out.println(']');
        System.out.print(Arrays.toString(this.player1Pits));
        System.out.println(player1Mancala);
        System.out.println("\n");
    } //ready for use

    public int makeMove(int firstArray[], int secondArray[], int mancala, PitPanel pitPanelFirstArray[], PitPanel pitPanelSecondArray[], MancalaPanel mancalaP) {
        JLabel imageLabel;
        int j, pit, stones;
        pit = this.currentPitMove;
        j = pit;
        if (pit > 0 && pit < 7 && firstArray[pit - 1] != 0) {
            stones = firstArray[pit - 1];
            firstArray[pit - 1] = 0;
            while (stones != 0) {
                imageLabel = pitPanelFirstArray[pit - 1].lastStoneInserted();
                if (j < 6) {
                    firstArray[j]++;
                    pitPanelFirstArray[j].addLabel(imageLabel);
                } else if (j == 6) {
                    mancala++;
                    mancalaP.addLabel(imageLabel);
                } else if (j < 13) {
                    secondArray[j - 7]++;
                    pitPanelSecondArray[j - 7].addLabel(imageLabel);
                } else {
                    pitPanelFirstArray[pit - 1].addLabel(imageLabel);
                    j = 0;
                    continue;
                }
                j++;
                stones--;
            }
            checkIfGameOver();
            if (j - 1 == 6 && !this.gameOver) {
                if (this.turn == 1)
                    turn--;
                else
                    turn++;
            } else if ((j - 1) < 6 && firstArray[j - 1] - 1 == 0) {
                mancala += firstArray[j - 1] + secondArray[5 - (j - 1)];
                firstArray[j - 1] = 0;
                secondArray[5 - (j - 1)] = 0;
                while (pitPanelFirstArray[j - 1].getStones() != null) {
                    mancalaP.addLabel(pitPanelFirstArray[j - 1].lastStoneInserted());
                    pitPanelFirstArray[j - 1].removeAll();
                    pitPanelFirstArray[j - 1].repaint();
                }
                while (pitPanelSecondArray[5 - (j - 1)].getStones() != null) {
                    mancalaP.addLabel(pitPanelSecondArray[5 - (j - 1)].lastStoneInserted());
                    pitPanelSecondArray[5 - (j - 1)].removeAll();
                    pitPanelSecondArray[5 - (j - 1)].repaint();
                }
            }
        } else {
            System.out.println("illegal move");
            if (this.turn == 1)
                turn--;
            else
                turn++;
        }

        pitPanelFirstArray[pit - 1].

                removeAll();

        pitPanelFirstArray[pit - 1].

                repaint();
        return mancala;
    } //ready for use

    public void checkIfGameOver() {
        boolean player1BoardEmpty = true, player2BoardEmpty = true;
        for (int i = 0; i < this.player1Pits.length; i++) {
            if (this.player1Pits[i] != 0)
                player1BoardEmpty = false;
            if (this.player2Pits[i] != 0)
                player2BoardEmpty = false;
        }
        if (player1BoardEmpty) {
            this.gameOver = true;
            this.player2Mancala += Arrays.stream(this.player2Pits).sum();
            this.player2Pits = zeroArray;
            for (int i = 0; i < this.player2Pits.length; i++) {
                while (pitPanelArrPlayer2[i].getStones() != null) {
                    board.getMancalaA().addLabel(pitPanelArrPlayer2[i].lastStoneInserted());
                    pitPanelArrPlayer2[i].removeAll();
                    pitPanelArrPlayer2[i].repaint();
                }
            }
        }
        if (player2BoardEmpty) {
            this.gameOver = true;
            this.player1Mancala += Arrays.stream(this.player1Pits).sum();
            this.player1Pits = zeroArray;
            for (int i = 0; i < this.player1Pits.length; i++) {
                while (pitPanelArrPlayer1[i].getStones() != null) {
                    board.getMancalaB().addLabel(pitPanelArrPlayer1[i].lastStoneInserted());
                    pitPanelArrPlayer1[i].removeAll();
                    pitPanelArrPlayer1[i].repaint();
                }
            }
        }
        if (this.gameOver) {
            printBoard();
            System.out.println("\n");
            updateBoard();
            if (this.player1Mancala > this.player2Mancala)
                System.out.println("\n player 1 won the game \n");
            else
                System.out.println("\n player 2 won the game \n");
        }
    } // ready for use

    public void updateBoard() {
        for (int i = 0; i < this.player1Pits.length; i++) {
            this.pitPanelArrPlayer1[i].setStoneAmount(this.player1Pits[i]);
            this.pitPanelArrPlayer2[i].setStoneAmount(this.player2Pits[i]);
        }
        this.board.SetStonesAmountInMancalaAPanel(this.player1Mancala);
        this.board.SetStonesAmountInMancalaBPanel(this.player2Mancala);
    } // ready for use
}
