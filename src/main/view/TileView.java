package main.view;

import main.entity.tiles.Property;

import javax.swing.*;
import java.awt.*;

/**
 * Visual representation of a single board tile.
 * Renders name, price and highlights owner.
 */

//This was CHATGPT's idea to make a tile view for the board.
public class TileView extends JPanel {
    private final Property model;
    private final JLabel nameLabel;
    private final JLabel priceLabel;

    /**
     * @param model the Property entity backing this tile
     */
    public TileView(Property model) {
        this.model = model;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // always use a neutral background here
        setBackground(Color.LIGHT_GRAY);

        nameLabel = new JLabel(model.getName(), SwingConstants.CENTER);
        nameLabel.setFont(nameLabel.getFont().deriveFont(10f));
        add(nameLabel, BorderLayout.CENTER);

        priceLabel = new JLabel(
                model.getPrice() > 0 ? "$" + model.getPrice() : "",
                SwingConstants.CENTER
        );
        priceLabel.setFont(priceLabel.getFont().deriveFont(9f));
        add(priceLabel, BorderLayout.SOUTH);
    }

    /**
     * Visually marks or clears ownership by changing the border.
     *
     * @param ownerColor the player color, or null to clear
     */
    public void setOwnerColor(Color ownerColor) {
        if (ownerColor != null) {
            setBorder(BorderFactory.createLineBorder(ownerColor, 3));
        } else {
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        repaint();
    }
}
