package com.mycompany.doangiuakynt106;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel bo tròn với viền, có hỗ trợ màu nền trong suốt
 */
public class RoundedPanel extends JPanel {

    private int cornerRadius;
    private Color backgroundColor;
    private Color borderColor;
    private int borderThickness;

    /**
     * @param radius       Bán kính bo góc
     * @param backgroundColor Màu nền (có thể trong suốt bằng alpha)
     * @param borderColor     Màu viền
     * @param borderThickness Độ dày viền
     */
    public RoundedPanel(int radius, Color backgroundColor, Color borderColor, int borderThickness) {
        this.cornerRadius = radius;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.borderThickness = borderThickness;
        setOpaque(false); // nền trong suốt ở góc
        setLayout(new GridBagLayout()); // giữ layout giống panel gốc
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Vẽ nền bo góc
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);

        // Vẽ viền bo góc
        g2.setStroke(new BasicStroke(borderThickness));
        g2.setColor(borderColor);
        g2.drawRoundRect(borderThickness / 2, borderThickness / 2,
                width - borderThickness, height - borderThickness,
                cornerRadius, cornerRadius);

        g2.dispose();
    }
}
