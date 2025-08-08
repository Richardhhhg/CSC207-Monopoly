package main.view.Tile;

import main.interface_adapter.Tile.StockMarketTileViewModel;

import javax.swing.*;
import java.awt.*;

public class StockMarketTileView extends TileView {
    public StockMarketTileView(StockMarketTileViewModel viewModel) {
        super(viewModel);
        this.setBackground(new Color(29, 145, 56, 128));
        this.setLayout(new BorderLayout());

        mainLabel = new JLabel(this.name, SwingConstants.CENTER);
        mainLabel.setFont(mainLabel.getFont().deriveFont(Font.BOLD, 14f));
        mainLabel.setForeground(Color.WHITE);
        this.add(mainLabel, BorderLayout.CENTER);
    }
}
