package main.view;

import main.Constants.Constants;

import javax.swing.*;
import java.awt.*;

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
        g.fillOval(5, 5, 15, 15);
        g.setColor(Color.BLACK);
        g.drawOval(5, 5, 15, 15);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Player View Test");
        PlayerView playerView = new PlayerView(Color.RED);
        frame.add(playerView);
        frame.setSize(200, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}