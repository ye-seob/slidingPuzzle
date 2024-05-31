package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomView extends JFrame {
    private JButton uploadButton = new JButton("Upload Image");
    private JButton startButton = new JButton("Start Puzzle");
    private JLabel imageLabel = new JLabel();
    private ImageIcon uploadedImage;

    public CustomView() {
        setTitle("Custom Puzzle");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.add(uploadButton);
        panel.add(startButton);

        add(panel, BorderLayout.SOUTH);
        add(imageLabel, BorderLayout.CENTER);
    }

    public void setUploadButtonListener(ActionListener listener) {
        uploadButton.addActionListener(listener);
    }

    public void setStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    public void setImage(ImageIcon image) {
        this.uploadedImage = image;
        imageLabel.setIcon(image);
    }

    public ImageIcon getImage() {
        return uploadedImage;
    }
}
