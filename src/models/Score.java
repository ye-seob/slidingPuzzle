package models; // 패키지 models 선언

public class Score { // Score 클래스 선언
    private String name; // 이름 변수 선언
    private int time; // 시간 변수 선언
    private int moves; // 이동 횟수 변수 선언

    public Score(String name, int time, int moves) { // 생성자 정의
        this.name = name; // 이름 설정
        this.time = time; // 시간 설정
        this.moves = moves; // 이동 횟수 설정
    }

    public String getName() { // 이름 반환 메서드 정의
        return name; // 이름 반환
    }

    public int getTime() { // 시간 반환 메서드 정의
        return time; // 시간 반환
    }

    public int getMoves() { // 이동 횟수 반환 메서드 정의
        return moves; // 이동 횟수 반환
    }

    @Override
    public String toString() { // 문자열로 변환 메서드 재정의
        return name + "," + time + "," + moves; // 이름, 시간, 이동 횟수를 문자열로 반환
    }

    public static Score fromString(String str) { // 문자열에서 객체 생성 메서드 정의
        String[] parts = str.split(","); // 문자열을 쉼표로 분리하여 배열에 저장
        return new Score(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2])); // 이름, 시간, 이동 횟수를 가지고 Score 객체 생성하여 반환
    }
}
