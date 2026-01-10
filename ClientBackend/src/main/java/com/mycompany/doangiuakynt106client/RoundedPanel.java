package com.mycompany.doangiuakynt106client; // Phải có chữ client ở cuối

import javax.swing.*;
import java.awt.*;

/**
 * JPanel bo tròn với viền, hỗ trợ dự án Client
 */
public class RoundedPanel extends JPanel {

    private int cornerRadius;
    private Color backgroundColor;
    private Color borderColor;
    private int borderThickness;

    public RoundedPanel(int radius, Color backgroundColor, Color borderColor, int borderThickness) {
        this.cornerRadius = radius;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.borderThickness = borderThickness;
        setOpaque(false); 
        setLayout(new BorderLayout()); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Vẽ nền
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);

        // Vẽ viền
        g2.setStroke(new BasicStroke(borderThickness));
        g2.setColor(borderColor);
        g2.drawRoundRect(borderThickness / 2, borderThickness / 2,
                width - borderThickness, height - borderThickness,
                cornerRadius, cornerRadius);

        g2.dispose();
    }
}