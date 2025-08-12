package main.view;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import main.constants.Constants;

public class DiceView extends JComponent {
    private final DiceAnimator diceAnimator;
    private final int tileSize;
    private final int gap = 10;
    private final int textSize = Constants.RULES_BUTTON_FONT_SIZE;

    public DiceView(DiceAnimator diceAnimator, int tileSize) {
        this.diceAnimator = diceAnimator;
        this.tileSize = tileSize;

        // Set size large enough to contain two dice and the text
        final int width = tileSize * 2 + gap;
        final int height = tileSize + 30;
        setSize(width, height);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;

        final int x1 = 0;
        final int x2 = tileSize + gap;
        final int y = 0;

        // Draw dice images
        g2d.drawImage(diceAnimator.getDiceIcon(diceAnimator.getFinalD1()).getImage(),
                x1, y, tileSize, tileSize, null);
        g2d.drawImage(diceAnimator.getDiceIcon(diceAnimator.getFinalD2()).getImage(),
                x2, y, tileSize, tileSize, null);

        // Draw the sum text
        final String sumText = "Sum: " + diceAnimator.getLastDiceSum();
        final Font oldFont = g2d.getFont();
        g2d.setFont(new Font("Arial", Font.BOLD, textSize));
        final FontMetrics fm = g2d.getFontMetrics();
        final int textX = (getWidth() - fm.stringWidth(sumText)) / 2;
        final int textY = tileSize + fm.getAscent() + 5;
        g2d.drawString(sumText, textX, textY);
        g2d.setFont(oldFont);
    }
}
