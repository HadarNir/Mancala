package Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MancalaServer {
    private boolean gameOver = false;
    private int player1Mancala;
    private int player2Mancala;
    private int player1Pits[];
    private int player2Pits[];
    private int zeroArray[];
    private int currentPlayer;
    private int currentPitMove;
    private ServerSocket server;
    private ExecutorService runGame;
    private Lock gameLock;
    private Condition otherPlayerConnected;
    private Condition otherPlayerTurn;
    private Player[] players;
    private final static int firstPlayer = 0;
    private final static int secondPlayer = 1;


    public MancalaServer() {
        startServer();
        players = new Player[2];
        currentPlayer = firstPlayer;
    }

    public void startServer() {
        // create ExecutorService with a thread for each player
        System.out.println("start running server...");
        runGame = Executors.newFixedThreadPool(2);
        gameLock = new ReentrantLock(); // create lock for game

        // condition variable for both players being connected
        otherPlayerConnected = gameLock.newCondition();

        // condition variable for the other player's turn
        otherPlayerTurn = gameLock.newCondition();
        try {
            server = new ServerSocket(12345, 2); // set up ServerSocket
        } // end try
        catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(1);
        } // end catch
    }

    // wait for two connections so game can be played
    public void execute() {
        initialBoard();
        // wait for each client to connect
        for (int i = 0; i < players.length; i++) {
            try // wait for connection, create Player, start runnable
            {
                players[i] = new Player(server.accept(), i);
                runGame.execute(players[i]); // execute player runnable
            } // end try
            catch (IOException ioException) {
                ioException.printStackTrace();
                System.exit(1);
            } // end catch
        } // end for

        gameLock.lock(); // lock game to signal player X's thread

        try {
            players[firstPlayer].setSuspended(false); // resume player X
            otherPlayerConnected.signal(); // wake up player X's thread
        } // end try
        finally {
            gameLock.unlock(); // unlock game after signalling player X
        } // end finally
    } // end method execute

    public int makeMove(int firstArray[], int secondArray[], int mancala) {
        int j, pit, stones;
        pit = this.currentPitMove;
        j = pit;
        if (pit > 0 && pit < 7) {
            stones = firstArray[pit - 1];
            firstArray[pit - 1] = 0;
            while (stones != 0) {
                if (j < 6) {
                    firstArray[j]++;
                    players[0].stoneMoved(pit - 1, j, 0);
                    players[1].stoneMoved(pit - 1, j, 0);
                } else if (j == 6) {
                    mancala++;
                    players[0].stoneMoved(pit - 1, 6, 0);
                    players[1].stoneMoved(pit - 1, 6, 0);
                } else if (j < 13) {
                    secondArray[j - 7]++;
                    players[0].stoneMoved(pit - 1, j - 7, 1);
                    players[1].stoneMoved(pit - 1, j - 7, 1);
                } else {
                    j = 0;
                    continue;
                }
                j++;
                stones--;
            }
            checkIfGameOver();
            if (j - 1 == 6 && !this.gameOver) {
                if (this.currentPlayer == 1)
                    currentPlayer--;
                else
                    currentPlayer++;
            } else if ((j - 1) < 6 && firstArray[j - 1] - 1 == 0) {
                int n = firstArray[j - 1];
                for (int i = 0; i < n; i++) {
                    mancala += 1;
                    firstArray[j - 1]--;
                    players[0].stoneMoved(j - 1, 6, 0);
                    players[1].stoneMoved(j - 1, 6, 0);
                }
                n = secondArray[5 - (j - 1)];
                for (int i = 0; i < n; i++) {
                    mancala += 1;
                    secondArray[5 - (j - 1)]--;
                    players[0].stoneMoved(5 - (j - 1), 6, 1);
                    players[1].stoneMoved(5 - (j - 1), 6, 1);
                }
            }
        }
        players[0].clearPit(pit);
        players[1].clearPit(pit);
        return mancala;
    }

    public boolean checkIfGameOver() {
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
        }
        if (player2BoardEmpty) {
            this.gameOver = true;
            this.player1Mancala += Arrays.stream(this.player1Pits).sum();
            this.player1Pits = zeroArray;
        }
        if (this.gameOver) {
            printBoard();
            System.out.println("\n");
            if (this.player1Mancala > this.player2Mancala)
                System.out.println("\n player 1 won the game \n");
            else if (this.player1Mancala < this.player2Mancala)
                System.out.println("\n player 2 won the game \n");
            else
                System.out.println("\n it's a tie \n");
        }
        return this.gameOver;
    } // ready for use

    public void initialBoard() { // initial the Mancala board
        this.currentPitMove = 0;
        this.zeroArray = new int[6];
        this.player1Pits = new int[6];
        this.player2Pits = new int[6];
        for (int i = 0; i < this.player2Pits.length; i++) {
            this.player1Pits[i] = 5;
            this.player2Pits[i] = 5;
        }
        this.player1Mancala = 0;
        this.player2Mancala = 0;
        printBoard();
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

    public boolean validateAndMove(int location, int player) {
        // while not current player, must wait for turn
        while (player != currentPlayer) {
            gameLock.lock(); // lock game to wait for other player to go

            try {
                otherPlayerTurn.await(); // wait for player's turn
            } // end try
            catch (InterruptedException exception) {
                exception.printStackTrace();
            } // end catch
            finally {
                gameLock.unlock(); // unlock game after waiting
            } // end finally
        } // end while

        // if location not occupied, make move
        currentPitMove = location;
        if (currentPlayer == 0) {
            player1Mancala = makeMove(player1Pits, player2Pits, player1Mancala);
            printBoard();
        } else {
            player2Mancala = makeMove(player2Pits, player1Pits, player1Mancala);
            printBoard();
        }
        currentPlayer = (currentPlayer + 1) % 2; // change player
        // let new current player know that move occurred
        players[currentPlayer].otherPlayerMoved();
        System.out.println("player number " + (this.currentPlayer + 1) + " turn");
        gameLock.lock(); // lock game to signal other player to go

        try {
            otherPlayerTurn.signal(); // signal other player to continue
        } // end try
        finally {
            gameLock.unlock(); // unlock game after signaling
        } // end finally

        return true; // notify player that move was valid
    }

    private class Player implements Runnable {
        private Socket connection; // connection to client
        private Scanner input; // input from client
        private Formatter output; // output to client
        private int playerNumber; // tracks which player this is
        private boolean suspended = true; // whether thread is suspended

        // set up Player thread
        public Player(Socket socket, int number) {
            playerNumber = number; // store this player's number
            connection = socket; // store socket for client

            try // obtain streams from Socket
            {
                input = new Scanner(connection.getInputStream());
                output = new Formatter(connection.getOutputStream());
            } // end try
            catch (IOException ioException) {
                ioException.printStackTrace();
                System.exit(1);
            } // end catch
        } // end Player constructor

        // send message that other player moved

        // control thread's execution
        public void run() {
            try {
                output.format("%d\n", playerNumber);
                output.flush();
                // if player X, wait for another player to arrive
                if (playerNumber == firstPlayer) {
                    System.out.println("first player connected");
                    gameLock.lock(); // lock game to  wait for second player

                    try {
                        while (suspended) {
                            otherPlayerConnected.await(); // wait for player O
                        } // end while
                    } // end try
                    catch (InterruptedException exception) {
                        exception.printStackTrace();
                    } // end catch
                    finally {
                        gameLock.unlock(); // unlock game after second player
                    } // end finally

                } // end if
                else {
                    System.out.println("second player connected");
                    System.out.println("player number " + (currentPlayer + 1) + " turn");
                } // end else

                // while game not over
                while (!checkIfGameOver()) {
                    int pit = 0; // initialize move location
                    if (input.hasNext()) {
                        pit = input.nextInt(); // get move location
                        validateAndMove(pit, playerNumber); //hadar checked till here
                    }
                } // end while
            } // end try
            finally {
                try {
                    connection.close(); // close connection to client
                } // end try
                catch (IOException ioException) {
                    ioException.printStackTrace();
                    System.exit(1);
                } // end catch
            } // end finally
        } // end method run

        public void otherPlayerMoved() {
            output.format("Opponent moved\n");
            output.flush(); // flush output
        } // end method otherPlayerMoved

        public void stoneMoved(int fromPit, int toPit, int relativeToPit) {
            output.format("Stone moved\n");
            output.format("%d\n", fromPit);
            output.format("%d\n", toPit);
            output.format("%d\n", currentPlayer);
            output.format("%d\n", relativeToPit);
            output.flush(); // flush output
        }

        public void clearPit(int pit) {
            output.format("clear pit\n");
            output.format("%d\n", pit);
            output.format("%d\n", currentPlayer);
            output.flush(); // flush output
        }

        // set whether or not thread is suspended
        public void setSuspended(boolean status) {
            suspended = status; // set value of suspended
        } // end method setSuspended
    } // end class Player

}