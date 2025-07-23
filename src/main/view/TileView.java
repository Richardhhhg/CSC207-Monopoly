package main.view;

import main.entity.tiles.Property;

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
     * @param model the Property entity to render
     */
    public TileView(Property model) {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(Color.LIGHT_GRAY);

        nameLabel = new JLabel(model.getName(), SwingConstants.CENTER);
        nameLabel.setFont(nameLabel.getFont().deriveFont(10f));
        this.add(nameLabel, BorderLayout.NORTH);

        priceLabel = new JLabel(
                model.getPrice() > 0 ? "$" + model.getPrice() : "",
                SwingConstants.CENTER
        );
        priceLabel.setFont(priceLabel.getFont().deriveFont(9f));
        this.add(priceLabel, BorderLayout.CENTER);

        ownerLabel = new JLabel("", SwingConstants.CENTER);
        ownerLabel.setFont(ownerLabel.getFont().deriveFont(8f));
        this.add(ownerLabel, BorderLayout.SOUTH);
    }

    /**
     * Display or clear the owner’s name.
     *
     * @param ownerName the player’s name, or null/empty to clear
     */
    public void setOwnerName(String ownerName) {
        ownerLabel.setText((ownerName != null && !ownerName.isBlank())
                ? ownerName
                : ""
        );
        repaint();
    }
}
