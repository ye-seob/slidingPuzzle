package controllers;

import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.JOptionPane;

public class MoveLimitModeController extends GameController {
    private int moveLimit;

    public MoveLimitModeController(JPanel mainPanel, CardLayout cardLayout, ScoreController scoreController) {
        super(mainPanel, cardLayout, scoreController);
    }

    @Override
    public void startGame(int size) {
        moveLimit = 30;
        setupGame(size);
        view.setMoveLimitMode(true);
        view.updateMoveCount(moveLimit);
        startTimer();
    }

    @Override
    protected void handleTileClick() {
        moveLimit--;
        view.updateMoveCount(moveLimit);
        if (moveLimit <= 0) {
            JOptionPane.showMessageDialog(null, "이동 제한을 초과했습니다!");
            cardLayout.show(mainPanel, "MainView");
        }
    }

    @Override
    protected void onPuzzleSolved() {
        JOptionPane.showMessageDialog(null, "축하합니다! 퍼즐을 맞췄습니다.");
        cardLayout.show(mainPanel, "MainView");
    }
}
