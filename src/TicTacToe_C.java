import java.util.Scanner;

public class TicTacToe_C {
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static String[][] board = new String[ROWS][COLS];

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String currentPlayer;
        boolean playAgain;

        do {
            clearBoard();
            currentPlayer = "X";
            int moveCount = 0;
            boolean gameWon = false;
            boolean tie = false;

            while (!gameWon && !tie) {
                display();

                int row = SafeInput.getRangedInt(in, currentPlayer + " - Enter row", 1, 3) - 1;
                int col = SafeInput.getRangedInt(in, currentPlayer + " - Enter column", 1, 3) - 1;

                while (!isValidMove(row, col)) {
                    System.out.println("Cell already taken. Try again.");
                    row = SafeInput.getRangedInt(in, currentPlayer + " - Enter row", 1, 3) - 1;
                    col = SafeInput.getRangedInt(in, currentPlayer + " - Enter column", 1, 3) - 1;
                }

                board[row][col] = currentPlayer;
                moveCount++;

                if (moveCount >= 5) {
                    gameWon = isWin(currentPlayer);
                    if (!gameWon && moveCount == 9) {
                        tie = isTie();
                    }
                }

                if (!gameWon) {
                    currentPlayer = currentPlayer.equals("X") ? "O" : "X";
                }
            }

            display();
            if (gameWon) {
                System.out.println(currentPlayer + " wins!");
            } else {
                System.out.println("It's a tie!");
            }

            playAgain = SafeInput.getYNConfirm(in, "Do you want to play again?");
        } while (playAgain);

        System.out.println("Thanks for playing!");
    }

    // Helper Methods

    private static void clearBoard() {
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++)
                board[i][j] = " ";
    }

    private static void display() {
        System.out.println("Current board:");
        for (int i = 0; i < ROWS; i++) {
            System.out.print("| ");
            for (int j = 0; j < COLS; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            if (i < ROWS - 1) {
                System.out.println("-------------");
            }
        }
    }

    private static boolean isValidMove(int row, int col) {
        return board[row][col].equals(" ");
    }

    private static boolean isWin(String player) {
        return isRowWin(player) || isColWin(player) || isDiagonalWin(player);
    }

    private static boolean isRowWin(String player) {
        for (int row = 0; row < ROWS; row++) {
            if (board[row][0].equals(player) && board[row][1].equals(player) && board[row][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isColWin(String player) {
        for (int col = 0; col < COLS; col++) {
            if (board[0][col].equals(player) && board[1][col].equals(player) && board[2][col].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDiagonalWin(String player) {
        return (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player))
                || (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player));
    }

    private static boolean isTie() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col].equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }
}