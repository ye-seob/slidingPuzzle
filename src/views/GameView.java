package views;

import javax.swing.*;
import controllers.GameController;
import models.Tile;
import java.awt.*;

public class GameView extends JPanel {
    private final JButton[] buttons;
    private final int size;
    private final JLabel timerLabel = new JLabel("Time: 0");

    public GameView(int size) {
        this.size = size;
        this.buttons = new JButton[size * size];
        setLayout(new BorderLayout());
        setBackground(new Color(45, 45, 45));

        JPanel gridPanel = new JPanel(new GridLayout(size, size));
        gridPanel.setBackground(new Color(45, 45, 45));
        initializeButtons(gridPanel);
        add(gridPanel, BorderLayout.CENTER);

        timerLabel.setForeground(Color.WHITE);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(timerLabel, BorderLayout.NORTH);
    }

    public void initializeButtons(JPanel panel) {
        for (int i = 0; i < size * size; i++) {
            buttons[i] = new JButton();
            customizeButton(buttons[i]);
            panel.add(buttons[i]);
        }
    }

    private void customizeButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBackground(new Color(60, 63, 65));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(100, 102, 105));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(60, 63, 65));
            }
        });
    }

    public void updateView(Tile[] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].getNumber() == 0) {
                buttons[i].setText("");
                buttons[i].setIcon(null);
            } else {
                buttons[i].setText(String.valueOf(tiles[i].getNumber()));
                if (tiles[i].getImage() != null) {
                    buttons[i].setIcon(new ImageIcon(tiles[i].getImage()));
                    buttons[i].setText("");
                }
            }
        }
    }

    public void setController(GameController controller) {
        for (int i = 0; i < buttons.length; i++) {
            int index = i;
            buttons[i].addActionListener(e -> controller.tileClicked(index));
        }
    }

    public void updateTimer(int seconds) {
        timerLabel.setText("Time: " + seconds);
    }
}
