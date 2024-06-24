package views; 

import javax.swing.*; 
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL; 

public class MainView extends JFrame { 
	private JButton size3Button = new JButton("3x3"); 
	private JButton size4Button = new JButton("4x4");
	private JButton size5Button = new JButton("5x5"); 
	
	private JButton customButton = new JButton("Custom");
	private JButton timeAttackButton = new JButton("Time Attack"); 
	private JButton moveLimitButton = new JButton("Move Limit"); 
	private JButton impossibleButton = new JButton("Impossible Mode");
	
	private JButton rankingButton = new JButton("Rankings");

	private JPanel mainPanel = new JPanel();
	private Image backgroundImg; 

	public MainView() { 
		setTitle("슬라이딩 퍼즐"); 
		setSize(900, 800); 

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		mainPanel.setLayout(new BorderLayout()); 
		JPanel backgroundPanel = createBackground(); 
		mainPanel.add(backgroundPanel, BorderLayout.CENTER);
		getContentPane().add(mainPanel);
	}

	public JPanel getMainPanel() { 
		return mainPanel;
	}

	private JPanel createBackground() { 
		JPanel backgroundPanel = new JPanel() { 
			{ 
				URL imageUrl = getClass().getClassLoader().getResource("images/하늘 배경.png"); 
				backgroundImg = new ImageIcon(imageUrl).getImage();
				
				setLayout(new GridBagLayout()); 
				GridBagConstraints grid = createGrid(); 
				
				addBackground(this, grid); 
			}

			protected void paintComponent(Graphics graphic) {
				super.paintComponent(graphic);
				graphic.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this); 
			}
		};
		return backgroundPanel; 
	}

	private GridBagConstraints createGrid() { 
		GridBagConstraints grid = new GridBagConstraints(); 
		grid.insets = new Insets(20, 0, 0, 0); 
		grid.gridx = 0; 
		grid.gridy = 0;
		grid.anchor = GridBagConstraints.CENTER; 
		return grid; 
	}

	private void addBackground(JPanel backgroundPanel , GridBagConstraints grid) { 
		
		JLabel title = createTitle(); 
		
		backgroundPanel.add(title, grid); 
		grid.gridy++; 
		
		createButton(backgroundPanel, size3Button, grid); 
		createButton(backgroundPanel, size4Button, grid); 
		createButton(backgroundPanel, size5Button, grid);
		createButton(backgroundPanel, customButton, grid); 
		createButton(backgroundPanel, timeAttackButton, grid); 
		createButton(backgroundPanel, moveLimitButton, grid);
		createButton(backgroundPanel, impossibleButton, grid);
		createButton(backgroundPanel, rankingButton, grid);

	}

	private JLabel createTitle() { 
		JLabel title = new JLabel("게임 시작");
		
		title.setFont(new Font("궁서 보통", Font.BOLD, 24));
		title.setForeground(Color.WHITE); 
		
		return title; 
	}

	private void createButton(JPanel panel, JButton button, GridBagConstraints g) { 
		button.setFont(new Font("궁서 보통", Font.PLAIN, 18)); 
		button.setBackground(new Color(30, 150, 255));
		button.setForeground(Color.WHITE); 
		
		panel.add(button, g); 
		g.gridy++; 
	}

	public void setSize3ButtonListener(ActionListener listener) {
		size3Button.addActionListener(listener);
	}

	public void setSize4ButtonListener(ActionListener listener) { 
		size4Button.addActionListener(listener); 
	}

	public void setSize5ButtonListener(ActionListener listener) {
		size5Button.addActionListener(listener); 
	}

	public void setCustomButtonListener(ActionListener listener) { 
		customButton.addActionListener(listener); 
	}

	public void setRankingButtonListener(ActionListener listener) {
		rankingButton.addActionListener(listener);
	}

	public void setTimeAttackButtonListener(ActionListener listener) { 
		timeAttackButton.addActionListener(listener);
	}

	public void setMoveLimitButtonListener(ActionListener listener) { 
		moveLimitButton.addActionListener(listener);
	}

	public void setImpossibleButtonListener(ActionListener listener) { 
		impossibleButton.addActionListener(listener); 
	}
}
