package controllers;

import views.ScoreView;
import models.Database;
import models.Score;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.util.List;

public class ScoreController {
    private Database database;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public ScoreController(JPanel mainPanel, CardLayout cardLayout) {
        this.database = new Database();
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
    }

    public void showRankings() {
        ScoreView scoreView = new ScoreView(database.getScores());
        scoreView.setController(this);
        mainPanel.add(scoreView, "ScoreView");
        cardLayout.show(mainPanel, "ScoreView");
    }

    public void backButtonClicked() {
        cardLayout.show(mainPanel, "MainView");
    }

    public void saveScore(String name, int time, int moves) {
        Score score = new Score(name, time, moves);
        database.addScore(score);
    }
}
