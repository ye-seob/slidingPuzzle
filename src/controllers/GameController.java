package controllers;

import java.awt.*;
import java.awt.event.*;
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
    protected Timer shuffleTimer;
    protected int timeElapsed;
    protected int shuffleTimeRemaining;
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

    public void tileClicked(int index) {
        if (model.moveTile(index)) {
            Music moveSound = new Music("resources/music/move.mp3", false);
            moveSound.start();
            view.updateView(model.getTiles());
            view.updateMoveCount(model.getMoveCount());

            handleTileClick();

            if (model.isSolved()) {
                onPuzzleSolved();
            }
        }
    }

    protected abstract void handleTileClick();

    protected void onPuzzleSolved() {
    	Music successSound = new Music("resources/music/완성.mp3", false);
    	successSound.start();
        String name = JOptionPane.showInputDialog(null, timeElapsed + "초만에 깼습니다 이름을 입력해주세요");
        if (name != null && !name.isEmpty()) {
            scoreController.saveScore(name, timeElapsed, model.getMoveCount());
        }
        
        JOptionPane.showMessageDialog(null, "축하합니다!");
        cardLayout.show(mainPanel, "MainView");
    }

    public void backButtonClicked() {
        stopTimers();
        cardLayout.show(mainPanel, "MainView");
    }

    public void setupCustomGame(Image image, int size) {
        stopTimers();
        model = new PuzzleModel(size);
        Tile[] tiles = new Tile[size * size];
        ImageIcon[] tileIcons = new ImageIcon[size * size - 1];
        int tileWidth = image.getWidth(null) / size;
        int tileHeight = image.getHeight(null) / size;
        for (int i = 0; i < size * size - 1; i++) {
            int x = (i % size) * tileWidth;
            int y = (i / size) * tileHeight;
            Image tileImage = createImage(new FilteredImageSource(image.getSource(), new CropImageFilter(x, y, tileWidth, tileHeight)));
            tileIcons[i] = new ImageIcon(tileImage);
            tiles[i] = new Tile(i + 1, tileImage);
        }
        tiles[size * size - 1] = new Tile(0, null);
        model.setTiles(tiles);
        view = new GameView(size);
        view.setController(this);
        view.setCustomTileIcons(tileIcons);
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
        timeElapsed = 0;
        view.updateTimer(timeElapsed);
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeElapsed++;
                view.updateTimer(timeElapsed);
            }
        });
        timer.start();
    }

    protected void startTimeAttackTimer() {
        timeElapsed = 60;
        view.updateTimer(timeElapsed);
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeElapsed--;
                view.updateTimer(timeElapsed);
                if (timeElapsed <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "시간이 종료되었습니다!");
                    cardLayout.show(mainPanel, "MainView");
                }
            }
        });
        timer.start();
    }

    protected void startShuffleTimer() {
        shuffleTimeRemaining = 100;
        view.updateShuffleTimer(shuffleTimeRemaining);
        shuffleTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shuffleTimeRemaining--;
                view.updateShuffleTimer(shuffleTimeRemaining);
                if (shuffleTimeRemaining <= 0) {
                    model.shuffle();
                    view.updateView(model.getTiles());
                    shuffleTimeRemaining = 100;
                }
            }
        });
        shuffleTimer.start();
    }

    protected void stopTimers() {
        if (timer != null) {
            timer.stop();
        }
        if (shuffleTimer != null) {
            shuffleTimer.stop();
        }
    }

    protected Image createImage(ImageProducer producer) {
        return Toolkit.getDefaultToolkit().createImage(producer);
    }

    protected void updateMoveLimit() {
        view.updateMoveCount(moveLimit);
    }
}
