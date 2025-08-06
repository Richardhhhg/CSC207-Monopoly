package main.view.Tile;

import main.entity.tiles.GoTile;
import main.entity.tiles.PropertyTile;
import main.entity.tiles.StockMarketTile;
import main.entity.tiles.Tile;

import javax.swing.*;
import java.awt.*;

/**
 * Generic visual component for a single board tile.
 * Creates different layouts based on tile type (Go, Property, Stock Market).
 */
public class TileView_OLD extends JPanel {
    private JLabel mainLabel;
    private JLabel priceLabel;
    private JLabel ownerLabel;
    private Tile currentTile;

    /**
     * Configure this TileView based on the tile type.
     * Creates different layouts for Go, Property, and Stock Market tiles.
     */
    public void configureForTile(Tile tile) {
        this.currentTile = tile;

        // Clear existing components
        this.removeAll(); // TODO: Is this used????? - Richard to Richard

        if (tile instanceof PropertyTile) {
            configureForPropertyTile((PropertyTile) tile);
        } else if (tile instanceof StockMarketTile) {
            configureForStockMarketTile((StockMarketTile) tile);
        } else if (tile instanceof GoTile) {
            configureForGoTile((GoTile) tile);
        } else {
            // Default configuration for any other tile types
            configureForGenericTile(tile);
        }

        this.revalidate();
        this.repaint();
    }

    /**
     * Configure layout for Property tiles (3-section layout)
     */
    private void configureForPropertyTile(PropertyTile property) {
        this.setLayout(new BorderLayout());

        mainLabel = new JLabel(property.getName(), SwingConstants.CENTER);
        mainLabel.setFont(mainLabel.getFont().deriveFont(10f));
        this.add(mainLabel, BorderLayout.NORTH);

        priceLabel = new JLabel("$" + (int)property.getPrice(), SwingConstants.CENTER);
        priceLabel.setFont(priceLabel.getFont().deriveFont(9f));
        this.add(priceLabel, BorderLayout.CENTER);

        ownerLabel = new JLabel("", SwingConstants.CENTER);
        ownerLabel.setFont(ownerLabel.getFont().deriveFont(8f));
        this.add(ownerLabel, BorderLayout.SOUTH);

        if (property.isOwned()) {
            ownerLabel.setText(property.getOwner().getName());
        } else {
            ownerLabel.setText("");
        }
    }

    /**
     * Configure layout for Stock Market tiles (single large text)
     */
    private void configureForStockMarketTile(StockMarketTile stockMarketTile) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.GREEN);

        mainLabel = new JLabel("Stock Market", SwingConstants.CENTER);
        mainLabel.setFont(mainLabel.getFont().deriveFont(Font.BOLD, 14f));
        mainLabel.setForeground(Color.WHITE);
        this.add(mainLabel, BorderLayout.CENTER);
    }

    /**
     * Configure layout for Go tiles (single large text)
     */
    private void configureForGoTile(GoTile goTile) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.RED);

        mainLabel = new JLabel("GO", SwingConstants.CENTER);
        mainLabel.setFont(mainLabel.getFont().deriveFont(Font.BOLD, 16f));
        mainLabel.setForeground(Color.WHITE);
        this.add(mainLabel, BorderLayout.CENTER);
    }

    /**
     * Configure layout for generic tiles (single large text)
     */
    private void configureForGenericTile(Tile tile) {
        this.setLayout(new BorderLayout());

        mainLabel = new JLabel(tile.getName(), SwingConstants.CENTER);
        mainLabel.setFont(mainLabel.getFont().deriveFont(Font.BOLD, 12f));
        this.add(mainLabel, BorderLayout.CENTER);
    }

    /**
     * Update the tile display (useful for property ownership changes)
     */
    public void updateDisplay() {
        if (currentTile != null) {
            configureForTile(currentTile);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tile View Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 200);

        TileView_OLD tileView = new TileView_OLD();

        frame.add(tileView);
        frame.setVisible(true);
    }
}
