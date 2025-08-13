package main.view.tile;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.interface_adapter.tile.AbstractTileViewModel;

public abstract class AbstractTileView extends JPanel {
    private String name;
    private JLabel mainLabel;

    public AbstractTileView(AbstractTileViewModel tileViewModel) {
        this.name = tileViewModel.getName();
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(java.awt.Color.LIGHT_GRAY);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
