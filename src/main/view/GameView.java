package main.view;

import main.entity.Game;

import javax.swing.*;
import java.awt.*;

/**
 * This Class displays the entire game view.
 */
public class GameView extends JFrame{
    private final BoardView boardView;
    private StockMarketView stockMarketView;
    private final Game game;

    // TODO: There is a ton of coupling here, fix it
    public GameView(Game game) {
        super("Stock Market Game");
        this.game = game;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBackground(Color.lightGray);
        setLayout(new BorderLayout());
        setVisible(true);

        this.boardView = new BoardView(this.game);
        // Pass this frame reference to BoardView so it can hide it when showing end screen
        this.boardView.setParentFrame(this);
        addBoard();
    }

    /**
     * Adds the board view to the game view.
     */
    public void addBoard() {
        this.add(boardView, BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * Displays the stock market JFrame
     */
    public void showStockMarket() {
        stockMarketView.setVisible(true);
    }

    /**
     * Main method to run the game view.
     * This is just for testing purposes.
     */
    public static void main(String[] args) {
        Game newgame = new Game();
        GameView game = new GameView(newgame);
        game.addBoard();
        game.showStockMarket();
    }
}