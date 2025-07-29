package main.view;

import main.entity.tiles.PropertyTile;
import main.use_case.Player;
import main.Constants.Constants;
import java.awt.*;
import java.util.List;

/**
 * BoardRenderer handles all drawing/rendering logic for the board.
 */
public class BoardRenderer {

    public void drawBoard(Graphics g, GameBoard gameBoard, DiceAnimator diceAnimator) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int startX = 50;
        int startY = 8;
        int tilesPerSide = (gameBoard.getProperties().size()-4) / 4 + 2;
        int tileSize = Constants.BOARD_SIZE / tilesPerSide;

        // Draw properties
        drawProperties(g2d, gameBoard.getProperties(), gameBoard, startX, startY, tileSize);

        // Draw dice
        drawDice(g2d, diceAnimator, startX, startY, tileSize);

        // Draw current player portrait
        drawPlayerPortrait(g2d, gameBoard.getCurrentPlayer(), startX, startY, tileSize);

        // Draw players
        drawPlayers(g2d, gameBoard.getPlayers(), gameBoard, startX, startY, tileSize);
    }

    //TODO: Refactor this to TileView
    private void drawProperties(Graphics2D g2d, List<PropertyTile> properties, GameBoard gameBoard,
                                int startX, int startY, int tileSize) {
        for (int i = 0; i < properties.size(); i++) {
            Point pos = gameBoard.getTilePosition(i, startX, startY, tileSize);
            PropertyTile prop = properties.get(i);


            // Draw property tile background - colored if owned
            if (prop.isOwned()) {
                // Use owner's color as background
                Color ownerColor = prop.getOwner().getColor();
                // Make it slightly transparent so text is still readable
                Color backgroundTint = new Color(ownerColor.getRed(), ownerColor.getGreen(),
                                               ownerColor.getBlue(), 120);
                g2d.setColor(backgroundTint);
                g2d.fillRect(pos.x, pos.y, tileSize, tileSize);

                // Draw a border in the full owner color
                g2d.setColor(ownerColor);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRect(pos.x + 1, pos.y + 1, tileSize - 2, tileSize - 2);
                g2d.setStroke(new BasicStroke(1));
            } else {
                // Unowned property - white background
                g2d.setColor(Color.WHITE);
                g2d.fillRect(pos.x, pos.y, tileSize, tileSize);
            }

            g2d.setColor(Color.BLACK);
            g2d.drawRect(pos.x, pos.y, tileSize, tileSize);

            // Draw property name
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            FontMetrics fm = g2d.getFontMetrics();
            String name = prop.getName();
            int maxWidth = tileSize - 8; // Padding for text

            if (fm.stringWidth(name) > maxWidth) {
                // Split name into two lines at the nearest space
                int splitIndex = name.lastIndexOf(' ', name.length() / 2);
                if (splitIndex == -1 || splitIndex == 0 || splitIndex == name.length() - 1) {
                    splitIndex = name.indexOf(' ', name.length() / 2);
                    if (splitIndex == -1 || splitIndex == 0 || splitIndex == name.length() - 1) {
                        splitIndex = name.length() / 2;
                    }
                }
                String line1 = name.substring(0, splitIndex).trim();
                String line2 = name.substring(splitIndex).trim();
                int textX = pos.x + (tileSize - Math.max(fm.stringWidth(line1), fm.stringWidth(line2))) / 2;
                int textY1 = pos.y + tileSize / 2 - 8;
                int textY2 = pos.y + tileSize / 2 + 10;
                g2d.drawString(line1, textX, textY1);
                g2d.drawString(line2, textX, textY2);
            } else {
                int textX = pos.x + (tileSize - fm.stringWidth(name)) / 2;
                int textY = pos.y + tileSize / 2 + fm.getAscent() / 2 - 4;
                g2d.drawString(name, textX, textY);
            }

            // Draw price or rent information
            if (prop.getPrice() > 0) {
                String priceText;
                if (prop.isOwned()) {
                    float rent = prop.getOwner().adjustRent(prop.getRent());
                    priceText = "Rent: $" + (int)rent;
                } else {
                    priceText = "$" + (int)prop.getPrice();
                }

                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                FontMetrics priceFm = g2d.getFontMetrics();
                int priceX = pos.x + (tileSize - priceFm.stringWidth(priceText)) / 2;
                int priceY = pos.y + tileSize - 5;
                g2d.drawString(priceText, priceX, priceY);
            }
        }
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

    private void drawPlayers(Graphics2D g2d, List<Player> players, GameBoard gameBoard,
                             int startX, int startY, int tileSize) {
        for (Player player : players) {
            if (player.isBankrupt()) continue;//
            Point pos = gameBoard.getTilePosition(player.getPosition(), startX, startY, tileSize);
            g2d.setColor(player.getColor());
            int playerSize = 15;
            int offsetX = (players.indexOf(player) % 2) * 20;
            int offsetY = (players.indexOf(player) / 2) * 20;
            g2d.fillOval(pos.x + offsetX + 5, pos.y + offsetY + 5, playerSize, playerSize);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(pos.x + offsetX + 5, pos.y + offsetY + 5, playerSize, playerSize);
        }
    }
}