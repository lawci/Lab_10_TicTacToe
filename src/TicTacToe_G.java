import java.util.Scanner;

public class TicTacToe_G {

    // --- Class-Level Declarations ---
    // Constants for the board dimensions [cite: 37]
    private static final int ROWS = 3; // [cite: 38]
    private static final int COLS = 3; // [cite: 39]
    // The game board, declared as a class-level variable [cite: 35, 40]
    private static String board[][] = new String[ROWS][COLS];
    private static String currentPlayer; // To keep track of the current player ('X' or 'O')

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean playAgain;

        // Main game loop that allows players to play multiple games [cite: 13]
        do {
            // --- Game Initialization ---
            clearBoard(); // Clear the board for a new game [cite: 43, 53]
            currentPlayer = "X"; // X always starts first [cite: 53]
            int moveCount = 0;
            boolean gameIsOver = false;

            // --- Individual Game Turn Loop ---
            while (!gameIsOver) {
                display(); // Show the current board state [cite: 44]

                // Get valid move coordinates from the current player
                int rowMove;
                int colMove;
                boolean validMove;

                do {
                    // Get player input for row and column (1-3) [cite: 54]
                    rowMove = SafeInput.getRangedInt(in, "Player " + currentPlayer + ", enter your row (1-3)", 1, 3);
                    colMove = SafeInput.getRangedInt(in, "Player " + currentPlayer + ", enter your column (1-3)", 1, 3);

                    // Convert player coordinates (1-3) to array indices (0-2) [cite: 55]
                    rowMove--;
                    colMove--;

                    validMove = isValidMove(rowMove, colMove); // Check if the spot is empty [cite: 45]
                    if (!validMove) {
                        System.out.println("Invalid move! That cell is already taken. Please try again.");
                    }
                } while (!validMove); // Loop until a valid, empty space is chosen [cite: 56]

                board[rowMove][colMove] = currentPlayer; // Record the valid move [cite: 57]
                moveCount++; // Increment the move counter [cite: 58]

                // --- Check for Win or Tie Condition ---
                // A win is possible after 5 moves; a tie is possible after 7 moves.
                if (moveCount >= 5) {
                    if (isWin(currentPlayer)) { // Check if the current player has won [cite: 46, 59]
                        display(); // Show the final board
                        System.out.println("Player " + currentPlayer + " wins!");
                        gameIsOver = true;
                    }
                }
                if (moveCount >= 7 && !gameIsOver) { // Check for a tie only if no one has won
                    if (isTie()) { // Check for a tie condition [cite: 50, 59]
                        display(); // Show the final board
                        System.out.println("It's a Tie Game!");
                        gameIsOver = true;
                    }
                }

                // If the game isn't over, toggle the player [cite: 61]
                if (!gameIsOver) {
                    currentPlayer = currentPlayer.equals("X") ? "O" : "X";
                }
            }

            // Prompt to play again [cite: 60]
            playAgain = SafeInput.getYNConfirm(in, "Do you want to play again?");

        } while (playAgain);

        System.out.println("Thanks for playing Tic-Tac-Toe!");
    }

    // --- Helper Methods ---

    /**
     * Resets the board by setting all cells to a space character.
     * [cite: 43]
     */
    private static void clearBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = " "; // Set each cell to a space
            }
        }
    }

    /**
     * Displays the current Tic-Tac-Toe board with row and column dividers.
     * [cite: 44]
     */
    private static void display() {
        System.out.println(); // Add a blank line for better formatting
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                System.out.print(board[row][col]);
                if (col < COLS - 1) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (row < ROWS - 1) {
                System.out.println("---------");
            }
        }
        System.out.println();
    }

    /**
     * Checks if a move at the given coordinates is valid (i.e., the cell is empty).
     * @param row The row index of the move.
     * @param col The column index of the move.
     * @return true if the cell is a space, false otherwise.
     * [cite: 45]
     */
    private static boolean isValidMove(int row, int col) {
        return board[row][col].equals(" ");
    }

    /**
     * Checks if the specified player has won the game by calling helper methods for
     * row, column, and diagonal wins.
     * @param player The player to check for a win ("X" or "O").
     * @return true if the player has won, false otherwise.
     * [cite: 46]
     */
    private static boolean isWin(String player) {
        return isRowWin(player) || isColWin(player) || isDiagonalWin(player);
    }

    /**
     * Checks for a win in any of the three columns for the specified player.
     * @param player The player to check for a win.
     * @return true if a column win is found, false otherwise.
     * [cite: 47]
     */
    private static boolean isColWin(String player) {
        for (int col = 0; col < COLS; col++) {
            if (board[0][col].equals(player) && board[1][col].equals(player) && board[2][col].equals(player)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for a win in any of the three rows for the specified player.
     * @param player The player to check for a win.
     * @return true if a row win is found, false otherwise.
     * [cite: 48]
     */
    private static boolean isRowWin(String player) {
        for (int row = 0; row < ROWS; row++) {
            if (board[row][0].equals(player) && board[row][1].equals(player) && board[row][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for a win on either of the two diagonals for the specified player.
     * @param player The player to check for a win.
     * @return true if a diagonal win is found, false otherwise.
     * [cite: 49]
     */
    private static boolean isDiagonalWin(String player) {
        // Check top-left to bottom-right diagonal
        if (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) {
            return true;
        }
        // Check top-right to bottom-left diagonal
        if (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player)) {
            return true;
        }
        return false;
    }

    /**
     * Checks for a tie condition. A tie occurs if all cells are filled.
     * This method assumes it's only called after checking for a win.
     * @return true if the board is full, false otherwise.
     * [cite: 50]
     */
    private static boolean isTie() {
        // Check if any cell still contains a space. If so, it's not a tie.
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col].equals(" ")) {
                    return false; // Found an empty cell, so not a tie
                }
            }
        }
        // If the loops complete, no empty cells were found.
        return true;
    }
}