package music;

import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class Music extends Thread {
    private Player player;
    private boolean isLoop;
    private String filePath;

    public Music(String filePath, boolean isLoop) {
        this.filePath = filePath;
        this.isLoop = isLoop;
    }

    @Override
    public void run() {
        do {
            try {
                FileInputStream fis = new FileInputStream(filePath);
                player = new Player(fis);
                player.play();
            } catch (Exception e) {
                System.out.println("Error playing music: " + e.getMessage());
            }
        } while (isLoop);
    }

    public void close() {
        isLoop = false;
        if (player != null) {
            player.close();
        }
        this.interrupt();
    }
}
