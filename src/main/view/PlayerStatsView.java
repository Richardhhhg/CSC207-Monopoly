package main.view;

import main.entity.players.Player;
import main.entity.tiles.PropertyTile;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static main.constants.constants.DIMENSION_DE_LA_PSV_W;
import static main.constants.constants.DIMENSION_DE_LA_PSV_H;

public class PlayerStatsView extends JPanel{
    private List<Player> players;

    public PlayerStatsView(List<Player> players) {
        this.players = players;
        setPreferredSize(new Dimension(DIMENSION_DE_LA_PSV_W, DIMENSION_DE_LA_PSV_H));
        setBackground(Color.WHITE);
    }

    public void updatePlayers(List<Player> players) {
        this.players = players;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (players == null) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("Arial", Font.PLAIN, 17));

        FontMetrics fm = g2.getFontMetrics();
        int lineH = fm.getHeight();

        int y = 20;

        for (Player player : players) {
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
