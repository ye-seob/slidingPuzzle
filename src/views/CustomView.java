package views; // 패키지 views 선언

import javax.swing.*;
import java.awt.*; 
import java.awt.event.ActionListener; // ActionListener import

public class CustomView extends JPanel { // CustomView 클래스 선언
    private JButton uploadButton = new JButton("이미지를 추가해주세요"); // 이미지 업로드 버튼 선언
    private JButton startButton = new JButton("시작!"); // 시작 버튼 선언
    private JLabel imageLabel = new JLabel(); // 이미지 레이블 선언
    private ImageIcon uploadImg; // 업로드된 이미지 변수 선언

    public CustomView() { // 생성자 정의
        setLayout(new BorderLayout()); // 레이아웃 설정
        JPanel panel = new JPanel(); // 패널 생성
        panel.add(uploadButton); // 업로드 버튼 패널에 추가
        panel.add(startButton); // 시작 버튼 패널에 추가
        add(panel, BorderLayout.SOUTH); // 패널을 SOUTH 위치에 추가
        add(imageLabel, BorderLayout.CENTER); // 이미지 레이블을 CENTER 위치에 추가
    }

    public void setUploadButtonListener(ActionListener listener) { // 업로드 버튼 리스너 설정 메서드 정의
        uploadButton.addActionListener(listener); // 업로드 버튼에 리스너 추가
    }

    public void setStartButtonListener(ActionListener listener) { // 시작 버튼 리스너 설정 메서드 정의
        startButton.addActionListener(listener); // 시작 버튼에 리스너 추가
    }

    public void setImage(ImageIcon image) { // 이미지 설정 메서드 정의
        this.uploadImg = image; // 업로드된 이미지 설정
        imageLabel.setIcon(image); // 이미지 레이블에 이미지 설정
        revalidate(); // 다시 유효성 검사
        repaint(); // 다시 그리기
    }

    public ImageIcon getImage() { // 이미지 반환 메서드 정의
        return uploadImg; // 업로드된 이미지 반환
    }
}
