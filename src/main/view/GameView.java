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

import java.util.List;

import javax.swing.*;
import java.awt.*;

/**
 * This Class displays the entire game view.
 */
public class GameView extends JFrame{
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
    private int tileSize;

    // TODO: There is a ton of coupling here, fix it
    public GameView() {
        super(Constants.GAME_TITLE);
        this.game = new Game();
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
        int centerX = Constants.START_X + Constants.BOARD_SIZE / 2;
        int centerY = Constants.START_Y + Constants.BOARD_SIZE / 2;

        DiceView diceView = new DiceView(diceAnimator, tileSize);

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
        int tilesPerSide = (game.getTiles().size() - 4) / 4 + 2;
        this.tileSize = Constants.BOARD_SIZE / tilesPerSide;
    }

    private void drawPlayerPortrait() {
        Component[] components = layeredPane.getComponentsInLayer(2);
        for (Component c : components) {
            if (c instanceof PlayerPortraitView) {
                layeredPane.remove(c);
            }
        }

        Player currentPlayer = game.getCurrentPlayer();
        if (currentPlayer != null && currentPlayer.getPortrait() != null) {
            int centerX = Constants.START_X + Constants.BOARD_SIZE / 2;
            int centerY = Constants.START_Y + Constants.BOARD_SIZE / 2;

            int portraitSize = tileSize;
            PlayerPortraitView portraitView = new PlayerPortraitView(currentPlayer.getPortrait(), "Current Player:", portraitSize);

            int x = centerX - tileSize - 10 + 80;
            int y = centerY - tileSize / 2 - 150;

            portraitView.setBounds(x, y, portraitSize, portraitSize + 30);
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
        this.statsPanel.setBounds(Constants.GAME_WIDTH/2, 0, 600, Constants.GAME_HEIGHT);
        layeredPane.add(statsPanel, Integer.valueOf(3));
        layeredPane.repaint();
    }

    private void drawPlayers() {
        Component[] components = layeredPane.getComponentsInLayer(1);
        for (Component c : components) {
            if (c instanceof PlayerView) {
                layeredPane.remove(c);
            }
        }

        List<Player> players = game.getPlayers();

        for (Player player : players) {
            if (player.isBankrupt()) {
                continue;
            }
            PlayerView playerView = new PlayerView(player.getColor());
            Point pos = game.getTilePosition(player.getPosition(), Constants.START_X, Constants.START_Y, tileSize);
            int offsetX = (players.indexOf(player) % 2) * 20;
            int offsetY = (players.indexOf(player) / 2) * 20;
            playerView.setBounds(pos.x + offsetX, pos.y + offsetY, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);
            layeredPane.add(playerView, Integer.valueOf(1));
        }

        layeredPane.repaint();
    }

    private void displayStockMarket() {
        StockMarketView stockMarketView = new StockMarketView(game.getCurrentPlayer(), false);
        stockMarketView.setVisible(true);
    }

    private void handleRollDice() {
        ButtonPanelView.getRollDiceButton().setEnabled(false);

        diceAnimator.startDiceAnimation(
                this::drawDice,
                this::onDiceRollComplete // Completion callback
        );
    }

    private void onDiceRollComplete() {
        Player currentPlayer = game.getCurrentPlayer();
        int diceSum = diceAnimator.getLastDiceSum();

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
