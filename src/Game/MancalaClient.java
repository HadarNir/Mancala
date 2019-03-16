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
    private String turn;
    private int currentPitMove;
    private Socket connection;
    private Scanner input;
    private Formatter output;
    private String mancalaHost;
    private String myTurn;

    public MancalaClient(String host) {
        mancalaHost = host;
        initialBoard();
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
        turn = input.nextLine(); // get player's mark (X or O)
    } // end method run

    public void sendPit(int location) {
        // if it is my turn
        if (turn.equals(myTurn)) {
            output.format("%d\n", location); // send location to server
            output.flush();
            if(turn.equals("0"))
                turn = "1";
            else
                turn = "0";
        } // end if
    }

    public void addMouseClick() {
        this.frame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (!gameOver) {
                    int firstX = 225, firstY = 447;
                    if (myTurn.equals("0")) {
                        for (int i = 0; i < pitPanelArrPlayer1.length; i++) {
                            if (Math.hypot(firstX - e.getX(), firstY - e.getY()) <= 65) {
                                currentPitMove = i + 1;
                                System.out.println(Math.hypot(firstX - e.getX(), firstY - e.getY()));
                            }
                            firstX += 150;
                        }
                        if (currentPitMove != 0 && pitPanelArrPlayer1[currentPitMove - 1].getStones() != null) {
                            sendPit(currentPitMove);
                        } else
                            System.out.println("illegal move");
                        System.out.println("player number " + turn + " turn");
                    } else if (myTurn.equals("1")) {
                        firstY = 211;
                        firstX = 225;
                        for (int i = pitPanelArrPlayer2.length - 1; i >= 0; i--) {
                            if (Math.hypot(firstX - e.getX(), firstY - e.getY()) <= 65) {
                                currentPitMove = i + 1;
                                System.out.println(Math.hypot(firstX - e.getX(), firstY - e.getY()));
                            }
                            firstX += 150;
                        }
                        if (currentPitMove != 0 && pitPanelArrPlayer1[currentPitMove - 1].getStones() != null) {
                            sendPit(currentPitMove);
                        } else
                            System.out.println("illegal move");
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
        this.turn = "0";
        this.frame = this.board.getFrame();
        System.out.println("player number " + this.turn + " turn");
    } //ready for use

}
