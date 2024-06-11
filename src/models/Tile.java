package models; // 패키지 models 선언

import java.awt.Image; // AWT Image import

public class Tile { // Tile 클래스 선언
    private int number; // 숫자 변수 선언
    private Image image; // 이미지 변수 선언

    public Tile(int number, Image image) { // 생성자 정의
        this.number = number; // 숫자 설정
        this.image = image; // 이미지 설정
    }

    public int getNumber() { // 숫자 반환 메서드 정의
        return number; // 숫자 반환
    }

    public Image getImage() { // 이미지 반환 메서드 정의
        return image; // 이미지 반환
    }

    public void setImage(Image image) { // 이미지 설정 메서드 정의
        this.image = image; // 이미지 설정
    }
}
