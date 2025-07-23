package main.view;

import main.entity.tiles.PropertyTile;
import main.entity.tiles.StockMarketTile;
import main.use_case.Tile;

import javax.swing.*;
import java.awt.*;

/**
 * Clean tile view - focused only on presentation
 */
public class TileView extends JPanel {
    private final JLabel nameLabel;
    private final JLabel priceLabel;
    private final JLabel ownerLabel;
    private final Tile tile;

    public TileView(Tile tile) {
        this.tile = tile;
        setupLayout();
        setupLabels();
    }

    private void setupLayout() {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(Color.LIGHT_GRAY);
    }

    private void setupLabels() {
        if (tile instanceof PropertyTile) {
            PropertyTile propertyTile = (PropertyTile) tile;

            nameLabel = createLabel(tile.getName(), 10f, BorderLayout.NORTH);
            priceLabel = createLabel("$" + propertyTile.getPrice(), 9f, BorderLayout.CENTER);
            ownerLabel = createLabel("", 8f, BorderLayout.SOUTH);

        } else if (tile instanceof StockMarketTile) {
            nameLabel = createLabel("Stock Market", 12f, BorderLayout.CENTER);
            priceLabel = null;
            ownerLabel = null;

        } else {
            nameLabel = createLabel(tile.getName(), 10f, BorderLayout.CENTER);
            priceLabel = null;
            ownerLabel = null;
        }
    }

    private JLabel createLabel(String text, float fontSize, String position) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(fontSize));
        this.add(label, position);
        return label;
    }

    public void updateOwnerDisplay(String ownerName) {
        if (ownerLabel != null) {
            ownerLabel.setText((ownerName != null && !ownerName.isBlank()) ? ownerName : "");
            repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tile View Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(200, 200);

            Tile propertyTile = new PropertyTile("Cool Name", 350, 50);
            TileView tileView = new TileView(propertyTile);
            tileView.updateOwnerDisplay("Player 1");

            frame.add(tileView);
            frame.setVisible(true);
        });
    }
}