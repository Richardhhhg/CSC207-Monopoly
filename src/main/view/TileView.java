package main.view;

import main.entity.tiles.PropertyTile;
import main.entity.tiles.StockMarketTile;
import main.use_case.Tile;

import javax.swing.*;
import java.awt.*;

/**
 * Visual component for a single board tile.
 * Shows tile name, price and owner name.
 */
public class TileView extends JPanel {
    private final JLabel nameLabel;
    private final JLabel priceLabel;
    private final JLabel ownerLabel;

    /**
     * @param tile the tile entity to render
     */
    public TileView(Tile tile) {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(Color.LIGHT_GRAY);

        // TODO: This logic of different types of tiles should be handled in the presenter i think
        if (tile instanceof PropertyTile) {
            nameLabel = new JLabel(tile.getName(), SwingConstants.CENTER);
            nameLabel.setFont(nameLabel.getFont().deriveFont(10f));
            this.add(nameLabel, BorderLayout.NORTH);

            priceLabel = new JLabel(
                    "$" + ((PropertyTile) tile).getPrice(),
                    SwingConstants.CENTER
            );
            priceLabel.setFont(priceLabel.getFont().deriveFont(9f));
            this.add(priceLabel, BorderLayout.CENTER);

            ownerLabel = new JLabel("", SwingConstants.CENTER);
            ownerLabel.setFont(ownerLabel.getFont().deriveFont(8f));
            this.add(ownerLabel, BorderLayout.SOUTH);
        } else if (tile instanceof StockMarketTile) {
            nameLabel = new JLabel("Stock Market", SwingConstants.CENTER);
            nameLabel.setFont(nameLabel.getFont().deriveFont(12f));
            this.add(nameLabel, BorderLayout.CENTER);
            priceLabel = null;
            ownerLabel = null;
        } else {
            nameLabel = new JLabel(tile.getName(), SwingConstants.CENTER);
            nameLabel.setFont(nameLabel.getFont().deriveFont(10f));
            this.add(nameLabel, BorderLayout.CENTER);
            priceLabel = null;
            ownerLabel = null;
        }
    }

    /**
     * Display or clear the owner’s name.
     *
     * @param ownerName the player’s name, or null/empty to clear
     */
    // TODO: This should be handled by the presenter, not the view
    // TODO: This is specific to property tiles
    public void setOwnerName(String ownerName) {
        if (ownerLabel != null) {
            ownerLabel.setText((ownerName != null && !ownerName.isBlank())
                    ? ownerName
                    : ""
            );
            repaint();
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tile View Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 200);

        Tile propertyTile = new PropertyTile("Cool Name", 350, 50);
        TileView tileView = new TileView(propertyTile);
        tileView.setOwnerName("Player 1");

        frame.add(tileView);
        frame.setVisible(true);
    }
}
