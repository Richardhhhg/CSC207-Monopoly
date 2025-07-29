package main.view;

import main.entity.tiles.PropertyTile;
import main.use_case.Player;
import main.interface_adapter.roll_dice.DiceViewModel; // NEW IMPORT
import main.Constants.Constants;
import java.awt.*;
import java.util.List;
import javax.swing.ImageIcon;

public class BoardRenderer {

    // UPDATED: Changed parameter from DiceController to DiceViewModel
    public void drawBoard(Graphics g, GameBoard gameBoard, DiceViewModel diceViewModel) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int startX = 50;
        int startY = 8;
        int tilesPerSide = (gameBoard.getProperties().size()-4) / 4 + 2;
        int tileSize = Constants.BOARD_SIZE / tilesPerSide;

        // Draw properties
        drawProperties(g2d, gameBoard.getProperties(), gameBoard, startX, startY, tileSize);

        // UPDATED: Draw dice using view model
        drawDice(g2d, diceViewModel, startX, startY, tileSize);

        // Draw current player portrait
        drawPlayerPortrait(g2d, gameBoard.getCurrentPlayer(), startX, startY, tileSize);

        // Draw players
        drawPlayers(g2d, gameBoard.getPlayers(), gameBoard, startX, startY, tileSize);
    }

    private void drawProperties(Graphics2D g2d, List<PropertyTile> properties, GameBoard gameBoard,
                                int startX, int startY, int tileSize) {
        // ... existing code remains the same ...
        for (int i = 0; i < properties.size(); i++) {
            Point pos = gameBoard.getTilePosition(i, startX, startY, tileSize);
            PropertyTile prop = properties.get(i);

            if (prop.isOwned()) {
                Color ownerColor = prop.getOwner().getColor();
                Color backgroundTint = new Color(ownerColor.getRed(), ownerColor.getGreen(),
                        ownerColor.getBlue(), 120);
                g2d.setColor(backgroundTint);
                g2d.fillRect(pos.x, pos.y, tileSize, tileSize);

                g2d.setColor(ownerColor);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRect(pos.x + 1, pos.y + 1, tileSize - 2, tileSize - 2);
                g2d.setStroke(new BasicStroke(1));
            } else {
                g2d.setColor(Color.WHITE);
                g2d.fillRect(pos.x, pos.y, tileSize, tileSize);
            }

            g2d.setColor(Color.BLACK);
            g2d.drawRect(pos.x, pos.y, tileSize, tileSize);

            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            FontMetrics fm = g2d.getFontMetrics();
            String name = prop.getName();
            int maxWidth = tileSize - 8;

            if (fm.stringWidth(name) > maxWidth) {
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

    // UPDATED: Use DiceViewModel instead of DiceController
    private void drawDice(Graphics2D g2d, DiceViewModel diceViewModel,
                          int startX, int startY, int tileSize) {
        int centerX = startX + Constants.BOARD_SIZE/2;
        int centerY = startY + Constants.BOARD_SIZE/2;

        int diceSize = tileSize;
        int gap = 10;

        int x1 = centerX - diceSize - gap/2;
        int x2 = centerX + gap/2;
        int y = centerY - diceSize/2;

        // Draw dice using view model values
        g2d.drawImage(getDiceIcon(diceViewModel.getDice1Value()).getImage(),
                x1, y, diceSize, diceSize, null);
        g2d.drawImage(getDiceIcon(diceViewModel.getDice2Value()).getImage(),
                x2, y, diceSize, diceSize, null);

        // Draw the sum underneath
        String sumText = "Sum: " + diceViewModel.getSum();
        Font oldFont = g2d.getFont();
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2d.getFontMetrics();
        int textX = centerX - fm.stringWidth(sumText)/2;
        int textY = y + diceSize + fm.getAscent() + 5;
        g2d.drawString(sumText, textX, textY);
        g2d.setFont(oldFont);

        // Show rolling status
        if (diceViewModel.isRolling()) {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            String rollingText = "Rolling...";
            FontMetrics rollingFm = g2d.getFontMetrics();
            int rollingX = centerX - rollingFm.stringWidth(rollingText)/2;
            int rollingY = textY + 20;
            g2d.drawString(rollingText, rollingX, rollingY);
        }
    }

    // MOVED: Dice icon method from old DiceController
    private ImageIcon getDiceIcon(int face) {
        ImageIcon[] diceIcons = new ImageIcon[7]; // Index 0 unused
        for (int i = 1; i <= 6; i++) {
            diceIcons[i] = new ImageIcon(getClass().getResource("/dice" + i + ".png"));
        }
        return diceIcons[face];
    }

    // ... rest of the existing methods remain the same ...
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
            if (player.isBankrupt()) continue;
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