package main.view;

import main.use_case.Tile;

import javax.swing.*;
import java.awt.*;

/**
 * Generic visual component for a single board tile.
 * Provides basic text display functionality that can be configured by presenters.
 */
public class TileView extends JPanel {
    private final JLabel mainLabel;
    private final JLabel priceLabel;
    private final JLabel ownerLabel;

    /**
     * Create a generic tile view with basic layout
     */
    public TileView() {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(Color.LIGHT_GRAY);

        mainLabel = new JLabel("", SwingConstants.CENTER);
        mainLabel.setFont(mainLabel.getFont().deriveFont(10f));
        this.add(mainLabel, BorderLayout.NORTH);

        priceLabel = new JLabel("", SwingConstants.CENTER);
        priceLabel.setFont(priceLabel.getFont().deriveFont(9f));
        this.add(priceLabel, BorderLayout.CENTER);

        ownerLabel = new JLabel("", SwingConstants.CENTER);
        ownerLabel.setFont(ownerLabel.getFont().deriveFont(8f));
        this.add(ownerLabel, BorderLayout.SOUTH);
    }

    /**
     * Set the main text (tile name)
     */
    public void setMainText(String text) {
        mainLabel.setText(text != null ? text : "");
        repaint();
    }

    /**
     * Set the price text
     */
    public void setPriceText(String text) {
        priceLabel.setText(text != null ? text : "");
        repaint();
    }

    /**
     * Set the owner text
     */
    public void setOwnerText(String text) {
        ownerLabel.setText(text != null ? text : "");
        repaint();
    }

    /**
     * Hide price and owner labels (for non-property tiles)
     */
    public void hidePropertyInfo() {
        priceLabel.setVisible(false);
        ownerLabel.setVisible(false);
        repaint();
    }

    /**
     * Show price and owner labels (for property tiles)
     */
    public void showPropertyInfo() {
        priceLabel.setVisible(true);
        ownerLabel.setVisible(true);
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tile View Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 200);

        TileView tileView = new TileView();
        tileView.setMainText("Cool Name");
        tileView.setPriceText("$350");
        tileView.setOwnerText("Player 1");

        frame.add(tileView);
        frame.setVisible(true);
    }
}
