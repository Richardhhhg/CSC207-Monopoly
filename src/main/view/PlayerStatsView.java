package main.view;

import main.entity.Game;
import main.interface_adapter.PlayerStats.PlayerStatsController;
import main.interface_adapter.PlayerStats.DisplayPlayer;
import main.interface_adapter.PlayerStats.DisplayProperty;
import main.interface_adapter.PlayerStats.PlayerStatsViewModel;

import javax.swing.*;
import java.awt.*;//
import java.util.List;

/**
 * Clean Architecture view WITHOUT PropertyChangeListener:
 * - Reads state from ViewModel only.
 * - Calls Controller (Input Boundary) to refresh.
 * - Rendering code is a strict refactor of the provided method.
 */
public class PlayerStatsView extends JPanel {

    private final PlayerStatsViewModel viewModel;
    private final PlayerStatsController controller;

    public PlayerStatsView(PlayerStatsViewModel viewModel, PlayerStatsController controller) {
        this.viewModel = viewModel;
        this.controller = controller;
    }

    /** External code supplies Game; we trigger the use case via controller and then repaint. */
    public void refreshFrom(Game game) {
        controller.execute(game);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        List<DisplayPlayer> players = viewModel.getState().getPlayers();
        if (players == null) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("ok", Font.PLAIN, 17));

        FontMetrics fm = g2.getFontMetrics();
        int lineH = fm.getHeight();

        int y = 20;

        for (DisplayPlayer player : players) {
            if (player.isBankrupt()) continue;

            g2.setColor(player.getColor());
            String info = player.getName() + " Information:";
            g2.drawString(info, 10, y);
            String networth = "Networth: " + player.getMoney() + "$";
            g2.drawString(networth, 180, y + 20);

            g2.setColor(player.getColor());
            int labelX = 180;
            int labelY = y + 40;
            g2.drawString("Properties:", labelX, labelY);

            List<DisplayProperty> props = player.getProperties();
            if (props == null || props.isEmpty()) {
                g2.drawString("", labelX, labelY + lineH);
            } else {
                final int startX = 180;
                final int colWidth = 160;
                final int linesPerCol = 5;

                int col = 0;
                int line = 0;

                for (DisplayProperty prop : props) {
                    int x = startX + col * colWidth;
                    int py = labelY + lineH * (line + 1);
                    g2.drawString("- " + prop.getPropertyName(), x, py);

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
