package main.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JComponent;

import main.constants.Constants;

public class PlayerPortraitView extends JComponent {
    private final Image portrait;
    private final String labelText;

    public PlayerPortraitView(Image portrait, String labelText, int portraitSize) {
        this.portrait = portrait;
        this.labelText = labelText;
        setSize(portraitSize, portraitSize + Constants.PORTRAIT_ADJUST_HEIGHT);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        final Graphics2D g2d = (Graphics2D) g;
        final int portraitSize = getWidth();
        final int portraitY = 20;

        final Font font = g2d.getFont();
        g2d.setFont(new Font("Arial", Font.BOLD, Constants.PORTRAIT_LABEL_FONT_SIZE));
        final FontMetrics fm = g2d.getFontMetrics();
        final int labelX = (getWidth() - fm.stringWidth(labelText)) / 2;
        final int labelY = 15;
        g2d.setColor(Color.BLACK);
        g2d.drawString(labelText, labelX, labelY);

        // Draw portrait image
        g2d.drawImage(portrait, 0, portraitY, portraitSize, portraitSize, null);

        g2d.setFont(font);
    }
}

