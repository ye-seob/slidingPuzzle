package controllers; 

import java.awt.*;
import javax.swing.*;

public class MoveLimitModeController extends GameController { 
    private int moveLimit; 

    public MoveLimitModeController(JPanel mainPanel, CardLayout cardLayout, ScoreController scoreController) {
        super(mainPanel, cardLayout, scoreController);
    }

    public void startGame(int size) { 
        moveLimit = 50; 
        setupGame(size); 
        view.setMoveLimitMode(true);
        view.updateMoveCount(moveLimit); 
        startTimer(); 
    }

    protected void tileClickEvent() { 
        moveLimit--;
        view.updateMoveCount(moveLimit);
        if (moveLimit == 0) { 
            JOptionPane.showMessageDialog(null, "남은 이동 기회가 없습니다");
            cardLayout.show(mainPanel, "MainView"); 
        }
    }

    protected void finishingGame() { 
        JOptionPane.showMessageDialog(null, "축하합니다"); 
        cardLayout.show(mainPanel, "MainView");
    }
}
