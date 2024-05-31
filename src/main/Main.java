package main;

import javax.swing.SwingUtilities;
import controllers.GameController;
import views.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();
            mainView.setVisible(true);

            GameController controller = new GameController(mainView);

            mainView.setEasyButtonListener(e -> controller.startGame(3));
            mainView.setMediumButtonListener(e -> controller.startGame(4));
            mainView.setHardButtonListener(e -> controller.startGame(5));
            mainView.setCustomButtonListener(e -> controller.showCustomView());
            mainView.setRankingButtonListener(e -> controller.showRankings());
        });
    }
}
