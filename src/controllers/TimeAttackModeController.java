package controllers;

import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.JOptionPane;

public class TimeAttackModeController extends GameController {

    public TimeAttackModeController(JPanel mainPanel, CardLayout cardLayout, ScoreController scoreController) {
        super(mainPanel, cardLayout, scoreController);
    }

    @Override
    public void startGame(int size) {
        setupGame(size);
        startTimeAttackTimer();
    }

    @Override
    protected void handleTileClick() {
       
    }

    @Override
    protected void onPuzzleSolved() {
        timer.stop();
        JOptionPane.showMessageDialog(null, "축하합니다!");
        cardLayout.show(mainPanel, "MainView");
    }
}
