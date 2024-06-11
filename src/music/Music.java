package music; // 패키지 music 선언

import javazoom.jl.player.Player; // javazoom 라이브러리 Player 클래스 import
import java.io.FileInputStream; // 파일 입력 스트림 import

public class Music extends Thread { // Music 클래스 선언
    private Player player; // 플레이어 변수 선언
    private boolean isLoop; // 반복 여부 변수 선언
    private String filePath; // 파일 경로 변수 선언

    public Music(String filePath, boolean isLoop) { // 생성자 정의
        this.filePath = filePath; // 파일 경로 설정
        this.isLoop = isLoop; // 반복 여부 설정
    }

    @Override
    public void run() { // 스레드 실행 메서드 재정의
        do { // 반복 실행
            try { // 예외 처리
                FileInputStream fis = new FileInputStream(filePath); // 파일 입력 스트림 생성
                player = new Player(fis); // 플레이어 생성
                player.play(); // 음악 재생
            } catch (Exception e) { // 예외 발생 시 처리
                System.out.println("Error playing music: " + e.getMessage()); // 오류 메시지 출력
            }
        } while (isLoop); // 반복 여부에 따라 실행
    }

    public void close() { // 음악 종료 메서드 정의
        isLoop = false; // 반복 종료
        if (player != null) { // 플레이어가 존재하면
            player.close(); // 플레이어 종료
        }
        this.interrupt(); // 스레드 인터럽트
    }
}
