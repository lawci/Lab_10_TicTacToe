import java.util.Scanner;

public class TicTacToe {
    // Class level variables
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static String[][] board = new String[ROWS][COLS];
    private static int moveCount = 0;
    private static String currentPlayer = "X";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean playAgain;
        String firstPlayer = "X";  // Track who should start first in the next game

        do {
            // Initialize the game
            clearBoard();
            moveCount = 0;
            currentPlayer = firstPlayer;  // Set starting player for this game
            boolean gameOver = false;

            // Game loop
            while (!gameOver) {
                display();

                // Get player move
                int row, col;
                do {
                    System.out.println("Player " + currentPlayer + "'s turn");
                    row = SafeInput.getRangedInt(scanner, "Enter row (1-3): ", 1, 3);
                    col = SafeInput.getRangedInt(scanner, "Enter column (1-3): ", 1, 3);

                    // Convert to array indices
                    int arrayRow = row - 1;
                    int arrayCol = col - 1;

                    if (!isValidMove(arrayRow, arrayCol)) {
                        System.out.println("That space is already taken. Try again.");
                    } else {
                        // Record the move
                        board[arrayRow][arrayCol] = currentPlayer;
                        moveCount++;
                        break;
                    }
                } while (true);

                // Check for win or tie
                if (isWin(currentPlayer)) {
                    display();
                    System.out.println("Player " + currentPlayer + " wins!");
                    gameOver = true;
                } else if (isTie()) {
                    display();
                    System.out.println("It's a tie!");
                    gameOver = true;
                } else {
                    // Switch players for next turn
                    currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";
                }
            }

            // Ask to play again
            playAgain = SafeInput.getYNConfirm(scanner, "Would you like to play again? (Y/N)");

            // Toggle who starts first for the next game
            firstPlayer = (firstPlayer.equals("X")) ? "O" : "X";
        } while (playAgain);

        System.out.println("Thanks for playing!");
        scanner.close();
    }

    // Helper methods
    private static void clearBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = " ";
            }
        }
    }

    private static void display() {
        System.out.println();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(" " + board[i][j] + " ");
                if (j < COLS - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i < ROWS - 1) {
                System.out.println("-----------");
            }
        }
        System.out.println();
    }

    private static boolean isValidMove(int row, int col) {
        return board[row][col].equals(" ");
    }

    private static boolean isWin(String player) {
        return isRowWin(player) || isColWin(player) || isDiagonalWin(player);
    }

    private static boolean isRowWin(String player) {
        for (int i = 0; i < ROWS; i++) {
            if (board[i][0].equals(player) && board[i][1].equals(player) && board[i][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isColWin(String player) {
        for (int j = 0; j < COLS; j++) {
            if (board[0][j].equals(player) && board[1][j].equals(player) && board[2][j].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDiagonalWin(String player) {
        // Check top-left to bottom-right
        if (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) {
            return true;
        }
        // Check top-right to bottom-left
        return board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player);
    }

    private static boolean isTie() {
        // If all spaces are filled, it's a tie
        if (moveCount >= ROWS * COLS) {
            return true;
        }

        // Check if all possible win vectors are blocked
        boolean xHasWin = isWin("X");
        boolean oHasWin = isWin("O");

        // If both players have potential wins (which shouldn't happen in normal play)
        // or if all possible win vectors are blocked by having both X and O
        return xHasWin && oHasWin;
    }
}