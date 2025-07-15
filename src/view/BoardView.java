package view;

import entity.Property;
import Constants.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * BoardView is a JPanel that represents the view of the game board.
 * Note: THIS IS NOT THE ENTIRE WINDOW, just the board itself.
 */
public class BoardView extends JPanel {
    private static final int BOARD_SIZE = 20;
    private static final int PLAYER_COUNT = 4;
    private static final Color[] PLAYER_COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
    private static final String[] PLAYER_NAMES = {"Player 1", "Player 2", "Player 3", "Player 4"};
    private static final int PLACEHOLDER_RENT = 50; // TODO: Replace with actual rent values Later

    // ——— Dice UI & state ———
    private final JButton rollDiceButton = new JButton("Roll Dice");
    private final JLabel  die1Label      = new JLabel("Die 1: 1", SwingConstants.CENTER);
    private final JLabel  die2Label      = new JLabel("Die 2: 1", SwingConstants.CENTER);
    private final JLabel  resultLabel    = new JLabel("Sum: 2", SwingConstants.CENTER);

    private final Random rand = new Random();
    private Timer         diceTimer;
    private int           frameCount;
    private int           finalD1, finalD2;

    private ArrayList<Property> properties;

    public BoardView() {
        initializeGame();
        setBackground(Color.WHITE);
        setPreferredSize(new java.awt.Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT));
        setupUI();
    }

    /**
     * Initializes Properties
     * TODO: Turn this into viewmodel for board view later, only here for testing purposes
     */
    private void initializeGame() {
        // Initialize properties
        properties = new ArrayList<>();
        String[] propertyNames = {
                "GO", "Mediterranean Ave", "Baltic Ave", "Reading Railroad",
                "Oriental Ave", "Vermont Ave", "Connecticut Ave", "St. James Place",
                "Tennessee Ave", "New York Ave", "Kentucky Ave", "Indiana Ave",
                "Illinois Ave", "Atlantic Ave", "Ventnor Ave", "Marvin Gardens",
                "Pacific Ave", "North Carolina Ave", "Pennsylvania Ave", "Boardwalk"
        };

        int[] prices = {0, 60, 60, 200, 100, 100, 120, 140, 140, 160, 180, 180, 200, 220, 220, 280, 300, 300, 320, 400};

        for (int i = 0; i < BOARD_SIZE; i++) {
            properties.add(new Property(propertyNames[i], prices[i], PLACEHOLDER_RENT));
        }
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        // Create board panel
        JPanel boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
            }
        };
        boardPanel.setPreferredSize(new Dimension(600, 600));
        boardPanel.setBackground(Color.WHITE);

        add(boardPanel, BorderLayout.CENTER);
        // ——— Roll-Dice side-panel ———
        JPanel side = new JPanel();
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.add(rollDiceButton);
        side.add(Box.createVerticalStrut(10));
        side.add(die1Label);
        side.add(Box.createVerticalStrut(5));
        side.add(die2Label);
        side.add(Box.createVerticalStrut(10));
        side.add(resultLabel);
        add(side, BorderLayout.EAST);

// wire the button
        rollDiceButton.addActionListener(e -> startDiceAnimation());

    }

    /**
     * Creates the actual GUI for the board
     * Note: This is only the board not the entire window
     * @param g
     */
    private void drawBoard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int boardSize = 500;
        int tileSize = boardSize / 6;
        int startX = 50;
        int startY = 50;

        // Draw properties around the board
        for (int i = 0; i < BOARD_SIZE; i++) {
            Point pos = getTilePosition(i, startX, startY, tileSize);
            Property prop = properties.get(i);

            // Draw property tile
            if (prop.getOwner() != null) {
                g2d.setColor(Color.WHITE);
            } else {
                g2d.setColor(Color.LIGHT_GRAY);
            }

            g2d.fillRect(pos.x, pos.y, tileSize, tileSize);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(pos.x, pos.y, tileSize, tileSize);

            // Draw property name
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            FontMetrics fm = g2d.getFontMetrics();
            String name = prop.getName();
            if (name.length() > 8) {
                name = name.substring(0, 8) + "...";
            }
            int textX = pos.x + (tileSize - fm.stringWidth(name)) / 2;
            int textY = pos.y + tileSize / 2;
            g2d.drawString(name, textX, textY);

            // Draw price
            if (prop.getPrice() > 0) {
                String price = "$" + prop.getPrice();
                int priceX = pos.x + (tileSize - fm.stringWidth(price)) / 2;
                int priceY = pos.y + tileSize - 5;
                g2d.drawString(price, priceX, priceY);
            }
        }
    }

    /** Animate 10 frames of random dice then settle on a final roll. */
    private void startDiceAnimation() {
        rollDiceButton.setEnabled(false);
        frameCount = 0;

        diceTimer = new Timer(100, null);
        diceTimer.addActionListener(evt -> {
            if (frameCount < 10) {
                int r1 = rand.nextInt(6) + 1;
                int r2 = rand.nextInt(6) + 1;
                die1Label.setText("Die 1: " + r1);
                die2Label.setText("Die 2: " + r2);
                frameCount++;
            } else {
                diceTimer.stop();
                finalD1 = rand.nextInt(6) + 1;
                finalD2 = rand.nextInt(6) + 1;
                die1Label.setText("Die 1: " + finalD1);
                die2Label.setText("Die 2: " + finalD2);
                int sum = finalD1 + finalD2;
                resultLabel.setText("Sum: " + sum);
                // TODO: if you want to move a token, invoke your controller or
                // call state.moveCurrentPlayer(sum) here.
                rollDiceButton.setEnabled(true);
            }
        });
        diceTimer.start();
    }

    private Point getTilePosition(int position, int startX, int startY, int tileSize) {
        int boardSize = 5 * tileSize;

        if (position >= 0 && position <= 5) {
            // Bottom row (left to right)
            return new Point(startX + position * tileSize, startY + boardSize);
        } else if (position >= 6 && position <= 10) {
            // Right column (bottom to top)
            return new Point(startX + boardSize, startY + boardSize - (position - 5) * tileSize);
        } else if (position >= 11 && position <= 15) {
            // Top row (right to left)
            return new Point(startX + boardSize - (position - 10) * tileSize, startY);
        } else {
            // Left column (top to bottom)
            return new Point(startX, startY + (position - 15) * tileSize);
        }
    }

    /**
     * For Testing the Board View on it's own
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Monopoly Board");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new BoardView());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}