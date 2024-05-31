package main;
import views.MainView;
import controllers.GameController;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sliding Puzzle Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);

            CardLayout cardLayout = new CardLayout();
            JPanel mainPanel = new JPanel(cardLayout);

            MainView mainView = new MainView();
            mainPanel.add(mainView, "MainView");

            GameController controller = new GameController(mainPanel, cardLayout);

            mainView.setEasyButtonListener(e -> controller.startGame(3));
            mainView.setMediumButtonListener(e -> controller.startGame(4));
            mainView.setHardButtonListener(e -> controller.startGame(5));
            mainView.setCustomButtonListener(e -> controller.showCustomView());
            mainView.setRankingButtonListener(e -> controller.showRankings());

            frame.add(mainPanel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            cardLayout.show(mainPanel, "MainView");
        });
    }
}
