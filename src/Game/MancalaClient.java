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
    private String myName;
    private String firstPlayerName;
    private String secondPlayerName;
    private WaitingPage wp;

    public MancalaClient(String host) {
        mancalaHost = host;
        MainPage m = new MainPage(this);
        turn = 0;
    }

    public void setPlayerName(String name) {
        this.myName = name;
    }

    public void startClient(WaitingPage wp) {
        this.wp = wp;
        try // connect to server, get streams and start outputThread
        {
            // make connection to server
            connection = new Socket(
                    InetAddress.getByName(mancalaHost), 12346);

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
        output.format("%s\n", this.myName);
        output.flush();
        if (input.hasNext()) {
            myTurn = input.nextInt(); // get player's number
            input.nextLine();
        }
        while (true) {
            if (input.hasNextLine())
                processMessage(input.nextLine());
        } // end while
    } // end method run

    private void processMessage(String message) {
        // valid move occurred
        // end if
        if (message.equals("Stone moved")) {
            int fromPit = input.nextInt();
            input.nextLine();
            int toPit = input.nextInt();
            input.nextLine();
            int currentPlayer = input.nextInt();
            input.nextLine();
            int relativeToPit = input.nextInt();
            input.nextLine();
            System.out.println("from pit " + fromPit + " to pit " + toPit + " curr player " + currentPlayer + " relative pit " + relativeToPit);
            moveStone(fromPit, toPit, currentPlayer, relativeToPit);
        } // end else if
        else if (message.equals("Opponent moved")) {
            turn = myTurn; // now this client's turn
        } // end else if
        else if (message.equals("first name is")) {
            firstPlayerName = input.nextLine();
            secondPlayerName = this.myName;
        } else if (message.equals("second name is")) {
            secondPlayerName = input.nextLine();
            firstPlayerName = this.myName;
        }
        boolean init = false;
        if (!init && firstPlayerName != null && secondPlayerName != null) {
            init = true;
            initialBoard();
            addMouseClick();
            wp.closeFrame();
        }
    } // end method processMessage

    public void moveStone(int fromPit, int toPit, int fromPanel, int toPanel) {
        if (fromPanel == 0) {
            if (toPit == 6) {
                if (toPanel == 0) {
                    board.getMancalaA().addLabel(pitPanelArrPlayer1[fromPit].lastStoneInserted());
                } else if (toPanel == 1) {
                    board.getMancalaA().addLabel(pitPanelArrPlayer2[fromPit].lastStoneInserted());
                }
            } else if (toPanel == 0) {
                pitPanelArrPlayer1[toPit].addLabel(pitPanelArrPlayer1[fromPit].lastStoneInserted());
            } else if (toPanel == 1) {
                pitPanelArrPlayer2[toPit].addLabel(pitPanelArrPlayer1[fromPit].lastStoneInserted());
            }
        } else if (fromPanel == 1) {
            if (toPit == 6) {
                if (toPanel == 0) {
                    board.getMancalaB().addLabel(pitPanelArrPlayer2[fromPit].lastStoneInserted());
                } else if (toPanel == 1) {
                    board.getMancalaB().addLabel(pitPanelArrPlayer1[fromPit].lastStoneInserted());
                }
            } else if (toPanel == 0) {
                pitPanelArrPlayer2[toPit].addLabel(pitPanelArrPlayer2[fromPit].lastStoneInserted());
            } else if (toPanel == 1) {
                pitPanelArrPlayer1[toPit].addLabel(pitPanelArrPlayer2[fromPit].lastStoneInserted());
            }
        }
    }


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
                        if (currentPitMove != 0 && pitPanelArrPlayer2[currentPitMove - 1].getStones() != null) {
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
        this.board = new GameBoard(this.firstPlayerName, this.secondPlayerName);
        this.currentPitMove = 0;
        this.pitPanelArrPlayer1 = board.getA();
        this.pitPanelArrPlayer2 = board.getB();
        for (int i = 0; i < this.pitPanelArrPlayer1.length; i++) {
            this.pitPanelArrPlayer1[i].setStoneAmount(5);
            this.pitPanelArrPlayer2[i].setStoneAmount(5);
        }
        this.frame = this.board.getFrame();
    } //ready for use

}
