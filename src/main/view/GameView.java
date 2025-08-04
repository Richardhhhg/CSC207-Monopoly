package main.view;

import main.Constants.Constants;
import main.entity.Game;
import main.entity.players.Player;
import java.util.List;

import javax.swing.*;
import java.awt.*;

/**
 * This Class displays the entire game view.
 */
public class GameView extends JFrame{
    private Game game;
    private JLayeredPane layeredPane;

    // TODO: There is a ton of coupling here, fix it
    public GameView() {
        super(Constants.GAME_TITLE);
        this.game = new Game();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBackground(Color.lightGray);
        setLayout(null);

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        setContentPane(layeredPane);

        drawBoard();
        drawPlayers();

        // Drawing Dice
        DiceAnimator diceAnimator = new DiceAnimator();

        setVisible(true);
    }

    // TODO: This should probably be like separate class - Richard
    private void drawBoard() {
        BoardView boardView = new BoardView(game);
        boardView.setBounds(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        layeredPane.add(boardView, Integer.valueOf(0));
    }

    // TODO: This should probably be like separate class - Richard
    // TODO: This is really messy, fix later
    private void drawPlayers() {
        // draws players
        int startX = 50;
        int startY = 8;
        int tilesPerSide = (game.getTiles().size()-4) / 4 + 2;
        int tileSize = Constants.BOARD_SIZE / tilesPerSide;

        List<Player> players = game.getPlayers();

        for (Player player : game.getPlayers()) {
            PlayerView playerView = new PlayerView(player.getColor());
            Point pos = game.getTilePosition(player.getPosition(), startX, startY, tileSize);
            int offsetX = (players.indexOf(player) % 2) * 20;
            int offsetY = (players.indexOf(player) / 2) * 20;
            playerView.setBounds(pos.x + offsetX, pos.y + offsetY, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);
            layeredPane.add(playerView, Integer.valueOf(1));
        }
    }
}