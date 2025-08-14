package main.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import main.constants.Constants;

public class PlayerView extends JComponent {
    private final Color color;

    public PlayerView(Color color) {
        this.color = color;
        setOpaque(false);
        setSize(Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillOval(Constants.PLAYER_X, Constants.PLAYER_Y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        g.setColor(Color.BLACK);
        g.drawOval(Constants.PLAYER_X, Constants.PLAYER_Y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
    }
}
