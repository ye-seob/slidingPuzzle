package views;

import javax.swing.*;
import controllers.GameController;
import models.Tile;
import java.awt.*;
import java.net.URL;

public class GameView extends JPanel {
    private final JButton[] buttons;
    private final int size;
    private final JLabel timerLabel = new JLabel("Time: 0");
    private Image backgroundImage;
    private ImageIcon tileIcon;

    public GameView(int size) {
        this.size = size;
        this.buttons = new JButton[size * size];
        setLayout(new BorderLayout());
        loadBackgroundImage();
        loadTileIcon();

        JPanel gridPanel = new JPanel(new GridLayout(size, size)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
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
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setIcon(tileIcon);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
    }

    public void updateView(Tile[] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].getNumber() == 0) {
                buttons[i].setText("");
                buttons[i].setIcon(tileIcon);
            } else {
                buttons[i].setText(String.valueOf(tiles[i].getNumber()));
                buttons[i].setIcon(tileIcon);
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

    private void loadBackgroundImage() {
        try {
            URL imageUrl = getClass().getClassLoader().getResource("images/퍼즐배경.png");
            if (imageUrl != null) {
                backgroundImage = new ImageIcon(imageUrl).getImage();
            } else {
                System.err.println("Background image not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTileIcon() {
        try {
            URL imageUrl = getClass().getClassLoader().getResource("images/tile.png");
            if (imageUrl != null) {
                tileIcon = new ImageIcon(imageUrl);
            } else {
                System.err.println("Tile image not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
