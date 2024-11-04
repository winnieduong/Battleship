import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    private JLabel missLabel, strikeLabel, totalMissLabel, totalHitLabel;

    public StatusPanel() {
        setLayout(new GridLayout(1, 4));

        missLabel = new JLabel("Miss: 0");
        strikeLabel = new JLabel("Strike: 0");
        totalMissLabel = new JLabel("Total Miss: 0");
        totalHitLabel = new JLabel("Total Hit: 0");

        add(missLabel);
        add(strikeLabel);
        add(totalMissLabel);
        add(totalHitLabel);
    }

    public void updateMissCounter(int miss) {
        missLabel.setText("Miss: " + miss);
    }

    public void updateStrikeCounter(int strike) {
        strikeLabel.setText("Strike: " + strike);
    }

    public void updateTotalMissCounter(int totalMiss) {
        totalMissLabel.setText("Total Miss: " + totalMiss);
    }

    public void updateTotalHitCounter(int totalHit) {
        totalHitLabel.setText("Total Hit: " + totalHit);
    }

    public void resetStatus() {
        updateMissCounter(0);
        updateStrikeCounter(0);
        updateTotalMissCounter(0);
        updateTotalHitCounter(0);
    }
}
