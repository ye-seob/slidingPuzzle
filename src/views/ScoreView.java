package views;

import java.awt.*;
import java.net.URL;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

import controllers.ScoreController;
import models.Score;

public class ScoreView extends JPanel {
	private JTable scoreTable;
	private String[] score = { "Name", "Time", "Moves" };

	private JButton backButton = new JButton("뒤로 가기");
	private Image backgroundImg;

	private ScoreController ScoreController;

	public ScoreView(List<Score> scores) {
		setLayout(new BorderLayout());
		loadBackgroundImage();

		Object[][] data = new Object[scores.size()][3];

		for (int i = 0; i < scores.size(); i++)
		{
			Score score = scores.get(i);
			data[i][0] = score.getName();
			data[i][1] = score.getTime();
			data[i][2] = score.getMoves();
		}

		scoreTable = new JTable(data, score) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component Comp = super.prepareRenderer(renderer, row, column); 
				if (Comp instanceof JComponent) {
					((JComponent) Comp).setOpaque(false);
				}
				return Comp;
			}
		};
		
		styleTable(scoreTable); 

		JScrollPane scrollPanel = new JScrollPane(scoreTable); 
		scrollPanel.setOpaque(false); 
		scrollPanel.getViewport().setOpaque(false); 

		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)) { 
			protected void paintComponent(Graphics graphic) { 
				super.paintComponent(graphic); 
				graphic.setColor(new Color(45, 45, 45, 150)); 
				graphic.fillRect(0, 0, getWidth(), getHeight()); 
			}
		};
		bottomPanel.setOpaque(false); 
		bottomPanel.add(backButton); 

		add(scrollPanel, BorderLayout.CENTER); 
		add(bottomPanel, BorderLayout.SOUTH); 

		backButton.addActionListener(e -> ScoreController.backButtonClicked()); 
	}

	public void setController(ScoreController controller) { 
		this.ScoreController = controller; 
	}

	private void styleTable(JTable table) { 
		table.setFont(new Font("궁서 보통", Font.BOLD, 16));
		table.setForeground(Color.BLACK); 

		JTableHeader header = table.getTableHeader(); 
		header.setFont(new Font("궁서 보통", Font.BOLD, 18));
		header.setForeground(Color.WHITE);
		header.setBackground(new Color(30, 140, 255)); 

	}

	private void loadBackgroundImage() {
		URL imageUrl = getClass().getClassLoader().getResource("images/하늘 배경.png");
		backgroundImg = new ImageIcon(imageUrl).getImage();
	}

	protected void paintComponent(Graphics graphic) { 
		super.paintComponent(graphic); 
		if (backgroundImg != null) {
			graphic.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this); 
		}
	}
}
