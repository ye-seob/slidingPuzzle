package main;

import views.MainView;
import controllers.*;
import music.Music;

import javax.swing.*;
import java.awt.*;

public class Main {
	public static void main(String[] args) {

		CardLayout cardLayout = new CardLayout();
		JPanel mainPanel = new JPanel(cardLayout);

		MainView mainView = new MainView();
		mainPanel.add(mainView.getMainPanel(), "MainView");

		ScoreController scoreController = new ScoreController(mainPanel, cardLayout);

		GameController normalModeController = new NormalModeController(mainPanel, cardLayout, scoreController);
		GameController timeAttackModeController = new TimeAttackModeController(mainPanel, cardLayout, scoreController);
		GameController moveLimitModeController = new MoveLimitModeController(mainPanel, cardLayout, scoreController);
		GameController impossibleModeController = new ImpossibleModeController(mainPanel, cardLayout, scoreController);
		CustomModeController customModeController = new CustomModeController(mainPanel, cardLayout, scoreController);

		mainView.setSize3ButtonListener(e -> normalModeController.startGame(3));
		mainView.setSize4ButtonListener(e -> normalModeController.startGame(4));
		mainView.setSize5ButtonListener(e -> normalModeController.startGame(5));

		mainView.setCustomButtonListener(e -> customModeController.showCustomView());

		mainView.setTimeAttackButtonListener(e -> timeAttackModeController.startGame(3));

		mainView.setMoveLimitButtonListener(e -> moveLimitModeController.startGame(3));

		mainView.setImpossibleButtonListener(e -> impossibleModeController.startGame(3));

		mainView.setRankingButtonListener(e -> scoreController.showRanking());

		mainView.getContentPane().add(mainPanel);
		mainView.setLocationRelativeTo(null);
		mainView.setVisible(true);

		cardLayout.show(mainPanel, "MainView");

		Music music = new Music("resources/music/bgm.mp3", true);
		music.start();

	}
}
