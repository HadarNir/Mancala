package Game;

import java.util.*;


public class StartGame {
    private boolean gameOver = false;
    private int player1Mancala = 0;
    private int player2Mancala = 0;
    private int player1Pits[];
    private int player2Pits[];
    Scanner in = new Scanner(System.in);

    public void StartGame() {
        initialBoard();
        printBoard();
        while (!this.gameOver) {
            this.player1Mancala = makeMove(this.player1Pits, this.player2Pits, this.player1Mancala);
            printBoard();
            int sum = Arrays.stream(this.player1Pits).sum() + Arrays.stream(this.player2Pits).sum() + this.player1Mancala + this.player2Mancala;
            System.out.println("\nthe sum is " + sum + "\n");
            checkIfGameOver();
            if (!this.gameOver) {
                this.player2Mancala = makeMove(this.player2Pits, this.player1Pits, this.player2Mancala);
                printBoard();
                sum = Arrays.stream(this.player1Pits).sum() + Arrays.stream(this.player2Pits).sum() + this.player1Mancala + this.player2Mancala;
                System.out.println("\nthe sum is " + sum + "\n");
                checkIfGameOver();
            }
        }
    }

    public void initialBoard() { // initial the Mancala board
        this.player1Pits = new int[6];
        this.player2Pits = new int[6];
        for (int i = 0; i < this.player2Pits.length; i++) {
            this.player1Pits[i] = 5;
            this.player2Pits[i] = 5;
        }
        player1Mancala = 0;
        player2Mancala = 0;
    } //done

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
    } //done

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
            if (j - 1 == 6) {
                printBoard();
                mancala = makeMove(firstArray, secondArray, mancala);
            }
            else if ((j-1) <= 6 && firstArray[j - 1] - 1 == 0) {
                firstArray[j - 1] += secondArray[5 - (j - 1)];
                secondArray[5 - (j - 1)] = 0;
            }
        } else {
            System.out.println("illegal move");
            mancala = makeMove(firstArray, secondArray, mancala);
        }
        return mancala;
    }

    public void checkIfGameOver() {

    }
}
