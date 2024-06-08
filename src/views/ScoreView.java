package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import controllers.ScoreController;
import models.Score;

public class ScoreView extends JPanel {
    private JTable scoreTable;
    private String[] columnNames = {"Name", "Time", "Moves"};
    private JButton backButton = new JButton("뒤로 가기");
    private Image backgroundImage;
    private ScoreController controller;

    public ScoreView(List<Score> scores) {
        setLayout(new BorderLayout());
        loadBackgroundImage();
        
        Object[][] data = new Object[scores.size()][3];
        for (int i = 0; i < scores.size(); i++) {
            Score score = scores.get(i);
            data[i][0] = score.getName();
            data[i][1] = score.getTime();
            data[i][2] = score.getMoves();
        }

        scoreTable = new JTable(data, columnNames) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (c instanceof JComponent) {
                    ((JComponent) c).setOpaque(false);
                }
                return c;
            }
        };
        styleTable(scoreTable);

        JScrollPane scrollPane = new JScrollPane(scoreTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(45, 45, 45, 150));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bottomPanel.setOpaque(false);
        bottomPanel.add(backButton);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> controller.backButtonClicked());
    }

    public void setController(ScoreController controller) {
        this.controller = controller;
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("궁서 보통", Font.BOLD, 16));
        table.setForeground(Color.WHITE);
        table.setBackground(new Color(0, 0, 0, 150));
        table.setGridColor(Color.GRAY);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("궁서 보통", Font.BOLD, 18));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(30, 144, 255));
        header.setOpaque(false);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, renderer);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
