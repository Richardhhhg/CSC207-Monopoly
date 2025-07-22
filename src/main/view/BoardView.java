package main.view;

import main.entity.*;
import main.use_case.Player;
import main.Constants.Constants;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;
import java.util.Random;
import javax.swing.ImageIcon;

import static main.Constants.Constants.FINISH_LINE_BONUS;

/**
 * BoardView is a JPanel that represents the main.view of the game board.
 * Note: THIS IS NOT THE ENTIRE WINDOW, just the board itself.
 */
public class BoardView extends JPanel {
    // Board Setup Variables
    private int tileCount;
    private static final int PLACEHOLDER_RENT = 50; // TODO: Replace with actual rent values Later
    private static final int PLAYER_COUNT = 4;
    private static final Color[] PLAYER_COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
    private static final String[] PLAYER_NAMES = {"Player 1", "Player 2", "Player 3", "Player 4"};

    // ——— Dice UI & state ———
    private final JButton rollDiceButton = new JButton("Roll Dice");
    private final JButton endTurnButton = new JButton("End Turn");
    // ——— Dice icons ———
    private final ImageIcon[] diceIcons = new ImageIcon[7];
    private final JLabel die1Label        = new JLabel();
    private final JLabel die2Label        = new JLabel();
    private final JLabel resultLabel      = new JLabel("Sum: 2", SwingConstants.CENTER);

    // ——— Portrait icons ———
    private Image currentPortrait;

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
        setPreferredSize(new java.awt.Dimension(Constants.BOARD_PANEL_WIDTH,
                        Constants.BOARD_PANEL_HEIGHT));
        currentPortrait = players.get(currentPlayerIndex).getPortrait();
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
     * TODO: Turn this into viewmodel for board main.view later, only here for testing purposes
     */
    private void initializeGame() {
        // Initialize properties
        properties = new ArrayList<>();
        // TODO: READ THIS FROM JSON FILE LATER
        String[] propertyNames = {
                "GO", "Mediterranean Ave", "Baltic Ave", "Reading Railroad",
                "Oriental Ave", "Vermont Ave", "Connecticut Ave", "St. James Place",
                "Tennessee Ave", "New York Ave", "Kentucky Ave", "Indiana Ave",
                "Illinois Ave", "Atlantic Ave", "Ventnor Ave", "Marvin Gardens",
                "Pacific Ave", "North Carolina Ave", "Pennsylvania Ave", "Boardwalk"
        };

        // TOOD: THIS SHOULD ALSO BE READ FROM JSON
        int[] prices = {0, 60, 60, 200, 100, 100, 120, 140, 140, 160, 180, 180, 200, 220, 220, 280, 300, 300, 320, 400};
        this.tileCount = propertyNames.length;

        for (int i = 0; i < tileCount; i++) {
            properties.add(new Property(propertyNames[i], prices[i], PLACEHOLDER_RENT));
        }

        players = new ArrayList<>();
        //for (int i = 0; i < PLAYER_COUNT; i++) {
           // players.add(new landlord(PLAYER_NAMES[i], PLAYER_COLORS[i]));
        //}
        DefaultPlayer defaultPlayer = new DefaultPlayer(PLAYER_NAMES[0], PLAYER_COLORS[0]);
        clerk clerk = new clerk(PLAYER_NAMES[1], PLAYER_COLORS[1]);
        collegeStudent collegeStudent = new collegeStudent(PLAYER_NAMES[2], PLAYER_COLORS[2]);
        landlord landlord = new landlord(PLAYER_NAMES[3], PLAYER_COLORS[3]);
        players.add(defaultPlayer);
        players.add(clerk);
        players.add(collegeStudent);
        players.add(landlord);
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
        boardPanel.setPreferredSize(new Dimension(Constants.BOARD_PANEL_WIDTH,
                Constants.BOARD_PANEL_HEIGHT));
        boardPanel.setBackground(Color.LIGHT_GRAY);

        add(boardPanel, BorderLayout.CENTER);
        // ——— Roll-Dice side-panel (button only) ———
        JPanel side = new JPanel();
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.add(rollDiceButton);
        side.add(endTurnButton);
        add(side, BorderLayout.EAST);

        // wire the button
        rollDiceButton.addActionListener(e -> startDiceAnimation());
        endTurnButton.addActionListener(e -> handleEndTurn());

    }

    /**
     * Creates the actual GUI for the board
     * Note: This is only the board not the entire window
     * @param g
     */
    private void drawBoard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int startX = 50;
        int startY = 50;
        int tilesPerSide = (this.properties.size()-4) / 4 + 2;
        int tileSize = Constants.BOARD_SIZE / tilesPerSide;

        // Draw properties around the board
        for (int i = 0; i < tileCount; i++) {
            Point pos = getTilePosition(i, startX, startY, tileSize);
            Property prop = properties.get(i);

            // Draw property tile
            g2d.setColor(Color.WHITE);

            g2d.fillRect(pos.x, pos.y, tileSize, tileSize);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(pos.x, pos.y, tileSize, tileSize);

            // Draw property name
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            FontMetrics fm = g2d.getFontMetrics();
            String name = prop.getName();
            int maxWidth = tileSize - 8; // Padding for text
            if (fm.stringWidth(name) > maxWidth) {
                // Split name into two lines at the nearest space
                int splitIndex = name.lastIndexOf(' ', name.length() / 2);
                if (splitIndex == -1 || splitIndex == 0 || splitIndex == name.length() - 1) {
                    splitIndex = name.indexOf(' ', name.length() / 2);
                    if (splitIndex == -1 || splitIndex == 0 || splitIndex == name.length() - 1) {
                        splitIndex = name.length() / 2;
                    }
                }
                String line1 = name.substring(0, splitIndex).trim();
                String line2 = name.substring(splitIndex).trim();
                int textX = pos.x + (tileSize - Math.max(fm.stringWidth(line1), fm.stringWidth(line2))) / 2;
                int textY1 = pos.y + tileSize / 2 - 8;
                int textY2 = pos.y + tileSize / 2 + 10;
                g2d.drawString(line1, textX, textY1);
                g2d.drawString(line2, textX, textY2);
            } else {
                int textX = pos.x + (tileSize - fm.stringWidth(name)) / 2;
                int textY = pos.y + tileSize / 2 + fm.getAscent() / 2 - 4;
                g2d.drawString(name, textX, textY);
            }
            // Draw price
            if (prop.getPrice() > 0) {
                String price = "$" + prop.getPrice();
                int priceX = pos.x + (tileSize - fm.stringWidth(price)) / 2;
                int priceY = pos.y + tileSize - 5;
                g2d.drawString(price, priceX, priceY);
            }
        }

        // TODO: MAKE SEPARATE METHOD FOR DRAWING DICE, preferably with a separate class
// centre of the 500×500 island
        int centerX = startX + Constants.BOARD_SIZE/2;
        int centerY = startY + Constants.BOARD_SIZE/2;

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

        if (currentPortrait != null) {
            int portraitSize = diceSize;
            int portraitX = x1 + 80;
            int portraitY = y - 150;
            String labelText = "Current Player:";
            Font oldFont = g2d.getFont();
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            FontMetrics fm = g2d.getFontMetrics();
            int labelX = portraitX + (portraitSize - fm.stringWidth(labelText)) / 2;
            int labelY = portraitY - 10;
            g2d.setColor(Color.BLACK);
            g2d.drawString(labelText, labelX, labelY);

            g2d.drawImage(currentPortrait, portraitX, portraitY, portraitSize, portraitSize, null);
        }

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

                int oldPosition = currentPlayer.getPosition() % tileCount;

                if (oldPosition + lastDiceSum >= tileCount) {
                    currentPlayer.addMoney(FINISH_LINE_BONUS);
                }
                animatePlayerMovement(currentPlayer, lastDiceSum);

            }
        });
        diceTimer.start();
    }

    private void animatePlayerMovement(Player player, int steps) {
        Timer moveTimer = new Timer(300, null); // delay should be a constant, we can tweak what feels "right"
        final int[] movesLeft = {steps};

        moveTimer.addActionListener(e -> {
            if (movesLeft[0] > 0) {
                int newPosition = (player.getPosition() + 1) % tileCount;
                player.setPosition(newPosition);
                movesLeft[0]--;
                repaint();
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        moveTimer.start();
    }

    private void handleEndTurn() {
        int startIndex = currentPlayerIndex;
        boolean foundNext = false;
        players.get(currentPlayerIndex).applyTurnEffects();

        for (int i = 1; i <= players.size(); i++) {
            int nextIndex = (startIndex + i) % players.size();
            if (!players.get(nextIndex).isBankrupt()) {
                currentPlayerIndex = nextIndex;
                foundNext = true;
                break;
            }
        }

        if (!foundNext) {
            JOptionPane.showMessageDialog(this, "Game Over: All players are bankrupt.");
            rollDiceButton.setEnabled(false);
            return;
        }

        rollDiceButton.setEnabled(true);
        currentPortrait = players.get(currentPlayerIndex).getPortrait();
        repaint();
    }



    public int getLastDiceSum() {
        return lastDiceSum;
    }

    private Point getTilePosition(int position, int startX, int startY, int tileSize) {
         // TODO: This is very messy, clean it up
        int tilesPerSide = this.tileCount / 4; // Number of tiles on each side of the board - 1
        int cool_number = tilesPerSide * tileSize;

        if (position >= 0 && position <= tilesPerSide) {
            // Bottom row (left to right)
            return new Point(startX + position * tileSize, startY + cool_number);
        } else if (position >= (tilesPerSide+1) && position <= tilesPerSide*2) {
            // Right column (bottom to top)
            return new Point(startX + cool_number, startY + cool_number - (position - tilesPerSide) * tileSize);
        } else if (position >= (tilesPerSide*2 + 1) && position <= tilesPerSide*3) {
            // Top row (right to left)
            return new Point(startX + cool_number - (position - tilesPerSide*2) * tileSize, startY);
        } else {
            // Left column (top to bottom)
            return new Point(startX, startY + (position - tilesPerSide*3) * tileSize);
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