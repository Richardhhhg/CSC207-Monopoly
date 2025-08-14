package main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import main.app.GameHolder;
import main.constants.Constants;
import main.entity.Game;
import main.entity.players.AbstractPlayer;
import main.interface_adapter.player_stats.PlayerStatsController;
import main.interface_adapter.player_stats.PlayerStatsPresenter;
import main.interface_adapter.player_stats.PlayerStatsViewModel;
import main.use_case.game.GameMoveCurrentPlayer;
import main.use_case.game.GameNextTurn;
import main.use_case.player_stats.PlayerStatsInteractor;
import main.view.stock.StockMarketView;

/**
 * This Class displays the entire game view.
 */
public class GameView extends JFrame {
    private static final int PORTRAIT_ADJUST_HEIGHT = 30;

    private Game game;
    private JLayeredPane layeredPane;
    private DiceAnimator diceAnimator;
    private PlayerMovementAnimator playerMovementAnimator;
    private GameMoveCurrentPlayer gameMoveCurrentPlayer;

    private PlayerStatsViewModel playerStatsViewModel;
    private PlayerStatsPresenter playerStatsPresenter;
    private PlayerStatsInteractor playerStatsInputBoundary;
    private PlayerStatsController playerStatsController;
    private PlayerStatsView statsPanel;

    private BoardView boardView;
    private ButtonPanelView buttonPanelView;
    private int tileSize;

    // TODO: There is a ton of coupling here, fix it
    public GameView() {
        super(Constants.GAME_TITLE);
        this.game = GameHolder.getGame();
        if (this.game == null) {
            throw new IllegalStateException("Game not initialized");
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBackground(Color.lightGray);

        setLayout(new BorderLayout());

        this.diceAnimator = new DiceAnimator();
        this.playerMovementAnimator = new PlayerMovementAnimator();
        this.gameMoveCurrentPlayer = new GameMoveCurrentPlayer(game);

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(Constants.GAME_WIDTH, Constants.GAME_HEIGHT));

        setTileSize();

        add(layeredPane, BorderLayout.CENTER);

        drawBoard();
        drawDice();
        drawPlayerPortrait();
        drawPlayers();
        drawStatsPanel();

        drawButtonPanel();

        setVisible(true);
    }

    private void drawBoard() {
        boardView = new BoardView(game);
        boardView.setBounds(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        layeredPane.add(boardView, Integer.valueOf(0));
    }

    private void drawDice() {
        final int centerX = Constants.START_X + Constants.BOARD_SIZE / 2;
        final int centerY = Constants.START_Y + Constants.BOARD_SIZE / 2;

        final DiceView diceView = new DiceView(diceAnimator, tileSize);

        final int viewWidth = diceView.getWidth();
        final int viewHeight = diceView.getHeight();
        diceView.setBounds(centerX - viewWidth / 2, centerY - viewHeight / 2, viewWidth, viewHeight);

        layeredPane.add(diceView, Integer.valueOf(2));
        layeredPane.repaint();
    }

    private void drawButtonPanel() {
        buttonPanelView = new ButtonPanelView(
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
        for (Component c : getContentPane().getComponents()) {
            if (c instanceof ButtonPanelView) {
                remove(c);
            }
        }
        add(buttonPanelView, BorderLayout.EAST);
        revalidate();
        repaint();
    }

    private void setTileSize() {
        final int tilesPerSide = (game.getTiles().size() - 4) / 4 + 2;
        this.tileSize = Constants.BOARD_SIZE / tilesPerSide;
    }

    private void drawPlayerPortrait() {
        final Component[] components = layeredPane.getComponentsInLayer(2);
        for (Component c : components) {
            if (c instanceof PlayerPortraitView) {
                layeredPane.remove(c);
            }
        }

        final AbstractPlayer currentPlayer = game.getCurrentPlayer();
        if (currentPlayer != null && currentPlayer.getPortrait() != null) {
            final int centerX = Constants.START_X + Constants.BOARD_SIZE / 2;
            final int centerY = Constants.START_Y + Constants.BOARD_SIZE / 2;

            final int portraitSize = tileSize;
            final PlayerPortraitView portraitView = new PlayerPortraitView(currentPlayer.getPortrait(),
                    "Current Player:", portraitSize);

            final int x = centerX - tileSize - 10 + 80;
            final int y = centerY - tileSize / 2 - 150;

            portraitView.setBounds(x, y, portraitSize, portraitSize + PORTRAIT_ADJUST_HEIGHT);
            layeredPane.add(portraitView, Integer.valueOf(2));
            layeredPane.repaint();
        }
    }

    private void drawStatsPanel() {
        this.playerStatsViewModel = new PlayerStatsViewModel();
        this.playerStatsPresenter = new PlayerStatsPresenter(playerStatsViewModel);
        this.playerStatsInputBoundary = new PlayerStatsInteractor(playerStatsPresenter);
        this.playerStatsController = new PlayerStatsController(playerStatsInputBoundary);
        this.statsPanel = new PlayerStatsView(playerStatsViewModel, playerStatsController);
        this.statsPanel.refreshFrom(this.game);
        this.statsPanel.setBounds(Constants.GAME_WIDTH / 2, 0, Constants.STATS_WIDTH, Constants.GAME_HEIGHT);
        layeredPane.add(statsPanel, Integer.valueOf(Constants.STATS_LAYER));
        layeredPane.repaint();
    }

    private void drawPlayers() {
        final Component[] components = layeredPane.getComponentsInLayer(1);
        for (Component c : components) {
            if (c instanceof PlayerView) {
                layeredPane.remove(c);
            }
        }

        final List<AbstractPlayer> players = game.getPlayers();

        for (AbstractPlayer player : players) {
            if (player.isBankrupt()) {
                continue;
            }
            final PlayerView playerView = new PlayerView(player.getColor());
            final Point pos = game.getTilePosition(player.getPosition(), Constants.START_X,
                    Constants.START_Y, tileSize);
            final int offsetX = (players.indexOf(player) % 2) * 20;
            final int offsetY = (players.indexOf(player) / 2) * 20;
            playerView.setBounds(pos.x + offsetX, pos.y + offsetY, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);
            layeredPane.add(playerView, Integer.valueOf(1));
        }

        layeredPane.repaint();
    }

    private void displayStockMarket() {
        final StockMarketView stockMarketView = new StockMarketView(game.getCurrentPlayer(), false);
        stockMarketView.setVisible(true);
    }

    private void handleRollDice() {
        this.buttonPanelView.getRollDiceButton().setEnabled(false);

        diceAnimator.startDiceAnimation(
                this::drawDice,
                this::onDiceRollComplete
        );
    }

    private void onDiceRollComplete() {
        final AbstractPlayer currentPlayer = game.getCurrentPlayer();
        final int diceSum = diceAnimator.getLastDiceSum();

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
        final Component[] components = layeredPane.getComponentsInLayer(0);
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
        }

        this.buttonPanelView.updateStatus(
                game.getCurrentRound(),
                game.getCurrentPlayer().getName()
        );
        refreshStats();
        drawPlayerPortrait();

        this.buttonPanelView.getRollDiceButton().setEnabled(true);
        resetBoardView();
        drawPlayers();
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

    private void refreshStats() {
        if (this.statsPanel != null) {
            this.statsPanel.refreshFrom(this.game);
        }
    }
}
