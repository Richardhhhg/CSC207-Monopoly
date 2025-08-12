package main.view.tile;

import main.interface_adapter.tile.TileViewModel;

import javax.swing.*;
import java.awt.*;

public abstract class TileView extends JPanel {
    protected String name;
    protected JLabel mainLabel;

    public TileView(TileViewModel tileViewModel) {
        this.name = tileViewModel.getName();
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(java.awt.Color.LIGHT_GRAY);
    }
}
