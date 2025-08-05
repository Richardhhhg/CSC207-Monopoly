package main.view;

import javax.swing.*;
import java.awt.*;

public class DiceView extends JComponent {
    private final DiceAnimator diceAnimator;
    private final int tileSize;
    private final int gap = 10;

    public DiceView(DiceAnimator diceAnimator, int tileSize) {
        this.diceAnimator = diceAnimator;
        this.tileSize = tileSize;

        int width = tileSize * 2 + gap;
        int height = tileSize + 30;
        setSize(width, height);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int x1 = 0;
        int x2 = tileSize + gap;
        int y = 0;

        g2d.drawImage(diceAnimator.getDiceIcon(diceAnimator.getFinalD1()).getImage(),
                x1, y, tileSize, tileSize, null);
        g2d.drawImage(diceAnimator.getDiceIcon(diceAnimator.getFinalD2()).getImage(),
                x2, y, tileSize, tileSize, null);

        String sumText = "Sum: " + diceAnimator.getLastDiceSum();
        Font oldFont = g2d.getFont();
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2d.getFontMetrics();
        int textX = (getWidth() - fm.stringWidth(sumText)) / 2;
        int textY = tileSize + fm.getAscent() + 5;
        g2d.drawString(sumText, textX, textY);
        g2d.setFont(oldFont);
    }
}
