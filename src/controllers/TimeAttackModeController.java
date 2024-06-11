package controllers;

import javax.swing.*;
import java.awt.*;

public class TimeAttackModeController extends GameController { 

    public TimeAttackModeController(JPanel mainPanel, CardLayout cardLayout, ScoreController scoreController) {
        super(mainPanel, cardLayout, scoreController); 
    }

    public void startGame(int size) {
        setupGame(size);
        startTimeAttackTimer();
    }

    protected void tileClickEvent() {
    }

    protected void finishingGame() { 
        timer.stop();
        JOptionPane.showMessageDialog(null, "축하합니다!"); 
        cardLayout.show(mainPanel, "MainView");
    }
}
