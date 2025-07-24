package main.view;

import main.use_case.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static main.Constants.Constants.DIMONSION_H;
import static main.Constants.Constants.DIMONSION_W;

public class PlayerStatsView extends JPanel{
    private List<Player> players;

    public PlayerStatsView(List<Player> players) {
        this.players = players;
        setPreferredSize(new Dimension(DIMONSION_W, DIMONSION_H));
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

        int y = 20;

        for (Player player : players) {
            if (player.isBankrupt()) continue;

            g2.setColor(player.getColor());
            String info = player.getName() + " Information:";
            g2.drawString(info, 10, y);
            String networth = "Networth: " + player.getMoney() + "$";
            g2.drawString(networth, 180, y + 20);

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
