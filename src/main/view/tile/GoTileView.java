package main.view.tile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import main.interface_adapter.tile.GoTileViewModel;

public class GoTileView extends AbstractTileView {
    private static final int GO_TILE_RED = 209;
    private static final int GO_TILE_GREEN = 48;
    private static final int GO_TILE_BLUE = 48;
    private static final int GO_TILE_ALPHA = 128;
    private static final float GO_TILE_FONT_SIZE = 16f;

    public GoTileView(GoTileViewModel viewModel) {
        super(viewModel);
        this.setBackground(new Color(GO_TILE_RED, GO_TILE_GREEN, GO_TILE_BLUE, GO_TILE_ALPHA));
        this.setLayout(new BorderLayout());

        final JLabel mainLabel = new JLabel("GO", SwingConstants.CENTER);
        mainLabel.setFont(mainLabel.getFont().deriveFont(Font.BOLD, GO_TILE_FONT_SIZE));
        mainLabel.setForeground(Color.WHITE);
        this.add(mainLabel, BorderLayout.CENTER);
    }
}
