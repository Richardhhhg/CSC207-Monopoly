package main.view;

import javax.swing.*;
import java.awt.*;

public class PlayerPortraitView extends JComponent {
    private final Image portrait;
    private final String labelText;

    public PlayerPortraitView(Image portrait, String labelText, int portraitSize) {
        this.portrait = portrait;
        this.labelText = labelText;
        setSize(portraitSize, portraitSize + 30);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (portrait == null) return;

        Graphics2D g2d = (Graphics2D) g;
        int portraitSize = getWidth();
        int portraitY = 20;

        Font oldFont = g2d.getFont();
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        FontMetrics fm = g2d.getFontMetrics();
        int labelX = (getWidth() - fm.stringWidth(labelText)) / 2;
        int labelY = 15;
        g2d.setColor(Color.BLACK);
        g2d.drawString(labelText, labelX, labelY);

        g2d.drawImage(portrait, 0, portraitY, portraitSize, portraitSize, null);

        g2d.setFont(oldFont);
    }
}

