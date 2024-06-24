package models;

import java.io.*; 
import java.util.*; 
public class Database { 
	
    private  String filePath = "scores.txt"; 
    private List<Score> scores; 

    public Database() {
        scores = new ArrayList<>();
        loadScores(); 
    }

    private void loadScores() { 
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line; 
            while ((line = reader.readLine()) != null) { 
                scores.add(Score.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("파일이 존재하지 않습니다");
        }
    }

    public void saveScore(Score score) {
        scores.add(score); 
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Score s : scores) {
                writer.write(s.toString());
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
