package views;

import javax.imageio.ImageIO;
import javax.swing.*;
import controllers.GameController;
import models.Tile;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class GameView extends JPanel {
    private final JButton[] buttons;
    private final int size;
    private final JLabel timerLabel = new JLabel("시간: 0");
    private final JLabel moveLabel = new JLabel("이동 횟수: 0");
    private final JButton backButton = new JButton("뒤로 가기");
    private Image backgroundImage;
    private ImageIcon tileIcon;

    public GameView(int size) {
        this.size = size;
        this.buttons = new JButton[size * size];
        setLayout(new BorderLayout());
        loadBackgroundImage();
        loadTileIcon();

        JPanel gridPanel = new JPanel(new GridLayout(size, size, 5, 5)) { // 타일 간격 추가
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        initializeButtons(gridPanel);
        add(gridPanel, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setFont(new Font("궁서 보통", Font.BOLD, 16));
        topPanel.add(timerLabel, BorderLayout.WEST);
        
        moveLabel.setForeground(Color.WHITE);
        moveLabel.setFont(new Font("궁서 보통", Font.BOLD, 16));
        topPanel.add(moveLabel, BorderLayout.CENTER);

        topPanel.add(backButton, BorderLayout.EAST); // 뒤로 가기 버튼 추가
        add(topPanel, BorderLayout.NORTH);
    }

    public void initializeButtons(JPanel panel) {
        for (int i = 0; i < size * size; i++) {
            buttons[i] = new JButton();
            customizeButton(buttons[i]);
            panel.add(buttons[i]);
        }
    }

    private void customizeButton(JButton button) {
        button.setFont(new Font("궁서 보통", Font.BOLD, 40));
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
                buttons[i].setText("");
                buttons[i].setIcon(new ImageIcon(tiles[i].getImage())); // 타일 이미지 설정
            }
        }
    }

    public void setController(GameController controller) {
        for (int i = 0; i < buttons.length; i++) {
            int index = i;
            buttons[i].addActionListener(e -> controller.tileClicked(index));
        }
        backButton.addActionListener(e -> controller.backButtonClicked()); // 뒤로 가기 버튼 이벤트 추가
    }

    public void updateTimer(int seconds) {
        timerLabel.setText("시간: " + seconds);
    }

    public void updateMoveCount(int moveCount) {
        moveLabel.setText("이동 횟수: " + moveCount);
    }

    private void loadBackgroundImage() {
        try {
            URL imageUrl = getClass().getClassLoader().getResource("images/하늘 배경.png");
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
            URL imageUrl = getClass().getClassLoader().getResource("images/blue_tile.png");
            if (imageUrl != null) {
                BufferedImage img = ImageIO.read(imageUrl);
                int tileSize = 240; // 타일 아이콘의 크기를 정사각형으로 설정

                // 이미지의 크기를 조정합니다.
                Image resizedImg = img.getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
                BufferedImage transparentImg = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = transparentImg.createGraphics();

                // 투명도를 설정합니다.
                float alpha = 0.5f; // 투명도 (0.0f - 완전 투명, 1.0f - 완전 불투명)
                AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g2d.setComposite(alphaComposite);

                // 이미지를 그립니다.
                g2d.drawImage(resizedImg, 0, 0, null);
                g2d.dispose();

                tileIcon = new ImageIcon(transparentImg);
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
