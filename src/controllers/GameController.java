package controllers;

import java.awt.CardLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import models.Database;
import models.PuzzleModel;
import models.Score;
import models.Tile;
import music.Music;
import views.CustomView;
import views.GameView;
import views.ScoreView;

public class GameController {
    private PuzzleModel model;
    private GameView view;
    private Timer timer;
    private int timeElapsed;
    private Database database;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private boolean isTimeAttackMode;

    public GameController(JPanel mainPanel, CardLayout cardLayout) {
        this.database = new Database();
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
    }

    public void startGame(int size) {
        isTimeAttackMode = false;
        setupGame(size);
        startTimer();
    }

    public void startTimeAttackGame(int size) {
        isTimeAttackMode = true;
        setupGame(size);
        startTimeAttackTimer();
    }

    public void showCustomView() {
        CustomView customView = new CustomView();
        mainPanel.add(customView, "CustomView");
        cardLayout.show(mainPanel, "CustomView");

        customView.setUploadButtonListener(e -> uploadImage(customView));
        customView.setStartButtonListener(e -> startCustomGame(customView));
    }

    public void showRankings() {
        ScoreView scoreView = new ScoreView(database.getScores());
        scoreView.setController(this);
        mainPanel.add(scoreView, "ScoreView");
        cardLayout.show(mainPanel, "ScoreView");
    }

    private void uploadImage(CustomView customView) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            ImageIcon image = new ImageIcon(file.getAbsolutePath());
            customView.setImage(image);
        }
    }

    private void startCustomGame(CustomView customView) {
        ImageIcon image = customView.getImage();
        if (image != null) {
            setupCustomGame(image.getImage(), 3);
            cardLayout.show(mainPanel, "GameView");
        } else {
            JOptionPane.showMessageDialog(null, "사진을 먼저 업로드해주세요.");
        }
    }

    private void setupGame(int size) {
        model = new PuzzleModel(size);
        view = new GameView(size);
        view.setController(this);
        model.shuffle();
        view.updateView(model.getTiles());
        mainPanel.add(view, "GameView");
        cardLayout.show(mainPanel, "GameView");
    }

    private void setupCustomGame(Image image, int size) {
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

    public void tileClicked(int index) {
        if (model.moveTile(index)) {
            // 효과음 재생
            Music moveSound = new Music("resources/music/move.mp3", false);
            moveSound.start();

            view.updateView(model.getTiles());
            view.updateMoveCount(model.getMoveCount()); // 이동 횟수 업데이트
            if (model.isSolved()) {
                if (isTimeAttackMode) {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "축하합니다!");
                } else {
                    String name = JOptionPane.showInputDialog(null, timeElapsed + "초만에 깼습니다 이름을 입력해주세요");
                    if (name != null && !name.isEmpty()) {
                        saveScore(new Score(name, timeElapsed, model.getMoveCount())); // 실제 이동 횟수를 저장합니다.
                    }
                }
            }
        }
    }

    public void backButtonClicked() {
        cardLayout.show(mainPanel, "MainView");
    }

    private Image createImage(ImageProducer producer) {
        return Toolkit.getDefaultToolkit().createImage(producer);
    }

    private void startTimer() {
        if (timer != null) {
            timer.stop();
        }
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
        if (timer != null) {
            timer.stop();
        }
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

    private void saveScore(Score score) {
        database.addScore(score);
    }
}
