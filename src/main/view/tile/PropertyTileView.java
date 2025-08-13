package main.view.tile;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import main.interface_adapter.tile.PropertyTileViewModel;

public class PropertyTileView extends AbstractTileView {
    private static final float MAIN_FONT_SIZE = 10f;
    private static final float PRICE_FONT_SIZE = 9f;
    private static final float OWNER_FONT_SIZE = 8f;
    private static final int BACKGROUND_ALPHA = 120;
    private static final int BORDER_WIDTH = 3;
    private static final int BORDER_OFFSET = 1;
    private static final int BORDER_SIZE_ADJUSTMENT = 2;

    private final JLabel priceLabel;
    private final JLabel ownerLabel;
    private final PropertyTileViewModel viewModel;

    public PropertyTileView(PropertyTileViewModel viewModel) {
        super(viewModel);
        this.viewModel = viewModel;

        setLayout(new BorderLayout());
        final JLabel mainLabel = new JLabel(viewModel.getName(), SwingConstants.CENTER);
        mainLabel.setFont(mainLabel.getFont().deriveFont(MAIN_FONT_SIZE));
        add(mainLabel, BorderLayout.NORTH);

        priceLabel = new JLabel("$" + viewModel.getPrice(), SwingConstants.CENTER);
        priceLabel.setFont(priceLabel.getFont().deriveFont(PRICE_FONT_SIZE));
        add(priceLabel, BorderLayout.CENTER);

        ownerLabel = new JLabel("", SwingConstants.CENTER);
        ownerLabel.setFont(ownerLabel.getFont().deriveFont(OWNER_FONT_SIZE));
        add(ownerLabel, BorderLayout.SOUTH);

        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g.create();

        final int tileSize = Math.min(getWidth(), getHeight());

        if (viewModel.isOwned()) {
            ownerLabel.setText(viewModel.getOwner());
            final Color ownerColor = viewModel.getOwnerColor();
            final Color backgroundTint = new Color(ownerColor.getRed(), ownerColor.getGreen(),
                    ownerColor.getBlue(), BACKGROUND_ALPHA);
            g2d.setColor(backgroundTint);
            g2d.fillRect(0, 0, tileSize, tileSize);

            g2d.setColor(ownerColor);
            g2d.setStroke(new BasicStroke(BORDER_WIDTH));
            g2d.drawRect(BORDER_OFFSET, BORDER_OFFSET, tileSize - BORDER_SIZE_ADJUSTMENT,
                         tileSize - BORDER_SIZE_ADJUSTMENT);
            g2d.setStroke(new BasicStroke(BORDER_OFFSET));
        }
        else {
            ownerLabel.setText("");
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, tileSize, tileSize);
        }

        g2d.dispose();
    }
}
