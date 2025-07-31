package main.view;

import main.entity.tiles.PropertyTile;
import main.entity.players.Player;
import main.entity.*;
import main.use_case.Game.GameNextTurn;
import main.Constants.Constants;
import main.use_case.Tile;
import main.interface_adapter.Property.PropertyController;
import main.interface_adapter.Property.PropertyPresenter;

import javax.swing.*;
import java.awt.*;

/**
 * BoardView is a JPanel that represents the main view of the game board.
 * This is a passive view that delegates business logic to controllers.
 */
public class BoardView extends JPanel {
    // Components responsible for specific functionality
    private final Game game;
    private final BoardRenderer boardRenderer;
    private final DiceController diceController;
    private final PlayerMovementAnimator playerMovementAnimator;
    private JFrame parentFrame; // Reference to parent frame for end screen

    // Controllers and Presenters
    private final PropertyController propertyController;
    private final PropertyPresenter propertyPresenter;

    // ——— Dice UI & state ———
    private final JButton rollDiceButton = new JButton("Roll Dice");
    private final JButton endTurnButton = new JButton("End Turn");
    private final JButton stockMarketButton = new JButton("Stock Market");
    private final JLabel roundLabel = new JLabel("Round: 1");
    private final JLabel turnLabel = new JLabel("Turns: 0");

    // player stats
    private final PlayerStatsView statsPanel;

    public BoardView() {
        this.game = new Game();
        this.diceController = new DiceController();
        this.boardRenderer = new BoardRenderer();
        this.playerMovementAnimator = new PlayerMovementAnimator();
        this.statsPanel = new PlayerStatsView(game.getPlayers());

        // Initialize presenter and controller following Clean Architecture
        this.propertyPresenter = new PropertyPresenter(this, statsPanel, game.getPlayers());
        this.propertyController = new PropertyController(propertyPresenter);

        // Set controller as the landing handler for all property tiles
        setupPropertyLandingHandlers();

        setPreferredSize(new java.awt.Dimension(Constants.BOARD_PANEL_WIDTH,
                Constants.BOARD_PANEL_HEIGHT));
        setupUI();
    }

    /**
     * Configure property tiles to use the controller for landing events
     */
    private void setupPropertyLandingHandlers() {
        for (PropertyTile property : game.getTiles()) {
            property.setLandingHandler(propertyController);
        }
    }

    public void setParentFrame(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Constants.BOARD_PANEL_WIDTH, Constants.BOARD_PANEL_HEIGHT));

        // Create board panel - delegate rendering to BoardRenderer
        JPanel boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                boardRenderer.drawBoard(g, game, diceController);
            }
        };
        boardPanel.setPreferredSize(new Dimension(Constants.BOARD_PANEL_WIDTH, Constants.BOARD_PANEL_HEIGHT));
        boardPanel.setBackground(Color.LIGHT_GRAY);

        add(boardPanel, BorderLayout.WEST);
        add(statsPanel, BorderLayout.CENTER);

        // Roll-Dice side-panel
        JPanel side = new JPanel();
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));

        // Game status labels
        roundLabel.setFont(new Font("Arial", Font.BOLD, 16));
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        side.add(roundLabel);
        side.add(turnLabel);
        side.add(Box.createVerticalStrut(20));

        side.add(rollDiceButton);
        side.add(endTurnButton);
        side.add(stockMarketButton);
        add(side, BorderLayout.EAST);

        // wire the button
        rollDiceButton.addActionListener(e -> handleRollDice());
        endTurnButton.addActionListener(e -> handleEndTurn());
        stockMarketButton.addActionListener(e -> displayStockMarket());

        updateStatusLabels();
    }

    /**
     * Delegate stock market display to appropriate component
     */
    private void displayStockMarket() {
        StockMarketView stockMarketView = new StockMarketView(game.getCurrentPlayer());
        stockMarketView.setVisible(true);
    }

    /**
     * Update game status display
     */
    private void updateStatusLabels() {
        roundLabel.setText("Round: " + game.getCurrentRound());
        turnLabel.setText("Turns: " + game.getTotalTurns());
    }

    /**
     * Handle dice roll - delegate to DiceController
     */
    private void handleRollDice() {
        rollDiceButton.setEnabled(false);

        // Use DiceController for dice animation and logic
        diceController.startDiceAnimation(
            this::repaint, // Animation frame callback
            this::onDiceRollComplete // Completion callback
        );
    }

    /**
     * Handle dice roll completion - delegate movement to game logic
     */
    private void onDiceRollComplete() {
        Player currentPlayer = game.getCurrentPlayer();
        int diceSum = diceController.getLastDiceSum();

        // Handle crossing GO bonus using Game logic
        game.moveCurrentPlayer(diceSum);

        // Use PlayerMovementAnimator for movement animation
        playerMovementAnimator.animatePlayerMovement(
            currentPlayer,
            diceSum,
            game.getTileCount(),
            this::repaint, // Movement step callback
            this::onPlayerMovementComplete // Completion callback
        );
    }

    /**
     * Handle player movement completion - delegate to tile logic
     */
    private void onPlayerMovementComplete() {
        handleLandingOnTile();
    }

    /**
     * Handle landing on tile - delegate to tile's onLanding method
     * The tile will use the appropriate controller (PropertyController for properties)
     */
    private void handleLandingOnTile() {
        Player currentPlayer = game.getCurrentPlayer();
        int position = currentPlayer.getPosition();
        Tile tile = game.getPropertyAt(position);

        if (tile != null) {
            // Use tile's built-in landing logic which will call the appropriate controller
            tile.onLanding(currentPlayer);
        }
    }

    /**
     * Handle end turn - delegate to game logic
     */
    private void handleEndTurn() {
        new GameNextTurn(game).execute();
        updateStatusLabels();
        statsPanel.updatePlayers(game.getPlayers());

        if (game.isGameOver()) {
            showEndScreen();
            return;
        }

        rollDiceButton.setEnabled(true);
        repaint();
    }

    /**
     * Show end screen when game is over
     */
    private void showEndScreen() {
        rollDiceButton.setEnabled(false);
        endTurnButton.setEnabled(false);

        // Hide the parent frame if it exists
        if (parentFrame != null) {
            parentFrame.setVisible(false);
        }

        // Show the end screen
        SwingUtilities.invokeLater(() -> {
            new EndScreen(
                    game.getPlayers(),
                    game.getGameEndReason(),
                    game.getCurrentRound()
            );
        });
    }

    // Getters for components that need access
    public int getLastDiceSum() {
        return diceController.getLastDiceSum();
    }

    public Game getGame() {
        return game;
    }

    /**
     * For Testing the Board View on its own
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Monopoly Board");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            BoardView boardView = new BoardView();
            boardView.setParentFrame(frame);
            frame.add(boardView);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

