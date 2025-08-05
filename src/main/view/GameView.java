package main.view;

import main.Constants.Constants;
import main.entity.Game;
import main.entity.players.Player;
import main.interface_adapter.PlayerStats.PlayerStatsController;
import main.interface_adapter.PlayerStats.PlayerStatsPresenter;
import main.interface_adapter.PlayerStats.PlayerStatsViewModel;
import main.use_case.Game.GameMoveCurrentPlayer;
import main.use_case.Game.GameNextTurn;
import main.use_case.PlayerStats.PlayerStatsInteractor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.swing.*;
import java.awt.*;

/**
 * This Class displays the entire game view.
 */
public class GameView extends JFrame{
    private final Game game;
    private final JLayeredPane layeredPane;
    private final DiceAnimator diceAnimator;
    private final PlayerMovementAnimator playerMovementAnimator;
    private final GameMoveCurrentPlayer gameMoveCurrentPlayer;

    private PlayerStatsView statsPanel;

    private BoardView boardView;

    // TODO: There is a ton of coupling here, fix it
    public GameView() {
        super(Constants.GAME_TITLE);
        this.game = new Game();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBackground(Color.lightGray);
        setLayout(null);

        this.diceAnimator = new DiceAnimator();
        this.playerMovementAnimator = new PlayerMovementAnimator();
        this.gameMoveCurrentPlayer = new GameMoveCurrentPlayer(game);

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        setContentPane(layeredPane);

        drawBoard();
        drawDice();
        drawPlayerPortrait();
        drawPlayers();
        drawButtonPanel();
        drawStatsPanel();

        setVisible(true);
    }

    // TODO: This should probably be like separate class - Richard
    private void drawBoard() {
        boardView = new BoardView(game);
        boardView.setBounds(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        layeredPane.add(boardView, Integer.valueOf(0));
    }

    // TODO: There is a lot of repeated code, fix it - Richard
    private void drawDice() {
        int startX = 50;
        int startY = 8;
        int tilesPerSide = (game.getTiles().size() - 4) / 4 + 2;
        int tileSize = Constants.BOARD_SIZE / tilesPerSide;

        int centerX = startX + Constants.BOARD_SIZE / 2;
        int centerY = startY + Constants.BOARD_SIZE / 2;

        DiceView diceView = new DiceView(diceAnimator, tileSize);

        // Center the DiceView
        int viewWidth = diceView.getWidth();
        int viewHeight = diceView.getHeight();
        diceView.setBounds(centerX - viewWidth / 2, centerY - viewHeight / 2, viewWidth, viewHeight);

        layeredPane.add(diceView, Integer.valueOf(2));
        layeredPane.repaint();
    }

    private void drawButtonPanel() {
        ButtonPanelView buttonPanelView = new ButtonPanelView(game,
                () -> {
                    handleRollDice();
                    repaint();
                },
                () -> {
                    handleEndTurn();
                    repaint();
                },
                () -> {
                    displayStockMarket();
                    repaint();
                }
                );
        buttonPanelView.setBounds(Constants.GAME_WIDTH-200, 0, 200, Constants.GAME_HEIGHT);
        layeredPane.add(buttonPanelView, Integer.valueOf(4));
        layeredPane.repaint();
    }

    // TODO: There is a lot of repeated code, fix it - Richard
    private void drawPlayerPortrait() {
        int startX = 50;
        int startY = 8;

        // Remove existing portrait views (layer 2)
        Component[] components = layeredPane.getComponentsInLayer(2);
        for (Component c : components) {
            if (c instanceof PlayerPortraitView) {
                layeredPane.remove(c);
            }
        }

        Player currentPlayer = game.getCurrentPlayer();
        if (currentPlayer != null && currentPlayer.getPortrait() != null) {
            PlayerPortraitView portraitView = getPlayerPortraitView(startX, startY, currentPlayer);
            layeredPane.add(portraitView, Integer.valueOf(2));
            layeredPane.repaint();
        }
    }

    /**
     * Helper method for getting the PlayerPortraitView of a given player.
     * @param startX: the starting X coordinate for the board
     * @param startY: the starting Y coordinate for the board
     * @param currentPlayer: the current player whose portrait is to be displayed
     * @return PlayerPortraitView: the view of the player's portrait
     */
    @NotNull
    private PlayerPortraitView getPlayerPortraitView(int startX, int startY, Player currentPlayer) {
        int tilesPerSide = (game.getTiles().size() - 4) / 4 + 2;
        int tileSize = Constants.BOARD_SIZE / tilesPerSide;

        int centerX = startX + Constants.BOARD_SIZE / 2;
        int centerY = startY + Constants.BOARD_SIZE / 2;

        PlayerPortraitView portraitView = new PlayerPortraitView(currentPlayer.getPortrait(), "Current Player:", tileSize);

        int x = centerX - tileSize - 10 + 80;
        int y = centerY - tileSize / 2 - 150;

        portraitView.setBounds(x, y, tileSize, tileSize + 30);
        return portraitView;
    }


    private void drawStatsPanel() {
        PlayerStatsViewModel playerStatsViewModel = new PlayerStatsViewModel();
        PlayerStatsPresenter playerStatsPresenter = new PlayerStatsPresenter(playerStatsViewModel);
        PlayerStatsInteractor playerStatsInputBoundary = new PlayerStatsInteractor(playerStatsPresenter);
        PlayerStatsController playerStatsController = new PlayerStatsController(playerStatsInputBoundary);
        this.statsPanel = new PlayerStatsView(playerStatsViewModel, playerStatsController);
        this.statsPanel.refreshFrom(this.game);
        this.statsPanel.setBounds(Constants.GAME_WIDTH/2, 0, 600, Constants.GAME_HEIGHT);
        layeredPane.add(statsPanel, Integer.valueOf(3));
        layeredPane.repaint();
    }

    // TODO: This should probably be like separate class - Richard
    // TODO: This is really messy, fix later
    private void drawPlayers() {
        Component[] components = layeredPane.getComponentsInLayer(1);
        for (Component c : components) {
            if (c instanceof PlayerView) {
                layeredPane.remove(c);
            }
        }

        int startX = 50;
        int startY = 8;
        int tilesPerSide = (game.getTiles().size() - 4) / 4 + 2;
        int tileSize = Constants.BOARD_SIZE / tilesPerSide;

        List<Player> players = game.getPlayers();

        for (Player player : players) {
            PlayerView playerView = new PlayerView(player.getColor());
            Point pos = game.getTilePosition(player.getPosition(), startX, startY, tileSize);
            int offsetX = (players.indexOf(player) % 2) * 20;
            int offsetY = (players.indexOf(player) / 2) * 20;
            playerView.setBounds(pos.x + offsetX, pos.y + offsetY, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);
            layeredPane.add(playerView, Integer.valueOf(1));
        }

        layeredPane.repaint();
    }

    private void displayStockMarket() {
        StockMarketView stockMarketView = new StockMarketView(game.getCurrentPlayer());
        stockMarketView.setVisible(true);
    }

    private void handleRollDice() {
        ButtonPanelView.getRollDiceButton().setEnabled(false);

        diceAnimator.startDiceAnimation(
                // TODO: This could probably just repaint the DiceView rather than the whole GameView - Richard
                this::drawDice,
                this::onDiceRollComplete
        );
    }

    private void onDiceRollComplete() {
        Player currentPlayer = game.getCurrentPlayer();
        int diceSum = diceAnimator.getLastDiceSum();

        // Handle crossing GO bonus using GameBoard logic
        gameMoveCurrentPlayer.execute(diceSum);

        refreshStats();

        playerMovementAnimator.animatePlayerMovement(
                currentPlayer,
                diceSum,
                game.getTileCount(),
                this::drawPlayers,
                this::onPlayerMovementComplete
        );
    }

    private void resetBoardView() {
        Component[] components = layeredPane.getComponentsInLayer(0);
        for (Component c : components) {
            if (c instanceof BoardView) {
                layeredPane.remove(c);
            }
        }

        boardView = new BoardView(game);
        boardView.setBounds(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        layeredPane.add(boardView, Integer.valueOf(0));
    }

    private void onPlayerMovementComplete() {
        boardView.handleLandingOnTile();
        refreshStats();
    }

    private void handleEndTurn() {
        new GameNextTurn(game).execute();
        if (game.isGameOver()) {
            showEndScreen();
            return;
        }

        ButtonPanelView.updateStatus(
                game.getCurrentRound(),
                game.getCurrentPlayer().getName()
        );
        refreshStats();
        drawPlayerPortrait();

        ButtonPanelView.getRollDiceButton().setEnabled(true);
        resetBoardView();
        repaint();
    }

    private void showEndScreen() {
        this.setVisible(false);
        SwingUtilities.invokeLater(() -> {
            new EndScreen(
                    game.getPlayers(),
                    game.getGameEndReason(),
                    game.getCurrentRound()
            );
        });
    }

    /**
     * Refreshes the stats panel with the current game state.
     * To be used every time there is a change in Money or Properties.
     */
    private void refreshStats() {
        if (this.statsPanel != null) {
            this.statsPanel.refreshFrom(this.game);
        }
    }
}
