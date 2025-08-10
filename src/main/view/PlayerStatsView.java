package main.view;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import main.entity.Game;
import main.interface_adapter.PlayerStats.DisplayPlayer;
import main.interface_adapter.PlayerStats.DisplayProperty;
import main.interface_adapter.PlayerStats.PlayerStatsController;
import main.interface_adapter.PlayerStats.PlayerStatsViewModel;

/**
 * Shows player stats on the screen.
 * Reads data from the view model and asks the controller to update it.
 */
public class PlayerStatsView extends JPanel {

    private static final int TWTY = 20;
    private static final int SWTN = 17;
    private static final int TEN = 10;
    private static final int IOOO = 180;
    private static final int SO = 50;
    private static final int OO0 = 80;

    private final PlayerStatsViewModel viewModel;
    private final PlayerStatsController controller;

    public PlayerStatsView(PlayerStatsViewModel viewModel, PlayerStatsController controller) {
        this.viewModel = viewModel;
        this.controller = controller;
    }

    /**
     * Updates the view with the latest player stats from the given game.
     * Calls the controller to run the Player Stats use case with the provided
     * game and then repaints the view to show the updated information.
     * @param game the current game to get player stats from
     */
    public void refreshFrom(Game game) {
        controller.execute(game);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        final List<DisplayPlayer> players = viewModel.getState().getPlayers();
        if (players == null) {
            return;
        }

        final Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("ok", Font.PLAIN, SWTN));

        final FontMetrics fm = g2.getFontMetrics();
        final int lineH = fm.getHeight();

        int y = TWTY;

        for (DisplayPlayer player : players) {
            if (player.isBankrupt()) {
                continue;
            }

            g2.setColor(player.getColor());
            final String info = player.getName() + " Information:";
            g2.drawString(info, TEN, y);
            final String networth = "Networth: " + player.getMoney() + "$";
            g2.drawString(networth, IOOO, y + TWTY);

            g2.setColor(player.getColor());
            final int labelX = 180;
            final int labelY = y + 40;
            g2.drawString("Properties:", labelX, labelY);

            final List<DisplayProperty> props = player.getProperties();
            if (props == null || props.isEmpty()) {
                g2.drawString("", labelX, labelY + lineH);
            }
            else {
                final int startX = 180;
                final int colWidth = 160;
                final int linesPerCol = 5;

                int col = 0;
                int line = 0;

                for (DisplayProperty prop : props) {
                    final int x = startX + col * colWidth;
                    final int py = labelY + lineH * (line + 1);
                    g2.drawString("- " + prop.getPropertyName(), x, py);

                    line++;
                    if (line == linesPerCol) {
                        line = 0;
                        col++;
                    }
                }
            }

            if (player.getPortrait() != null) {
                final int portraitSize = 155;
                final int portraitX = 10;
                final int portraitY = y + 10;

                g2.drawImage(player.getPortrait(), portraitX, portraitY, portraitSize, portraitSize, null);
                y += portraitSize + SO;
            }
            else {
                y += OO0;
            }
        }
    }
}
