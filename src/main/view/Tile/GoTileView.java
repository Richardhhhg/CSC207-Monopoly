package main.view.Tile;

import main.entity.tiles.GoTile;
import main.interface_adapter.Tile.GoTileViewModel;

import javax.swing.*;
import java.awt.*;

public class GoTileView extends TileView {
    public GoTileView(GoTileViewModel viewModel) {
        super(viewModel);
        this.setBackground(new Color(209, 48, 48, 128));
        this.setLayout(new BorderLayout());

        mainLabel = new JLabel("GO", SwingConstants.CENTER);
        mainLabel.setFont(mainLabel.getFont().deriveFont(Font.BOLD, 16f));
        mainLabel.setForeground(Color.WHITE);
        this.add(mainLabel, BorderLayout.CENTER);
    }
}
