package controllers; 

import javax.swing.JPanel; 
import java.awt.CardLayout; 

public class NormalModeController extends GameController {

    public NormalModeController(JPanel mainPanel, CardLayout cardLayout, ScoreController scoreController) {
        super(mainPanel, cardLayout, scoreController); 
    }
    protected void tileClickEvent() {}

    public void startGame(int size) { 
        setupGame(null,size); 
    }
   
    protected void finishingGame() { 
        timer.stop(); 
        super.finishingGame(); 
    }
}
