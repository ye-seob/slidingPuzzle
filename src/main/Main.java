package main;

import views.MainView;
import controllers.GameController;
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

			GameController controller = new GameController(mainPanel, cardLayout);

			mainView.setEasyButtonListener(e -> controller.startGame(3));
			mainView.setMediumButtonListener(e -> controller.startGame(4));
			mainView.setHardButtonListener(e -> controller.startGame(5));
			mainView.setCustomButtonListener(e -> controller.showCustomView());
			mainView.setRankingButtonListener(e -> controller.showRankings());

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
