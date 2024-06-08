package controllers;

import java.awt.CardLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JOptionPane;

import models.PuzzleModel;
import models.Tile;
import music.Music;
import views.GameView;

public class GameController {
    private PuzzleModel model;
    private GameView view;
    private Timer timer;
    private Timer shuffleTimer;
    private int timeElapsed;
    private int shuffleTimeRemaining;
    private int moveLimit;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private boolean isTimeAttackMode;
    private boolean isMoveLimitMode;
    private boolean isImpossibleMode;
    private ScoreController scoreController;

    public GameController(JPanel mainPanel, CardLayout cardLayout, ScoreController scoreController) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.scoreController = scoreController;
    }

    public void startGame(int size) {
        stopTimers();
        isTimeAttackMode = false;
        isMoveLimitMode = false;
        isImpossibleMode = false;
        setupGame(size);
        startTimer();
    }

    public void startTimeAttackGame(int size) {
        stopTimers();
        isTimeAttackMode = true;
        isMoveLimitMode = false;
        isImpossibleMode = false;
        setupGame(size);
        startTimeAttackTimer();
    }

    public void startMoveLimitGame(int size) {
        stopTimers();
        isMoveLimitMode = true;
        isTimeAttackMode = false;
        isImpossibleMode = false;
        moveLimit = 30;
        setupGame(size);
        view.setMoveLimitMode(true);
        updateMoveLimit();
    }

    public void startImpossibleGame(int size) {
        stopTimers();
        isImpossibleMode = true;
        isTimeAttackMode = false;
        isMoveLimitMode = false;
        shuffleTimeRemaining = 100;
        setupGame(size);
        view.setImpossibleMode(true);
        startShuffleTimer();
    }

    public void tileClicked(int index) {
        if (model.moveTile(index)) {
            // 효과음 재생
            Music moveSound = new Music("resources/music/move.mp3", false);
            moveSound.start();

            view.updateView(model.getTiles());
            view.updateMoveCount(model.getMoveCount()); // 이동 횟수 업데이트

            if (isMoveLimitMode) {
                moveLimit--;
                view.updateMoveCount(moveLimit); // 남은 이동 횟수 업데이트
                if (moveLimit <= 0) {
                    JOptionPane.showMessageDialog(null, "이동 제한을 초과했습니다!");
                    cardLayout.show(mainPanel, "MainView");
                    return;
                }
            }

            if (model.isSolved()) {
                if (isTimeAttackMode) {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "축하합니다!");
                } else if (isMoveLimitMode) {
                    JOptionPane.showMessageDialog(null, "축하합니다! 퍼즐을 맞췄습니다.");
                } else if (isImpossibleMode) {
                    shuffleTimer.stop();
                    JOptionPane.showMessageDialog(null, "축하합니다! 퍼즐을 맞췄습니다. 성적은 저장되지 않습니다.");
                } else {
                    String name = JOptionPane.showInputDialog(null, timeElapsed + "초만에 깼습니다 이름을 입력해주세요");
                    if (name != null && !name.isEmpty()) {
                        scoreController.saveScore(name, timeElapsed, model.getMoveCount());
                    }
                }
            }
        }
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

    private void setupGame(int size) {
        stopTimers();
        model = new PuzzleModel(size);
        view = new GameView(size);
        view.setController(this);
        view.setImpossibleMode(isImpossibleMode);
        view.setMoveLimitMode(isMoveLimitMode);
        model.shuffle();
        view.updateView(model.getTiles());
        mainPanel.add(view, "GameView");
        cardLayout.show(mainPanel, "GameView");
    }

    private void startTimer() {
        timeElapsed = 0;
        view.updateTimer(timeElapsed);  // 타이머 초기화 시 0초를 갱신합니다.
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeElapsed++;
                view.updateTimer(timeElapsed);
            }
        });
        timer.start();
    }

    private void startTimeAttackTimer() {
        timeElapsed = 60; // 타임어택 모드는 60초부터 시작합니다.
        view.updateTimer(timeElapsed);
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeElapsed--;
                view.updateTimer(timeElapsed);
                if (timeElapsed <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "시간이 종료되었습니다!");
                }
            }
        });
        timer.start();
    }

    private void startShuffleTimer() {
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

    private void stopTimers() {
        if (timer != null) {
            timer.stop();
        }
        if (shuffleTimer != null) {
            shuffleTimer.stop();
        }
    }

    private Image createImage(ImageProducer producer) {
        return Toolkit.getDefaultToolkit().createImage(producer);
    }

    private void updateMoveLimit() {
        view.updateMoveCount(moveLimit);
    }
}
