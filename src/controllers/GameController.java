package controllers; 

import java.awt.*;

import java.awt.image.*;
import javax.swing.*; 

import models.PuzzleModel;
import models.Tile; 

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

	public abstract void startGame(int size); // 게임 시작 추상 메서드 선언

	public void tileClick(int index) { 
        if (model.moveTile(index)) { 
        	
            Music moveSound = new Music("resources/music/move.mp3", false); 
            moveSound.start();
            
            view.updateView(model.getTiles()); // 뷰 업데이트
            view.updateMoveCount(model.getMoveCount()); 
        
            tileClickEvent(); 

            if (model.isSolved())  
                finishingGame();
        }
        else {
        	System.out.println("이동할 수 없습니다");
    }
    }

	protected abstract void tileClickEvent(); 

	protected void finishingGame() {
		Music successSound = new Music("resources/music/완성.mp3", false); 
		successSound.start();
		
		String name = JOptionPane.showInputDialog(null, time + "초만에 깼습니다 이름을 입력해주세요");
		
		if (name != null && !name.isEmpty()) { 
			scoreController.saveScore(name, time, model.getMoveCount());
		}

		JOptionPane.showMessageDialog(null, "축하합니다"); 
		cardLayout.show(mainPanel, "MainView"); 
	}

	public void backButtonClicked() { 
		cardLayout.show(mainPanel, "MainView"); 
	}

	public void setupCustomGame(Image image, int size) { 
		
		model = new PuzzleModel(size); 
		Tile[] tiles = new Tile[size * size];
		ImageIcon[] tileIcons = new ImageIcon[size * size - 1]; // 타일 아이콘 배열 생성
		int tileWidth = image.getWidth(null) / size; // 타일 너비 계산
		int tileHeight = image.getHeight(null) / size; // 타일 높이 계산
		for (int i = 0; i < size * size - 1; i++) { // 모든 타일에 대해 반복
			int x = (i % size) * tileWidth; // x 좌표 계산
			int y = (i / size) * tileHeight; // y 좌표 계산
			Image tileImage = createImage(
					new FilteredImageSource(image.getSource(), new CropImageFilter(x, y, tileWidth, tileHeight))); // 타일
																													// 이미지
																													// 생성
			tileIcons[i] = new ImageIcon(tileImage); // 타일 아이콘 생성
			tiles[i] = new Tile(i + 1, tileImage); // 타일 생성
		}
		tiles[size * size - 1] = new Tile(0, null); // 마지막 타일 생성
		model.setTiles(tiles); // 모델에 타일 설정
		view = new GameView(size); // 게임 뷰 생성
		view.setController(this); // 컨트롤러 설정
		view.setCustomTileIcons(tileIcons); // 사용자 정의 타일 아이콘 설정
		model.shuffle(); // 모델 셔플
		view.updateView(model.getTiles()); // 뷰 업데이트
		mainPanel.add(view, "GameView"); // 메인 패널에 뷰 추가
		cardLayout.show(mainPanel, "GameView"); 
		startTimer(); // 타이머 시작
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

		timer.start(); // 타이머 시작
	}

	protected void startTimeAttackTimer() {
		time = 30; 
		
		view.updateTimer(time); 
		
		timer = new Timer(1000, e -> {
		    time--;
		    view.updateTimer(time);
		    if (time <= 0) {
		        timer.stop();
		        JOptionPane.showMessageDialog(null, "시간이 종료되었습니다");
		        cardLayout.show(mainPanel, "MainView");
		    }
		});

		timer.start();
	}

	protected void startInitTimer() {
		initTime = 100; 
		
		view.updateInitTimer(initTime); 
		
		initTimer = new Timer(1000, e -> { 
		    initTime--; 
		    view.updateInitTimer(initTime);
		    
		    if (initTime == 0) { 
		        model.shuffle();
		        view.updateView(model.getTiles()); 
		        initTime = 100; 
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
