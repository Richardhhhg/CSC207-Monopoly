package main.interface_adapter;

import java.awt.Point;

/**
 * Utility class for calculating board positions - separated from view logic
 */
public class BoardPositionCalculator {

    public Point getTilePosition(int position, int startX, int startY, int tileSize, int tileCount) {
        if (tileCount == 0) return new Point(startX, startY);

        int tilesPerSide = tileCount / 4;
        int boardSize = tilesPerSide * tileSize;

        if (position >= 0 && position <= tilesPerSide) {
            // Bottom row (left to right)
            return new Point(startX + position * tileSize, startY + boardSize);
        } else if (position >= (tilesPerSide + 1) && position <= tilesPerSide * 2) {
            // Right column (bottom to top)
            return new Point(startX + boardSize, startY + boardSize - (position - tilesPerSide) * tileSize);
        } else if (position >= (tilesPerSide * 2 + 1) && position <= tilesPerSide * 3) {
            // Top row (right to left)
            return new Point(startX + boardSize - (position - tilesPerSide * 2) * tileSize, startY);
        } else {
            // Left column (top to bottom)
            return new Point(startX, startY + (position - tilesPerSide * 3) * tileSize);
        }
    }
}