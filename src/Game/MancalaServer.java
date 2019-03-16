package Game;

import java.util.Arrays;

public class MancalaServer{
    private boolean gameOver = false;
    private int player1Mancala;
    private int player2Mancala;
    private int player1Pits[];
    private int player2Pits[];
    private int zeroArray[];
    private int turn;
    private int currentPitMove;

    public MancalaServer() {

    }

    public void initialBoard() { // initial the Mancala board
        this.currentPitMove = 0;
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
        printBoard();
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

    public int makeMove(int firstArray[], int secondArray[], int mancala) {
        int j, pit, stones;
        pit = this.currentPitMove;
        j = pit;
        if (pit > 0 && pit < 7 && firstArray[pit - 1] != 0) {
            stones = firstArray[pit - 1];
            firstArray[pit - 1] = 0;
            while (stones != 0) {
                if (j < 6) {
                    firstArray[j]++;
                } else if (j == 6) {
                    mancala++;
                } else if (j < 13) {
                    secondArray[j - 7]++;
                } else {
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
            }
        } else {
            System.out.println("illegal move");
            if (this.turn == 1)
                turn--;
            else
                turn++;
        }
        return mancala;
    }

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
            else
                System.out.println("\n player 2 won the game \n");
        }
    } // ready for use

}