package views;

import javax.swing.*;
import models.Score;
import java.awt.*;
import java.util.List;

public class ScoreView extends JPanel {
    private JTable scoreTable;
    private String[] columnNames = {"Name", "Time", "Moves"};

    public ScoreView(List<Score> scores) {
        setLayout(new BorderLayout());

        Object[][] data = new Object[scores.size()][3];
        for (int i = 0; i < scores.size(); i++) {
            Score score = scores.get(i);
            data[i][0] = score.getName();
            data[i][1] = score.getTime();
            data[i][2] = score.getMoves();
        }

        scoreTable = new JTable(data, columnNames);
        scoreTable.setBackground(new Color(45, 45, 45));
        scoreTable.setForeground(Color.WHITE);
        scoreTable.setFont(new Font("궁서 보통", Font.BOLD, 16));
        scoreTable.setGridColor(Color.GRAY);
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        scoreTable.setFillsViewportHeight(true);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(45, 45, 45));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }
}
