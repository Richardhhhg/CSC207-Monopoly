package main.view;

import javax.swing.*;
import java.awt.*;

/**
 * This Class displays the entire game view.
 */
public class GameView extends JFrame{
    private final BoardView boardView;
    private StockMarketView stockMarketView;

    // TODO: There is a ton of coupling here, fix it
    public GameView() {
        super("Stock Market Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBackground(Color.lightGray);
        setLayout(new BorderLayout());
        setVisible(true);

        this.boardView = new BoardView();
        // Pass this frame reference to BoardView so it can hide it when showing end screen
        this.boardView.setParentFrame(this);
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
        GameView game = new GameView();
        game.addBoard();
        game.showStockMarket();
    }
}