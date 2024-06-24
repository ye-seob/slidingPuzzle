package music; 

import javazoom.jl.player.Player; 
import java.io.FileInputStream; 

public class Music extends Thread { 
    private Player player; 
    private boolean rePlay; 
    private String filePath;

    public Music(String filePath, boolean rePlay) { 
        this.filePath = filePath; 
        this.rePlay = rePlay; 
    }

    public void run() { 
        do {
            try {
                FileInputStream fileInput = new FileInputStream(filePath); 
                player = new Player(fileInput); 
                player.play(); 
            } catch (Exception e) {
                System.out.println("음악을 재생시킬 수 없습니다");
            }
        } while (rePlay);
    }

    public void close() { 
        rePlay = false; 
        
        if (player != null) {
            player.close();
        }
    
    }
}
