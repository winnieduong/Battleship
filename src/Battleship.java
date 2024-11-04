import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Battleship extends JFrame {
    private GameBoard gameBoard;
    private StatusPanel statusPanel;
    private JButton playAgainButton, quitButton;

    public Battleship() {
        setTitle("Battleship Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        statusPanel = new StatusPanel();
        gameBoard = new GameBoard(statusPanel);  // Pass StatusPanel to GameBoard

        playAgainButton = new JButton("Play Again");
        quitButton = new JButton("Quit");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(playAgainButton);
        buttonPanel.add(quitButton);

        playAgainButton.addActionListener(e -> resetGame());
        quitButton.addActionListener(e -> quitGame());

        add(gameBoard, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void resetGame() {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            gameBoard.resetBoard();
            statusPanel.resetStatus();
        }
    }

    private void quitGame() {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Battleship::new);
    }
}
