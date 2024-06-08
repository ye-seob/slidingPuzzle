package main;

import views.MainView;
import controllers.GameController;
import controllers.CustomController;
import controllers.ScoreController;
import music.Music;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();

            CardLayout cardLayout = new CardLayout();
            JPanel mainPanel = new JPanel(cardLayout);

            mainPanel.add(mainView.getMainPanel(), "MainView");

            ScoreController scoreController = new ScoreController(mainPanel, cardLayout);
            GameController gameController = new GameController(mainPanel, cardLayout, scoreController);
            CustomController customController = new CustomController(mainPanel, cardLayout, gameController);

            mainView.setEasyButtonListener(e -> gameController.startGame(3));
            mainView.setMediumButtonListener(e -> gameController.startGame(4));
            mainView.setHardButtonListener(e -> gameController.startGame(5));
            mainView.setCustomButtonListener(e -> customController.showCustomView());
            mainView.setRankingButtonListener(e -> scoreController.showRankings());
            mainView.setTimeAttackButtonListener(e -> gameController.startTimeAttackGame(3)); // 타임어택 모드 리스너 추가
            mainView.setMoveLimitButtonListener(e -> gameController.startMoveLimitGame(3)); // 움직임 제한 모드 리스너 추가
            mainView.setImpossibleButtonListener(e -> gameController.startImpossibleGame(3)); // 임파서블 모드 리스너 추가

            mainView.getContentPane().add(mainPanel);
            mainView.setLocationRelativeTo(null);
            mainView.setVisible(true);

            cardLayout.show(mainPanel, "MainView");
            try {
                Music music = new Music("resources/music/bgm.mp3", true);
                music.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
