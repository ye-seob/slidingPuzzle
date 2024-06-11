package main;

import views.MainView;
import controllers.GameController;
import controllers.CustomModeController; // 변경된 클래스 이름 import
import controllers.ScoreController;
import controllers.NormalModeController;
import controllers.TimeAttackModeController;
import controllers.MoveLimitModeController;
import controllers.ImpossibleModeController;
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
			GameController normalModeController = new NormalModeController(mainPanel, cardLayout, scoreController);
			GameController timeAttackModeController = new TimeAttackModeController(mainPanel, cardLayout,
					scoreController);
			GameController moveLimitModeController = new MoveLimitModeController(mainPanel, cardLayout,
					scoreController);
			GameController impossibleModeController = new ImpossibleModeController(mainPanel, cardLayout,
					scoreController);
			CustomModeController customModeController = new CustomModeController(mainPanel, cardLayout,
					scoreController); 

			mainView.setEasyButtonListener(e -> normalModeController.startGame(3));
			mainView.setMediumButtonListener(e -> normalModeController.startGame(4));
			mainView.setHardButtonListener(e -> normalModeController.startGame(5));
			mainView.setCustomButtonListener(e -> customModeController.showCustomView()); // 변경된 클래스 이름
			mainView.setRankingButtonListener(e -> scoreController.showRanking());
			mainView.setTimeAttackButtonListener(e -> timeAttackModeController.startGame(3)); // 타임어택 모드 리스너 추가
			mainView.setMoveLimitButtonListener(e -> moveLimitModeController.startGame(3)); // 움직임 제한 모드 리스너 추가
			mainView.setImpossibleButtonListener(e -> impossibleModeController.startGame(3)); // 임파서블 모드 리스너 추가

			mainView.getContentPane().add(mainPanel);
			mainView.setLocationRelativeTo(null);
			mainView.setVisible(true);

			cardLayout.show(mainPanel, "MainView");

			Music music = new Music("resources/music/bgm.mp3", true);
			music.start();

		});
	}
}
