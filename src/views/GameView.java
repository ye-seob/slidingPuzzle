package views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import controllers.GameController;
import models.Tile;

public class GameView extends JPanel {
    private JButton[] button;
    private int size;

    private JLabel timer = new JLabel();
    private JLabel move = new JLabel("이동 횟수: 0");
    private JButton backButton = new JButton("뒤로 가기");
    private JLabel tileInitTimer = new JLabel();

    private boolean isImpossibleMode;
    private boolean isMoveLimitMode;

    private Image backgroundImg;
    private ImageIcon tile;
    private ImageIcon[] customTile;

    public GameView(int size) {
        this.size = size;
        this.button = new JButton[size * size];

        setLayout(new BorderLayout());

        loadBackgroundImg();
        loadTile();

        JPanel grid = new JPanel(new GridLayout(size, size, 5, 5)) {
            protected void paintComponent(Graphics graphic) {
                super.paintComponent(graphic);
                graphic.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
            }
        };

        initButton(grid);
        add(grid, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        move.setForeground(Color.WHITE);
        move.setFont(new Font("궁서 보통", Font.BOLD, 16));

        tileInitTimer.setForeground(Color.WHITE);
        tileInitTimer.setFont(new Font("궁서 보통", Font.BOLD, 16));
        tileInitTimer.setVisible(false);

        timer.setForeground(Color.WHITE);
        timer.setFont(new Font("궁서 보통", Font.BOLD, 16));


        topPanel.add(timer, BorderLayout.NORTH);
        topPanel.add(tileInitTimer, BorderLayout.CENTER);
        topPanel.add(move, BorderLayout.WEST);
        topPanel.add(backButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
    }

    public void initButton(JPanel panel) {
        for (int i = 0; i < size * size; i++) {
            button[i] = new JButton();
            stylingButton(button[i]);
            panel.add(button[i]);
        }
    }

    private void stylingButton(JButton button) {
        button.setFont(new Font("궁서 보통", Font.BOLD, 40));

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        button.setForeground(Color.WHITE);
        button.setIcon(tile);

        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
    }

    public void updateView(Tile[] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            int number = tiles[i].getNumber();
            if (number == 0) {
                button[i].setText("");
                button[i].setIcon(tile);
            } else {
                if (customTile != null) {
                    button[i].setText("");
                    button[i].setIcon(customTile[number - 1]);
                } else {
                    button[i].setText(String.valueOf(number));
                    button[i].setIcon(tile);
                }
            }
        }
    }


    public void setController(GameController controller) {
        for (int i = 0; i < button.length; i++) {
            int index = i;
            button[i].addActionListener(e -> controller.tileClick(index));
        }
        backButton.addActionListener(e -> controller.backButtonClicked());
    }

    public void setImpossibleMode(boolean isImpossibleMode) {
        this.isImpossibleMode = isImpossibleMode;

        timer.setVisible(!isImpossibleMode);
        tileInitTimer.setVisible(isImpossibleMode);
    }

    public void setMoveLimitMode(boolean isMoveLimitMode) {
        this.isMoveLimitMode = isMoveLimitMode;
    }

    public void updateTimer(int seconds) {
        if (isImpossibleMode) {
            tileInitTimer.setText("초기화까지: " + seconds + "초");
        } else {
            timer.setText("시간: " + seconds);
        }
    }

    public void updateMoveCount(int moveCount) {
        if (isMoveLimitMode) {
            move.setText("남은 이동 횟수: " + moveCount);
        } else {
            move.setText("이동 횟수: " + moveCount);
        }
    }
	private void loadBackgroundImg() {

		URL imageUrl = getClass().getClassLoader().getResource("images/하늘 배경.png");

		if (imageUrl != null) {
			backgroundImg = new ImageIcon(imageUrl).getImage();
		} else {
			System.err.println("배경 이미지를 찾을 수 없습니다 ");
		}
	}

	private void loadTile() {
		try {
			URL imageUrl = getClass().getClassLoader().getResource("images/blue_tile.png");
			if (imageUrl != null) {
				BufferedImage img = ImageIO.read(imageUrl);
				int tileSize = 200;
				Image resizedImg = img.getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
				BufferedImage transparentImg = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_ARGB);

				Graphics2D graphic = transparentImg.createGraphics();
				
				float opaque = 0.5f;
				
				AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opaque); 
				graphic.setComposite(alphaComposite); 
				graphic.drawImage(resizedImg, 0, 0, null);
				graphic.dispose(); 
				
				tile = new ImageIcon(transparentImg); 
			} else { 
				System.out.println("이미지를 찾을 수 없습니다"); 
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}

	public void setCustomTileIcons(ImageIcon[] customTileIcons) { 
		this.customTile = customTileIcons;
	}

	protected void paintComponent(Graphics graphic) { 
		super.paintComponent(graphic);
		graphic.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this); 
	}
}
