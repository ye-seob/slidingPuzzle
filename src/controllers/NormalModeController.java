package controllers;

import javax.swing.JPanel;
import java.awt.CardLayout;

public class NormalModeController extends GameController {

    public NormalModeController(JPanel mainPanel, CardLayout cardLayout, ScoreController scoreController) {
        super(mainPanel, cardLayout, scoreController);
    }

    @Override
    public void startGame(int size) {
        setupGame(size);
        startTimer();
    }

    @Override
    protected void handleTileClick() {
        // Normal mode doesn't have any specific tile click logic.
    }

    @Override
    protected void onPuzzleSolved() {
        timer.stop();
        super.onPuzzleSolved();
    }
}
