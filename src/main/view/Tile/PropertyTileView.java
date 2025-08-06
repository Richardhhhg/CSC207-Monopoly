package main.view.Tile;

import main.entity.tiles.PropertyTile;
import main.entity.tiles.Tile;
import main.interface_adapter.Tile.PropertyTileViewModel;

import javax.swing.*;
import java.awt.*;

public class PropertyTileView extends TileView {
    private final JLabel priceLabel;
    private final JLabel ownerLabel;
    private final PropertyTileViewModel viewModel;

    public PropertyTileView(PropertyTileViewModel viewModel) {
        super(viewModel);
        this.viewModel = viewModel;

        setLayout(new BorderLayout());
        mainLabel = new JLabel(viewModel.getName(), SwingConstants.CENTER);
        mainLabel.setFont(mainLabel.getFont().deriveFont(10f));
        add(mainLabel, BorderLayout.NORTH);

        priceLabel = new JLabel("$" + viewModel.getPrice(), SwingConstants.CENTER);
        priceLabel.setFont(priceLabel.getFont().deriveFont(9f));
        add(priceLabel, BorderLayout.CENTER);

        ownerLabel = new JLabel("", SwingConstants.CENTER);
        ownerLabel.setFont(ownerLabel.getFont().deriveFont(8f));
        add(ownerLabel, BorderLayout.SOUTH);

        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int tileSize = Math.min(getWidth(), getHeight());

        if (viewModel.isOwned()) {
            ownerLabel.setText(viewModel.getOwner());
            Color ownerColor = viewModel.getOwnerColor();
            Color backgroundTint = new Color(ownerColor.getRed(), ownerColor.getGreen(),
                    ownerColor.getBlue(), 120);
            g2d.setColor(backgroundTint);
            g2d.fillRect(0, 0, tileSize, tileSize);

            g2d.setColor(ownerColor);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRect(1, 1, tileSize - 2, tileSize - 2);
            g2d.setStroke(new BasicStroke(1));
        } else {
            ownerLabel.setText("");
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, tileSize, tileSize);
        }

        g2d.dispose();
    }

    public static void main(String[] args) {
        // Example usage
        PropertyTileViewModel viewModel = new PropertyTileViewModel(
                "Park Place", 350, "John Doe", 50, Color.BLUE);
        PropertyTileView tileView = new PropertyTileView(viewModel);

        JFrame frame = new JFrame("Property Tile View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(tileView);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }
}
