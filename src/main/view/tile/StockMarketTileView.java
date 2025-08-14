package main.view.tile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import main.constants.Constants;
import main.interface_adapter.tile.StockMarketTileViewModel;

public class StockMarketTileView extends AbstractTileView {
    public StockMarketTileView(StockMarketTileViewModel viewModel) {
        super(viewModel);
        this.setBackground(Constants.STOCK_MKT_COLOR);
        this.setLayout(new BorderLayout());

        final JLabel mainLabel = new JLabel(this.getName(), SwingConstants.CENTER);
        mainLabel.setFont(mainLabel.getFont().deriveFont(Font.BOLD, Constants.STOCK_MKT_TILE_FONT_SIZE));
        mainLabel.setForeground(Color.WHITE);
        this.add(mainLabel, BorderLayout.CENTER);
    }
}
