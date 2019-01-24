package Game;

import java.util.*;


public class StartGame {
    private boolean gameOver = false;
    private int player;
    private int player1Mancala = 0;
    private int player2Mancala = 0;
    private int player1Pits[];
    private int player2Pits[];
    Scanner in = new Scanner(System.in);

    public void StartGame() {
        initialBoard();
        printBoard();
        while (!this.gameOver) {
            makeMove(this.player, this.player1Pits, this.player2Pits);
            this.player++;
            printBoard();
            checkIfGameOver();
            if (!this.gameOver) {
                makeMove(this.player, this.player2Pits, this.player1Pits);
                this.player--;
                printBoard();
                checkIfGameOver();
            }
        }
    }

    public void initialBoard() { // initial the Mancala board
        this.player1Pits = new int[6];
        this.player2Pits = new int[6];
        this.player = 1;
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

    public void makeMove(int player, int firstArray[], int secondArray[]) {
        int j = 0, pit, firstMancala, secondMancala;
        System.out.println("enter number of pit from 1 to 6");
        pit = in.nextInt();
        if (player == 1) {
            firstMancala = this.player1Mancala;
            secondMancala = this.player2Mancala;
        } else {
            firstMancala = this.player2Mancala;
            secondMancala = this.player1Mancala;
        }
        if (pit > 0 && pit < 7 && firstArray[pit - 1] != 0) {
            int stones = firstArray[pit - 1];
            firstArray[pit - 1] = 0;
            for (int i = pit; i < stones + pit; i++) {
                if (i < 6)
                    firstArray[i]++;
                else if (i == 6)
                    firstMancala++;
                else if (i < 13)
                    secondArray[i - 7]++;
                else {
                    secondMancala++;
                    pit = pit - (i + 1);
                    i = 0;
                }
                j = i;
            }
            if (j == 6) {
                printBoard();
                makeMove(player, firstArray, secondArray);
            } else if (j < 6)
                if (firstArray[j] - 1 == 0) {
                    firstArray[j] += secondArray[5 - j];
                    secondArray[5 - j] = 0;
                }
        } else {
            System.out.println("illegal move");
            makeMove(player, firstArray, secondArray);
        }
        if (player == 1) {
            this.player1Mancala = firstMancala;
            this.player2Mancala = secondMancala;
        } else {
            this.player2Mancala = firstMancala;
            this.player1Mancala = secondMancala;
        }
    }

    public void checkIfGameOver() {


    }
}
