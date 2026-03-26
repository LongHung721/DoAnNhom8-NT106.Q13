package com.mycompany.doangiuakynt106.partials;

import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            // getResource cần đường dẫn tuyệt đối trong classpath, bắt đầu bằng "/"
            backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        } catch (Exception e) {
            System.err.println("Không tìm thấy ảnh: " + imagePath);
        }
        setLayout(new GridBagLayout()); // căn giữa form đăng nhập
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Vẽ ảnh full kích thước panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
