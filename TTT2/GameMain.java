
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
/**
 * The main class for the Tic-Tac-Toe (Console-OO, non-graphics version)
 * It acts as the overall controller of the game.
 */


public class GameMain {

    // Define properties
    /** membuat antrian bermain */
    Queue<Seed> playerQueue = new LinkedList<>();
    private Board board;
    /** status dari game */
    private State currentState;
    /** declare variabel pemain yang sedang bermain */
    private Seed currentPlayer;

    private static Scanner in = new Scanner(System.in);

    /** Constructor to setup the game */
    public Main() {
        //membuat board baru
        initGame();

        //mereset board,menambah pemain pada antrian
        newGame();

        // Play the game once
        do {
            // The currentPlayer makes a move.
            // Update cells[][] and currentState
            stepGame();
            // Refresh the display
            board.paint();
            // output ketika state berubah
            if (currentState == State.CROSS_WON) {
                System.out.println("'X' won!\nBye!");
            } else if (currentState == State.NOUGHT_WON) {
                System.out.println("'O' won!\nBye!");
            } else if (currentState == State.DRAW) {
                System.out.println("It's Draw!\nBye!");
            }
            // Switch currentPlayer
        } while (currentState == State.PLAYING);  // repeat until game over
    }

    /** Perform one-time initialization tasks */
    public void initGame() {
        board = new Board();  // allocate game-board
    }

    /**reset board,menambah pemain,declare state */
    public void newGame() {
        board.newGame();  // clear the board contents
        // NOUGHT plays first
        playerQueue.add(Seed.NOUGHT);
        playerQueue.add(Seed.CROSS);
        currentState = State.PLAYING;
        // ready to play
    }

    /**step yang akan dijalankan pemain */
    public void stepGame() {
        boolean validInput = false;  // for validating input
        do {
           //mengambil value pertama dari queue lalu dihapus
            currentPlayer = playerQueue.poll();
            String icon = currentPlayer.getIcon();
            System.out.print("Player '" + currentPlayer + "', enter your move (row[1-3] column[1-3]): ");
            int row = in.nextInt() - 1;   // [0-2]
            int col = in.nextInt() - 1;
            if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                    && board.cells[row][col].content == Seed.NO_SEED) {
                // Update cells[][] and return the new game state after the move
                currentState = board.stepGame(currentPlayer, row, col);
                validInput = true; // input okay, exit loop
            } else {
                System.out.println("This move at (" + (row + 1) + "," + (col + 1)
                        + ") is not valid. Try again...");
            }
           //menambah antrian bermain
            playerQueue.add(currentPlayer);
        } while (!validInput);   // repeat until input is valid
    }

    /** The entry main() method */
    public static void main(String[] args) {
        new GameMain();  // Let the constructor do the job
    }
}
