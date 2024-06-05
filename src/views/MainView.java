package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private final JButton easyButton = new JButton("3x3");
    private final JButton mediumButton = new JButton("4x4");
    private final JButton hardButton = new JButton("5x5");
    private final JButton customButton = new JButton("Custom");
    private final JButton rankingButton = new JButton("Rankings");
    private final JPanel mainPanel = new JPanel();

    public MainView() {
        setTitle("슬라이딩 퍼즐");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.setLayout(new BorderLayout());
        JPanel backgroundPanel = createBackgroundPanel();
        mainPanel.add(backgroundPanel, BorderLayout.CENTER);
        getContentPane().add(mainPanel);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private JPanel createBackgroundPanel() {
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(45, 45, 45));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = createGridBagConstraints();
        addComponentsToBackgroundPanel(backgroundPanel, gbc);
        return backgroundPanel;
    }

    private GridBagConstraints createGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        return gbc;
    }

    private void addComponentsToBackgroundPanel(JPanel backgroundPanel, GridBagConstraints gbc) {
        JLabel titleLabel = createTitleLabel();
        backgroundPanel.add(titleLabel, gbc);
        gbc.gridy++;
        addButtonToPanel(backgroundPanel, easyButton, gbc);
        gbc.gridy++;
        addButtonToPanel(backgroundPanel, mediumButton, gbc);
        gbc.gridy++;
        addButtonToPanel(backgroundPanel, hardButton, gbc);
        gbc.gridy++;
        addButtonToPanel(backgroundPanel, customButton, gbc);
        gbc.gridy++;
        addButtonToPanel(backgroundPanel, rankingButton, gbc);
    }

    private JLabel createTitleLabel() {
        JLabel titleLabel = new JLabel("게임 시작");
        titleLabel.setFont(new Font("궁서 보통", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        return titleLabel;
    }

    private void addButtonToPanel(JPanel panel, JButton button, GridBagConstraints gbc) {
        customizeButton(button);
        panel.add(button, gbc);
    }

    private void customizeButton(JButton button) {
        button.setFont(new Font("궁서 보통", Font.PLAIN, 18));
        button.setBackground(new Color(60, 63, 65));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }

    public void setEasyButtonListener(ActionListener listener) {
        easyButton.addActionListener(listener);
    }

    public void setMediumButtonListener(ActionListener listener) {
        mediumButton.addActionListener(listener);
    }

    public void setHardButtonListener(ActionListener listener) {
        hardButton.addActionListener(listener);
    }

    public void setCustomButtonListener(ActionListener listener) {
        customButton.addActionListener(listener);
    }

    public void setRankingButtonListener(ActionListener listener) {
        rankingButton.addActionListener(listener);
    }
}
