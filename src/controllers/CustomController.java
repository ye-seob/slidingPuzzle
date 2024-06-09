package controllers;

import java.awt.CardLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import views.CustomView;

public class CustomController {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private GameController gameController;

    public CustomController(JPanel mainPanel, CardLayout cardLayout, GameController gameController) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.gameController = gameController;
    }

    public void showCustomView() {
        CustomView customView = new CustomView();
        mainPanel.add(customView, "CustomView");
        cardLayout.show(mainPanel, "CustomView");

        customView.setUploadButtonListener(e -> uploadImage(customView));
        customView.setStartButtonListener(e -> startCustomGame(customView));
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
            gameController.setupCustomGame(image.getImage(), 3);
            cardLayout.show(mainPanel, "GameView");
        } else {
            JOptionPane.showMessageDialog(null, "사진을 먼저 업로드해주세요.");
        }
    }
}
