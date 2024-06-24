package controllers;

import java.awt.*;

import java.awt.image.*;
import javax.swing.*;

import models.PuzzleModel;

import music.Music;
import views.GameView;

public abstract class GameController {

	protected PuzzleModel model;
	protected GameView view;

	protected Timer timer;
	protected Timer initTimer;

	protected int time;
	protected int initTime;
	protected int moveLimit;

	protected JPanel mainPanel;
	protected CardLayout cardLayout;
	protected ScoreController scoreController;

	public GameController(JPanel mainPanel, CardLayout cardLayout, ScoreController scoreController) {
		this.mainPanel = mainPanel;
		this.cardLayout = cardLayout;
		this.scoreController = scoreController;
	}

	public abstract void startGame(int size);

	protected abstract void tileClickEvent();

	public void backButtonClicked() {
		cardLayout.show(mainPanel, "MainView");
	}

	public void tileClick(int index) {
		if (model.moveTile(index)) {

			Music moveSound = new Music("resources/music/move.mp3", false);
			moveSound.start();

			view.updateView(model.getTiles());
			view.updateMoveCount(model.getMoveCount());

			tileClickEvent();

			if (model.isSolved())
				finishingGame();
		} else {
			System.out.println("이동할 수 없습니다");
		}
	}

	protected void finishingGame() {
		Music successSound = new Music("resources/music/완성.mp3", false);
		successSound.start();

		String name = JOptionPane.showInputDialog( time + "초만에 깼습니다 이름을 입력해주세요");

		scoreController.saveScore(name, time, model.getMoveCount());

		JOptionPane.showMessageDialog(null, "축하합니다");

		cardLayout.show(mainPanel, "MainView");
	}

	public void setupGame(Image image,int size ) {
	    stopTimers();

	    model = new PuzzleModel(size);
	    view = new GameView(size);
	    view.setController(this);
	    model.setNumTile(); 
	    
	    if (image != null) {
            view.setCustomTileIcons(model.getTileIcons(image));
	    }

	    model.shuffle(); 
	    view.updateView(model.getTiles());

	    mainPanel.add(view, "GameView");
	    cardLayout.show(mainPanel, "GameView");

	    startTimer();
	}

	protected void setupGame(int size) {
		stopTimers();

		model = new PuzzleModel(size);
		view = new GameView(size);

		view.setController(this);
		model.shuffle();

		view.updateView(model.getTiles());

		mainPanel.add(view, "GameView");
		cardLayout.show(mainPanel, "GameView");
	}

	protected void startTimer() {
		time = 0;

		view.updateTimer(time);

		timer = new Timer(1000, e -> {
			time++;
			view.updateTimer(time);
		});

		timer.start();
	}
	protected void startTimeAttackTimer() {
	    stopTimers();

	    time = 30;
	    view.updateTimer(time);

	    timer = new Timer(1000, e -> {
	        time--;
	        view.updateTimer(time);
	        if (time == 0) {
	            timer.stop();
	            JOptionPane.showMessageDialog(null, "시간이 종료되었습니다");
	            cardLayout.show(mainPanel, "MainView");
	        }
	    });

	    timer.start();
	}
	protected void startInitTimer() {
	    initTime = 15;
	    view.updateTimer(initTime);

	    initTimer = new Timer(1000, e -> { 
	        initTime--; 
	        view.updateTimer(initTime);

	        if (initTime == 0) { 
	            model.shuffle(); 
	            view.updateView(model.getTiles()); 
	            initTime = 15; 
	        }
	    });

	    initTimer.start(); 
	}



	protected void stopTimers() {
		if (timer != null) {
			timer.stop();
		}
		if (initTimer != null) {
			initTimer.stop();
		}
	}

	protected Image createImage(ImageProducer producer) {
		return Toolkit.getDefaultToolkit().createImage(producer);
	}

	protected void updateMoveLimit() {
		view.updateMoveCount(moveLimit);
	}
}
