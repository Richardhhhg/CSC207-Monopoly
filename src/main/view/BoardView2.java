package main.view;

import main.Constants.Constants;
import main.entity.tiles.PropertyTile;
import main.entity.tiles.StockMarketTile;
import main.use_case.Tile;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Just the Game board
 * This is the display of just the board with tiles.
 */
public class BoardView2 extends JPanel {
    private int tileCount;
    private ArrayList<Tile> tiles;

    private final float PLACEHOLDER_RENT = 50;

    public BoardView2() {
        setPreferredSize(new java.awt.Dimension(Constants.BOARD_PANEL_WIDTH,
                Constants.BOARD_PANEL_HEIGHT));
    }

    /**
     * This creates the entities for the tiles
     */
    // TODO: This should be in a use case
    private void initializeGame() {
        String[] propertyNames = {
                "GO", "Mediterranean Ave", "Baltic Ave", "Reading Railroad",
                "Oriental Ave", "Vermont Ave", "Connecticut Ave", "St. James Place",
                "Tennessee Ave", "New York Ave", "Kentucky Ave", "Indiana Ave",
                "Illinois Ave", "Atlantic Ave", "Ventnor Ave", "Marvin Gardens",
                "Pacific Ave", "North Carolina Ave", "Pennsylvania Ave", "Boardwalk"
        };
        int[] prices = {0, 60, 60, 200, 100, 100, 120, 140, 140, 160, 180, 180, 200, 220, 220, 280, 300, 300, 320, 400};
        this.tileCount = propertyNames.length + 4;
        int nameIndex = 0;
        int tilesPerRoad = tileCount / 4;
        int firstStockIndex = tilesPerRoad / 2;
        List<Integer> stockIndices = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            stockIndices.add(firstStockIndex + i * tilesPerRoad);
        }
        this.tiles = new ArrayList<>();
        for (int i = 0; i < tileCount; i++) {
            if (stockIndices.contains(i)) {
                tiles.add(new StockMarketTile());
            } else {
                String name = propertyNames[nameIndex];
                int price = prices[nameIndex];
                nameIndex++;
                PropertyTile propertyTile = new PropertyTile(name, price, PLACEHOLDER_RENT);
                tiles.add(propertyTile);
            }
        }

    }

    /**
     * This intializes the UI components of the board.
     */
    // TODO: This is to be replaced by some sort of builder
    private void initializeUI() {
        setLayout(null); // Use absolute positioning

        int startX = 50;
        int startY = 50;
        System.out.println("Tile count: " + tileCount);
        int tilesPerSide = (tileCount) / 4 + 1; // +1 to include corners
        int tileSize = Constants.BOARD_SIZE / tilesPerSide;

        for (int i = 0; i < tileCount; i++) {
            Point position = getTilePosition(i, startX, startY, tileSize);
            TileView tileView = new TileView(tiles.get(i));
            tileView.setBounds(position.x, position.y, tileSize, tileSize);
            add(tileView);
            }
        }

    /**
     * Helper method to get the position of a tile based on its index.
     * @param position
     * @param startX
     * @param startY
     * @param tileSize
     * @return
     */
    private Point getTilePosition(int position, int startX, int startY, int tileSize) {
        // TODO: This is very messy, clean it up
        int tilesPerSide = this.tileCount / 4; // Number of tiles on each side of the board - 1
        int cool_number = tilesPerSide * tileSize;

        if (position >= 0 && position <= tilesPerSide) {
            // Bottom row (left to right)
            return new Point(startX + position * tileSize, startY + cool_number);
        } else if (position >= (tilesPerSide+1) && position <= tilesPerSide*2) {
            // Right column (bottom to top)
            return new Point(startX + cool_number, startY + cool_number - (position - tilesPerSide) * tileSize);
        } else if (position >= (tilesPerSide*2 + 1) && position <= tilesPerSide*3) {
            // Top row (right to left)
            return new Point(startX + cool_number - (position - tilesPerSide*2) * tileSize, startY);
        } else {
            // Left column (top to bottom)
            return new Point(startX, startY + (position - tilesPerSide*3) * tileSize);
        }
    }

    /**
     * Main method for testing the BoardView2 component.
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Board View");
        BoardView2 boardView = new BoardView2();
        boardView.initializeGame();
        boardView.initializeUI();
        frame.setContentPane(boardView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
