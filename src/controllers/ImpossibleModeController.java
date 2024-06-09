package controllers;

import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.JOptionPane;

public class ImpossibleModeController extends GameController {

    public ImpossibleModeController(JPanel mainPanel, CardLayout cardLayout, ScoreController scoreController) {
        super(mainPanel, cardLayout, scoreController);
    }

    @Override
    public void startGame(int size) {
        setupGame(size);
        view.setImpossibleMode(true);
        startShuffleTimer();
    }

    @Override
    protected void handleTileClick() {
        // Impossible mode doesn't have any specific tile click logic.
    }

    @Override
    protected void onPuzzleSolved() {
        shuffleTimer.stop();
        JOptionPane.showMessageDialog(null, "축하합니다! 퍼즐을 맞췄습니다. 성적은 저장되지 않습니다.");
        cardLayout.show(mainPanel, "MainView");
    }
}
