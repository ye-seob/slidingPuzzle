package models; // 패키지 models 선언

import java.io.*; // 입출력 패키지 import
import java.util.*; // 유틸리티 패키지 import

public class Database { // Database 클래스 선언
    private static final String FILE_PATH = "scores.txt"; // 파일 경로 상수 선언
    private List<Score> scores; // 점수 리스트 선언

    public Database() { // 생성자 정의
        scores = new ArrayList<>(); // 점수 리스트 초기화
        loadScores(); // 점수 불러오기 메서드 호출
    }

    private void loadScores() { // 점수 불러오기 메서드 정의
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) { // 파일 리더 생성
            String line; // 라인 변수 선언
            while ((line = reader.readLine()) != null) { // 파일 라인 읽기
                scores.add(Score.fromString(line)); // 문자열에서 점수 객체로 변환하여 리스트에 추가
            }
        } catch (IOException e) { // 입출력 예외 처리
            // 파일이 없을 경우 예외를 무시합니다.
        }
    }

    public void addScore(Score score) { // 점수 추가 메서드 정의
        scores.add(score); // 점수 리스트에 점수 추가
        saveScores(); // 점수 저장 메서드 호출
    }

    private void saveScores() { // 점수 저장 메서드 정의
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) { // 파일 라이터 생성
            for (Score score : scores) { // 점수 리스트 순회
                writer.write(score.toString()); // 점수 문자열로 변환하여 파일에 쓰기
                writer.newLine(); // 새로운 라인 추가
            }
        } catch (IOException e) { // 입출력 예외 처리
            e.printStackTrace(); // 예외 출력
        }
    }

    public List<Score> getScores() { // 점수 리스트 반환 메서드 정의
        return scores; // 점수 리스트 반환
    }
}
