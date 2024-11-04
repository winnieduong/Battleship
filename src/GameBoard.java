import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameBoard extends JPanel {
    private JButton[][] cells;
    private int[][] board;
    private final int BOARD_SIZE = 10;
    private final int[] SHIP_SIZES = {5, 4, 3, 3, 2};

    private int missCounter, strikeCounter, totalMissCounter, totalHitCounter;
    private StatusPanel statusPanel;

    public GameBoard(StatusPanel statusPanel) {
        this.statusPanel = statusPanel;
        setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        cells = new JButton[BOARD_SIZE][BOARD_SIZE];
        board = new int[BOARD_SIZE][BOARD_SIZE];

        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                cells[row][col] = new JButton();
                cells[row][col].setPreferredSize(new Dimension(50, 50));  // Increase cell size
                cells[row][col].setBackground(Color.CYAN);
                cells[row][col].addActionListener(new CellClickHandler(row, col));
                add(cells[row][col]);
                board[row][col] = 0;
            }
        }
        placeShips();
    }

    private void placeShips() {
        Random random = new Random();
        for (int size : SHIP_SIZES) {
            boolean placed = false;
            while (!placed) {
                boolean horizontal = random.nextBoolean();
                int row = random.nextInt(BOARD_SIZE);
                int col = random.nextInt(BOARD_SIZE);

                if (canPlaceShip(row, col, size, horizontal)) {
                    placeShip(row, col, size, horizontal);
                    placed = true;
                }
            }
        }
    }

    private boolean canPlaceShip(int row, int col, int size, boolean horizontal) {
        if (horizontal && col + size > BOARD_SIZE) return false;
        if (!horizontal && row + size > BOARD_SIZE) return false;

        for (int i = 0; i < size; i++) {
            int r = row + (horizontal ? 0 : i);
            int c = col + (horizontal ? i : 0);
            if (board[r][c] != 0) return false;
        }
        return true;
    }

    private void placeShip(int row, int col, int size, boolean horizontal) {
        for (int i = 0; i < size; i++) {
            int r = row + (horizontal ? 0 : i);
            int c = col + (horizontal ? i : 0);
            board[r][c] = 1; // Ship cell
        }
    }

    private class CellClickHandler implements ActionListener {
        private int row, col;

        public CellClickHandler(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (board[row][col] == 1) { // Hit
                board[row][col] = 2;
                cells[row][col].setBackground(Color.RED);
                totalHitCounter++;
                missCounter = 0;
                statusPanel.updateTotalHitCounter(totalHitCounter);
                checkGameStatus();
            } else if (board[row][col] == 0) { // Miss
                board[row][col] = 3;
                cells[row][col].setBackground(Color.YELLOW);
                totalMissCounter++;
                missCounter++;
                statusPanel.updateMissCounter(missCounter);
                statusPanel.updateTotalMissCounter(totalMissCounter);

                if (missCounter == 5) {
                    strikeCounter++;
                    missCounter = 0;
                    statusPanel.updateStrikeCounter(strikeCounter);
                    if (strikeCounter == 3) {
                        JOptionPane.showMessageDialog(GameBoard.this, "Game Over! You lost.");
                        resetBoard();
                    }
                }
            }
            cells[row][col].setEnabled(false);
        }
    }

    private void checkGameStatus() {
        if (totalHitCounter == 17) {
            JOptionPane.showMessageDialog(this, "Congratulations! You won!");
            resetBoard();
        }
    }

    public void resetBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = 0;
                cells[row][col].setBackground(Color.CYAN);
                cells[row][col].setEnabled(true);
            }
        }
        missCounter = 0;
        strikeCounter = 0;
        totalMissCounter = 0;
        totalHitCounter = 0;
        statusPanel.resetStatus();
        placeShips();
    }
}
