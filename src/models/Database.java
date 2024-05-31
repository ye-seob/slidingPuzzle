package models;

import java.io.*;
import java.util.*;

public class Database {
    private static final String FILE_PATH = "scores.txt";
    private List<Score> scores;

    public Database() {
        scores = new ArrayList<>();
        loadScores();
    }

    private void loadScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(Score.fromString(line));
            }
        } catch (IOException e) {
            // 파일이 없을 경우 예외를 무시합니다.
        }
    }

    public void addScore(Score score) {
        scores.add(score);
        saveScores();
    }

    private void saveScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Score score : scores) {
                writer.write(score.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Score> getScores() {
        return scores;
    }
}
