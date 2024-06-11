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
        int result = fileChooser.showOpenDialog(null); // 파일 선택 대화상자 표시
        if (result == JFileChooser.APPROVE_OPTION) { // 파일이 선택된 경우
            File file = fileChooser.getSelectedFile(); // 선택된 파일 가져오기
            ImageIcon image = new ImageIcon(file.getAbsolutePath()); // 이미지 아이콘 생성
            customView.setImage(image); // 커스텀 뷰에 이미지 설정
        }
    }

    private void startCustomGame(CustomView customView) { 
        ImageIcon image = customView.getImage(); 
        if (image != null) { 
            setupCustomGame(image.getImage(), 3); 
        } 
        else { 
            JOptionPane.showMessageDialog(null, "사진을 선택하세요"); 
        }
    }

    public void startGame(int size) {}

    protected void tileClickEvent() {}
}
