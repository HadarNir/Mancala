package Game;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MancalaClient extends JFrame implements Runnable {
    private boolean gameOver = false;
    private GameBoard board;
    private PitPanel pitPanelArrPlayer1[];
    private PitPanel pitPanelArrPlayer2[];
    private JFrame frame;
    private int turn;
    private int currentPitMove;
    private Socket connection;
    private Scanner input;
    private Formatter output;
    private String mancalaHost;
    private int myTurn;

    public MancalaClient(String host) {
        mancalaHost = host;
        initialBoard();
        addMouseClick();
        turn = 0;
    }

    public void startClient() {
        try // connect to server, get streams and start outputThread
        {
            // make connection to server
            connection = new Socket(
                    InetAddress.getByName(mancalaHost), 12345);

            // get streams for input and output
            input = new Scanner(connection.getInputStream());
            output = new Formatter(connection.getOutputStream());
        } // end try
        catch (IOException ioException) {
            ioException.printStackTrace();
        } // end catch

        // create and start worker thread for this client
        ExecutorService worker = Executors.newFixedThreadPool(1);
        worker.execute(this); // execute client
    } // end method startClient

    public void run() {
        if (input.hasNext())
            myTurn = input.nextInt(); // get player's number
        while (true) {
            if (input.hasNextLine())
                processMessage(input.nextLine());
        } // end while
    } // end method run

    private void processMessage(String message) {
        // valid move occurred
        if (message.equals("Valid move.")) {

        } // end if
        else if (message.equals("Invalid move, try again")) {

        } // end else if
        else if (message.equals("Opponent moved")) {
           turn = myTurn; // now this client's turn
        } // end else if
    } // end method processMessage


    public void sendPit(int location) {
        output.format("%d\n", location); // send location to server
        output.flush();
        if (turn == 1)
            turn = 0;
        else
            turn = 1;
    }

    public void addMouseClick() {
        this.frame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (!gameOver && myTurn == turn) {
                    int firstX = 225, firstY = 447;
                    if (myTurn == 0) {
                        for (int i = 0; i < pitPanelArrPlayer1.length; i++) {
                            if (Math.hypot(firstX - e.getX(), firstY - e.getY()) <= 65) {
                                currentPitMove = i + 1;
                                System.out.println(currentPitMove);
                                System.out.println(Math.hypot(firstX - e.getX(), firstY - e.getY()));
                            }
                            firstX += 150;
                        }
                        if (currentPitMove != 0 && pitPanelArrPlayer1[currentPitMove - 1].getStones() != null) {
                            sendPit(currentPitMove);
                        } else
                            System.out.println("illegal move");
                    } else if (myTurn == 1) {
                        firstY = 211;
                        firstX = 225;
                        for (int i = pitPanelArrPlayer2.length - 1; i >= 0; i--) {
                            if (Math.hypot(firstX - e.getX(), firstY - e.getY()) <= 65) {
                                currentPitMove = i + 1;
                                System.out.println(currentPitMove);
                                System.out.println(Math.hypot(firstX - e.getX(), firstY - e.getY()));
                            }
                            firstX += 150;
                        }
                        if (currentPitMove != 0 && pitPanelArrPlayer1[currentPitMove - 1].getStones() != null) {
                            sendPit(currentPitMove);
                        } else
                            System.out.println("illegal move");
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
        this.frame = this.board.getFrame();
        System.out.println("player number " + (this.turn + 1) + " turn");
    } //ready for use

}
