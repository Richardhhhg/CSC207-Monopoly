package view;

import entity.Property;
import Constants.Constants;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.Timer;
import java.util.Random;
import javax.swing.ImageIcon;

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
    // ——— Dice icons ———
    private final ImageIcon[] diceIcons = new ImageIcon[7];
    private final JLabel die1Label        = new JLabel();
    private final JLabel die2Label        = new JLabel();
    private final JLabel resultLabel      = new JLabel("Sum: 2", SwingConstants.CENTER);

    private final Random rand = new Random();
    private Timer         diceTimer;
    private int           frameCount;
    private int           finalD1, finalD2;

    private ArrayList<Property> properties;
    private List<Player> players;
    private int currentPlayerIndex = 0;

    private int lastDiceSum;

    public BoardView() {
        initializeGame();
        setBackground(Color.WHITE);
        setPreferredSize(new java.awt.Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT));
        setupUI();
        // assume you have dice1.png … dice6.png under /images on your classpath
        for (int i = 1; i <= 6; i++) {
            diceIcons[i] = new ImageIcon(getClass().getResource("images/dice" + i + ".png"));
            finalD1 = finalD2 = 1;
            lastDiceSum = 2;
        }
// start both dice showing “1”
        die1Label.setIcon(diceIcons[1]);
        die2Label.setIcon(diceIcons[1]);

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

        players = new ArrayList<>();
        for (int i = 0; i < PLAYER_COUNT; i++) {
            players.add(new Player(PLAYER_NAMES[i], PLAYER_COLORS[i], 1500));
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
        // ——— Roll-Dice side-panel (button only) ———
        JPanel side = new JPanel();
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.add(rollDiceButton);
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

// centre of the 500×500 island
        int centerX = startX + boardSize/2;
        int centerY = startY + boardSize/2;

// make each dice twice a tile wide (≈166px), so two side-by-side fit under 500px
        int diceSize = tileSize;
        int gap      = 10;

// compute their top-left corners
        int x1 = centerX - diceSize - gap/2;
        int x2 = centerX + gap/2;
        int y  = centerY - diceSize/2;

// draw them
        g2d.drawImage(diceIcons[finalD1].getImage(), x1, y, diceSize, diceSize, null);
        g2d.drawImage(diceIcons[finalD2].getImage(), x2, y, diceSize, diceSize, null);

        // ——— draw the sum underneath ———
        String sumText = "Sum: " + lastDiceSum;
        Font oldFont = g2d.getFont();
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2d.getFontMetrics();
        int textX = centerX - fm.stringWidth(sumText)/2;
        int textY = y + diceSize + fm.getAscent() + 5;
        g2d.drawString(sumText, textX, textY);
        g2d.setFont(oldFont);

        for (Player player : players) {
            Point pos = getTilePosition(player.getPosition(), startX, startY, tileSize);
            g2d.setColor(player.getColor());
            int playerSize = 15;
            int offsetX = (players.indexOf(player) % 2) * 20;
            int offsetY = (players.indexOf(player) / 2) * 20;
            g2d.fillOval(pos.x + offsetX + 5, pos.y + offsetY + 5, playerSize, playerSize);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(pos.x + offsetX + 5, pos.y + offsetY + 5, playerSize, playerSize);
        }
    }

    /** Animate 10 frames of random dice then settle on a final roll in the centre. */
    private void startDiceAnimation() {
        rollDiceButton.setEnabled(false);
        frameCount = 0;

        diceTimer = new Timer(100, null);
        diceTimer.addActionListener(evt -> {
            if (frameCount < 10) {
                // pick two random faces
                finalD1 = rand.nextInt(6) + 1;
                finalD2 = rand.nextInt(6) + 1;
                frameCount++;

                // redraw the board (which also draws dice in drawBoard)
                repaint();
            } else {
                diceTimer.stop();

                // final roll
                finalD1 = rand.nextInt(6) + 1;
                finalD2 = rand.nextInt(6) + 1;
                lastDiceSum = finalD1 + finalD2;

                Player currentPlayer = players.get(currentPlayerIndex);
                int newPosition = (currentPlayer.getPosition() + lastDiceSum) % BOARD_SIZE;
                currentPlayer.setPosition(newPosition);
                currentPlayerIndex = (currentPlayerIndex + 1) % PLAYER_COUNT;
                repaint();

                // one last redraw for the final faces
                repaint();

                rollDiceButton.setEnabled(true);
            }
        });
        diceTimer.start();
    }


    public int getLastDiceSum() {
        return lastDiceSum;
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

class Player {
private String name;
private Color color;
private int money;
private int position;

public Player(String name, Color color, int money) {
    this.name = name;
    this.color = color;
    this.money = money;
    this.position = 0;
}

// Getters and setters
public String getName() { return name; }
public Color getColor() { return color; }
public int getMoney() { return money; }
public void setMoney(int money) { this.money = money; }
public int getPosition() { return position; }
public void setPosition(int position) { this.position = position; }
}