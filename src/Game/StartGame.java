package Game;

import java.util.*;

public class StartGame {
    private boolean gameOver = false;
    private int player1Mancala = 0;
    private int player2Mancala = 0;
    private int player1Pits[];
    private int player2Pits[];
    int zeroArray[]= new int[6];
    Scanner in = new Scanner(System.in);

    public void StartGame() {
        initialBoard();
        printBoard();
        GameBoard gui = new GameBoard();
        while (!this.gameOver) {
            this.player1Mancala = makeMove(this.player1Pits, this.player2Pits, this.player1Mancala);
            printBoard();
            checkIfGameOver();
            if (!this.gameOver) {
                this.player2Mancala = makeMove(this.player2Pits, this.player1Pits, this.player2Mancala);
                printBoard();
                checkIfGameOver();
            }
        }
        System.out.println("\n");
        printBoard();
        if(this.player1Mancala > this.player2Mancala)
            System.out.println("\n player 1 won the game \n");
        else
            System.out.println("\n player 2 won the game \n");
    } //ready for use

    public void initialBoard() { // initial the Mancala board
        this.player1Pits = new int[6];
        this.player2Pits = new int[6];
        for (int i = 0; i < this.player2Pits.length; i++) {
            this.player1Pits[i] = 5;
            this.player2Pits[i] = 5;
        }
        player1Mancala = 0;
        player2Mancala = 0;
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
    } //ready for use

    public int makeMove(int firstArray[], int secondArray[], int mancala) {
        int j, pit, stones;
        System.out.println("enter number of pit from 1 to 6");
        pit = in.nextInt();
        j = pit;
        if (pit > 0 && pit < 7 && firstArray[pit - 1] != 0) {
            stones = firstArray[pit - 1];
            firstArray[pit - 1] = 0;
            while (stones != 0) {
                if (j < 6)
                    firstArray[j]++;
                else if (j == 6)
                    mancala++;
                else if (j < 13)
                    secondArray[j - 7]++;
                else {
                    j = 0;
                    continue;
                }
                j++;
                stones--;
            }
            if (j - 1 == 6 && !this.gameOver) {
                printBoard();
                mancala = makeMove(firstArray, secondArray, mancala);
            } else if ((j - 1) <= 6 && firstArray[j - 1] - 1 == 0) {
                mancala += firstArray[j - 1] + secondArray[5 - (j - 1)];
                firstArray[j - 1] = 0;
                secondArray[5 - (j - 1)] = 0;
            }
        } else {
            System.out.println("illegal move");
            mancala = makeMove(firstArray, secondArray, mancala);
        }
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
        }
        if (player2BoardEmpty) {
            this.gameOver = true;
            this.player2Mancala += Arrays.stream(this.player2Pits).sum();
            this.player1Pits = zeroArray;
        }
    } // ready for use
}