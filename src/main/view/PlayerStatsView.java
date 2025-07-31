package main.view;

import main.entity.players.Player;
import main.entity.tiles.PropertyTile;
import main.interface_adapter.PlayerStatsPresenter;
import main.interface_adapter.PlayerStatsViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static main.Constants.Constants.DIMENSION_DE_LA_PSV_W;
import static main.Constants.Constants.DIMENSION_DE_LA_PSV_H;

public class PlayerStatsView extends JPanel{
    private PlayerStatsViewModel viewModel;

    // REFACTORED: presenter that builds the view model from players
    private final PlayerStatsPresenter presenter = new PlayerStatsPresenter();

    public PlayerStatsView(List<Player> players) {
        setPreferredSize(new Dimension(DIMENSION_DE_LA_PSV_W, DIMENSION_DE_LA_PSV_H));
        setOpaque(true);
        setBackground(Color.WHITE);
        updatePlayers(players);  // build initial VM
    }

    public void updatePlayers(List<Player> players) {
        this.viewModel = presenter.toViewModel(players);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (viewModel == null || viewModel.getCards() == null) return;
        Graphics2D g2 = (Graphics2D) g.create();

        int y = 20;
        int lineH = 18;

        for (var player : viewModel.getCards()) {
            if (player.isBankrupt()) continue;

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18f));
            g2.setColor(player.getColor());
            String info = player.getName() + " Information:";
            g2.drawString(info, 10, y);
            String networth = "Networth: " + player.getMoney() + "$";
            g2.drawString(networth, 180, y + 20);

            g2.setColor(player.getColor());
            int labelX = 180;
            int labelY = y + 40;
            g2.drawString("Properties:", labelX, labelY);

            List<PropertyTile> props = player.getProperties();
            if (props == null || props.isEmpty()) {
                g2.drawString("", labelX, labelY + lineH);
            } else {
                final int startX = 180;
                final int colWidth = 160;
                final int linesPerCol = 5;

                int col = 0;
                int line = 0;

                for (PropertyTile prop : props) {
                    int x = startX + col * colWidth;
                    int py = labelY + lineH * (line + 1);
                    g2.drawString("- " + prop.getName(), x, py);

                    line++;
                    if (line == linesPerCol) {
                        line = 0;
                        col++;
                    }
                }
            }



            if (player.getPortrait() != null) {
                int portraitSize = 155;
                int portraitX = 10;
                int portraitY = y + 10;

                g2.drawImage(player.getPortrait(), portraitX, portraitY, portraitSize, portraitSize, null);
                y += portraitSize + 50;
            } else {
                y += 80;
            }
        }
    }
}
