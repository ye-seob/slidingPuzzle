package views;

import javax.swing.*;

import models.Score;

import java.awt.*;
import java.util.List;

public class ScoreView extends JFrame {
    private JTable scoreTable;
    private String[] columnNames = {"Name", "Time", "Moves"};

    public ScoreView(List<Score> scores) {
        setTitle("High Scores");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Object[][] data = new Object[scores.size()][3];
        for (int i = 0; i < scores.size(); i++) {
            Score score = scores.get(i);
            data[i][0] = score.getName();
            data[i][1] = score.getTime();
            data[i][2] = score.getMoves();
        }

        scoreTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        scoreTable.setFillsViewportHeight(true);

        add(scrollPane, BorderLayout.CENTER);
    }
}