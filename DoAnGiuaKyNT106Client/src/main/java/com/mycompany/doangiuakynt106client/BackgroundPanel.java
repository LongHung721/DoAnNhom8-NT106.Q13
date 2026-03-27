package com.mycompany.doangiuakynt106client;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String resourcePath) {
        try {
            java.net.URL imgURL = getClass().getResource(resourcePath);
            if (imgURL != null) {
                this.backgroundImage = new ImageIcon(imgURL).getImage();
            }
        } catch (Exception e) {}
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (backgroundImage != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            // Vẽ ảnh nền phủ kín
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            // Phủ lớp đen mờ (Alpha 180) để làm nổi bật UI phía trên
            g2.setColor(new Color(15, 23, 42, 180));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
        super.paintComponent(g);
    }
}