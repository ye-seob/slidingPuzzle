package controllers; // 패키지 controllers 선언

import javax.swing.JPanel; // Swing JPanel import
import java.awt.CardLayout; // AWT CardLayout import

public class NormalModeController extends GameController {

    public NormalModeController(JPanel mainPanel, CardLayout cardLayout, ScoreController scoreController) {
        super(mainPanel, cardLayout, scoreController); 
    }

    public void startGame(int size) { 
        setupGame(size); 
        startTimer(); 
    }

    protected void tileClickEvent() { 
    }

    protected void finishingGame() { 
        timer.stop(); 
        super.finishingGame(); 
    }
}
