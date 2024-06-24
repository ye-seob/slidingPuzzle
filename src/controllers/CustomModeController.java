package controllers;

import java.awt.CardLayout;
import java.io.File;
import javax.swing.*;

import views.CustomView;

public class CustomModeController extends GameController {

    public CustomModeController(JPanel mainPanel, CardLayout cardLayout, ScoreController scoreController) {
        super(mainPanel, cardLayout, scoreController);
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
            setupGame(image.getImage(), 3); 
        } 
        else { 
            JOptionPane.showMessageDialog(null, "사진을 선택하세요"); 
        }
    }

    public void startGame(int size) {}

    protected void tileClickEvent() {}
}
