package main.view;

import main.entity.players.rentModifier;
import main.entity.Game;
import main.entity.tiles.PropertyTile;
import main.entity.players.Player;
import main.Constants.Constants;
import main.entity.tiles.Tile;
import main.interface_adapter.Tile.PropertyTileViewModel;
import main.interface_adapter.Tile.StockMarketTileViewModel;
import main.view.Tile.PropertyTileView;
import main.view.Tile.StockMarketTileView;
import main.view.Tile.TileView;

import java.awt.*;
import java.util.List;

/**
 * BoardRenderer handles all drawing/rendering logic for the board.
 */
public class BoardRenderer {

    public void drawBoard(Graphics g, Game game, DiceAnimator diceAnimator) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int startX = 50;
        int startY = 8;
        int tilesPerSide = (game.getTiles().size()-4) / 4 + 2;
        int tileSize = Constants.BOARD_SIZE / tilesPerSide;

        // Remove: drawTiles(g2d, game.getTiles(), game, startX, startY, tileSize);

        drawDice(g2d, diceAnimator, startX, startY, tileSize);
        drawPlayerPortrait(g2d, game.getCurrentPlayer(), startX, startY, tileSize);
    }

    private void drawDice(Graphics2D g2d, DiceAnimator diceAnimator,
                          int startX, int startY, int tileSize) {
        // Centre of the board
        int centerX = startX + Constants.BOARD_SIZE/2;
        int centerY = startY + Constants.BOARD_SIZE/2;

        int diceSize = tileSize;
        int gap = 10;

        // Compute dice positions
        int x1 = centerX - diceSize - gap/2;
        int x2 = centerX + gap/2;
        int y = centerY - diceSize/2;

        // Draw dice
        g2d.drawImage(diceAnimator.getDiceIcon(diceAnimator.getFinalD1()).getImage(),
                x1, y, diceSize, diceSize, null);
        g2d.drawImage(diceAnimator.getDiceIcon(diceAnimator.getFinalD2()).getImage(),
                x2, y, diceSize, diceSize, null);

        // Draw the sum underneath
        String sumText = "Sum: " + diceAnimator.getLastDiceSum();
        Font oldFont = g2d.getFont();
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2d.getFontMetrics();
        int textX = centerX - fm.stringWidth(sumText)/2;
        int textY = y + diceSize + fm.getAscent() + 5;
        g2d.drawString(sumText, textX, textY);
        g2d.setFont(oldFont);
    }

    private void drawPlayerPortrait(Graphics2D g2d, Player currentPlayer,
                                    int startX, int startY, int tileSize) {
        if (currentPlayer == null || currentPlayer.getPortrait() == null) return;

        int centerX = startX + Constants.BOARD_SIZE/2;
        int centerY = startY + Constants.BOARD_SIZE/2;
        int diceSize = tileSize;
        int gap = 10;
        int x1 = centerX - diceSize - gap/2;
        int y = centerY - diceSize/2;

        int portraitSize = diceSize;
        int portraitX = x1 + 80;
        int portraitY = y - 150;

        String labelText = "Current Player:";
        Font oldFont = g2d.getFont();
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        FontMetrics fm = g2d.getFontMetrics();
        int labelX = portraitX + (portraitSize - fm.stringWidth(labelText)) / 2;
        int labelY = portraitY - 10;
        g2d.setColor(Color.BLACK);
        g2d.drawString(labelText, labelX, labelY);

        g2d.drawImage(currentPlayer.getPortrait(), portraitX, portraitY, portraitSize, portraitSize, null);
        g2d.setFont(oldFont);
    }
}