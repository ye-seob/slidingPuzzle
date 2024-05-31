package controllers;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import javax.swing.*;
import models.*;
import views.*;

public class GameController {
    private PuzzleModel model;
    private GameView view;
    private Timer timer;
    private int timeElapsed;
    private Database database;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public GameController(JPanel mainPanel, CardLayout cardLayout) {
        this.database = new Database();
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
    }

    public void startGame(int size) {
        setupGame(size);
        startTimer();
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
            JOptionPane.showMessageDialog(null, "Please upload an image first.");
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

        int tileWidth = image.getWidth(null) / size;
        int tileHeight = image.getHeight(null) / size;

        for (int i = 0; i < size * size - 1; i++) {
            int x = (i % size) * tileWidth;
            int y = (i / size) * tileHeight;
            Image tileImage = createImage(new FilteredImageSource(image.getSource(), new CropImageFilter(x, y, tileWidth, tileHeight)));
            tiles[i] = new Tile(i + 1, tileImage);
        }
        tiles[size * size - 1] = new Tile(0, null);
        model.setTiles(tiles);

        view = new GameView(size);
        view.setController(this);

        model.shuffle();
        view.updateView(model.getTiles());

        mainPanel.add(view, "GameView");
        cardLayout.show(mainPanel, "GameView");
        startTimer();
    }

    public void tileClicked(int index) {
        if (model.moveTile(index)) {
            view.updateView(model.getTiles());
            if (model.isSolved()) {
                String name = JOptionPane.showInputDialog(null, "Congratulations! You've solved the puzzle in " + timeElapsed + " seconds! Enter your name:");
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
