package controllers; 

import javax.swing.*;
import java.awt.*;

public class ImpossibleModeController extends GameController { 

    public ImpossibleModeController(JPanel mainPanel, CardLayout cardLayout, ScoreController scoreController) {
        super(mainPanel, cardLayout, scoreController); 
    }

    public void startGame(int size) { 
        setupGame(null,size); 
        view.setImpossibleMode(true);
        startInitTimer(); 
    }

    protected void tileClickEvent() {}

    protected void finishingGame() {
        initTimer.stop(); 
        JOptionPane.showMessageDialog(null, "축하합니다"); 
        cardLayout.show(mainPanel, "MainView");
    }
}
