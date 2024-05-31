package controllers;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.io.File;
import javax.swing.*;
import models.Database;
import models.PuzzleModel;
import models.Score;
import models.Tile;
import views.*;

public class GameController {
    private PuzzleModel model;
    private GameView view;
    private JFrame frame;
    private Timer timer;
    private int timeElapsed;
    private Database database;
    private MainView mainView;

    public GameController(MainView mainView) {
        this.mainView = mainView;
        this.database = new Database();
    }

    public void startGame(int size) {
        setupGame(size);
        startTimer();
    }

    public void showCustomView() {
        CustomView customView = new CustomView();
        customView.setVisible(true);
        customView.setUploadButtonListener(e -> uploadImage(customView));
        customView.setStartButtonListener(e -> startCustomGame(customView));
    }

    public void showRankings() {
        ScoreView scoreView = new ScoreView(database.getScores());
        scoreView.setVisible(true);
    }

    private void uploadImage(CustomView customView) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(customView);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            ImageIcon image = new ImageIcon(file.getAbsolutePath());
            customView.setImage(image);
        }
    }

    private void startCustomGame(CustomView customView) {
        ImageIcon image = customView.getImage();
        if (image != null) {
            customView.dispose();
            setupCustomGame(image.getImage(), 3); // 기본적으로 3x3으로 시작
        } else {
            JOptionPane.showMessageDialog(customView, "Please upload an image first.");
        }
    }

    private void setupGame(int size) {
        model = new PuzzleModel(size);
        view = new GameView(size);
        frame = new JFrame("Sliding Puzzle Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        view.setController(this);
        model.shuffle();
        view.updateView(model.getTiles());

        frame.add(view);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setupCustomGame(Image image, int size) {
        model = new PuzzleModel(size);
        Tile[] tiles = new Tile[size * size];

        int tileWidth = image.getWidth(null) / size;
        int tileHeight = image.getHeight(null) / size;

        for (int i = 0; i < size * size - 1; i++) {
            int x = (i % size) * tileWidth;
            int y = (i / size) * tileHeight;
            Image tileImage = createImage(new FilteredImageSource(image.getSource(), new CropImageFilter(x, y, tileWidth, tileHeight)));
            tiles[i] = new Tile(i + 1, tileImage);
        }
        tiles[size * size - 1] = new Tile(0, null); // 빈 타일
        model.setTiles(tiles);

        view = new GameView(size);
        frame = new JFrame("Custom Sliding Puzzle Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        view.setController(this);
        model.shuffle();
        view.updateView(model.getTiles());

        frame.add(view);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        startTimer();
    }

    public void tileClicked(int index) {
        if (model.moveTile(index)) {
            view.updateView(model.getTiles());
            if (model.isSolved()) {
                String name = JOptionPane.showInputDialog(frame, "Congratulations! You've solved the puzzle in " + timeElapsed + " seconds! Enter your name:");
                if (name != null && !name.isEmpty()) {
                    saveScore(new Score(name, timeElapsed, model.getSize() * model.getSize()));
                }
            }
        }
    }

    private Image createImage(ImageProducer producer) {
        return Toolkit.getDefaultToolkit().createImage(producer);
    }

    private void startTimer() {
        timeElapsed = 0;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeElapsed++;
                view.updateTimer(timeElapsed);
            }
        });
        timer.start();
    }

    private void saveScore(Score score) {
        database.addScore(score);
    }
}
