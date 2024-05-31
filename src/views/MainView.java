package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainView extends JPanel {
    private final JButton easyButton = new JButton("3x3");
    private final JButton mediumButton = new JButton("4x4");
    private final JButton hardButton = new JButton("5x5");
    private final JButton customButton = new JButton("Custom");
    private final JButton rankingButton = new JButton("Rankings");

    public MainView() {
        setLayout(new BorderLayout());

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(45, 45, 45));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        add(backgroundPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("Select Difficulty:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
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

    private void addButtonToPanel(JPanel panel, JButton button, GridBagConstraints gbc) {
        customizeButton(button);
        panel.add(button, gbc);
    }

    private void customizeButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setBackground(new Color(60, 63, 65));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(100, 102, 105));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(60, 63, 65));
            }
        });
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
