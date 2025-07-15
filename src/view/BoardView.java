package view;

import entity.Property;
import Constants.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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

    private ArrayList<Property> properties;

    public BoardView() {
        initializeGame();
        setPreferredSize(new java.awt.Dimension(Constants.BOARD_PANEL_WIDTH, Constants.BOARD_PANEL_HEIGHT));
        setupUI();
    }

    /**
     * Initializes Properties
     * TODO: Turn this into viewmodel for board view later, only here for testing purposes
     */
    private void initializeGame() {
        // Initialize properties
        properties = new ArrayList<>();
        String[] propertyNames = { // TODO: Read this from json file later
                "GO", "Mediterranean Ave", "Baltic Ave", "Reading Railroad",
                "Oriental Ave", "Vermont Ave", "Connecticut Ave", "St. James Place",
                "Tennessee Ave", "New York Ave", "Kentucky Ave", "Indiana Ave",
                "Illinois Ave", "Atlantic Ave", "Ventnor Ave", "Marvin Gardens",
                "Pacific Ave", "North Carolina Ave", "Pennsylvania Ave", "Boardwalk"
        };
        // TODO: This is also to be read from a json file
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
        boardPanel.setPreferredSize(new Dimension(Constants.BOARD_PANEL_WIDTH, Constants.BOARD_PANEL_HEIGHT));
        boardPanel.setBackground(Color.lightGray);

        add(boardPanel, BorderLayout.CENTER);
    }

    /**
     * Creates the actual GUI for the board
     * Note: This is only the board not the entire window
     * @param g
     */
    private void drawBoard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int tilesPerSide = (this.properties.size()-4) / 4 + 2;

        int tileSize = Constants.BOARD_SIZE / tilesPerSide;
        int startX = 50;
        int startY = 50;

        // Draw properties around the board
        // TODO: Clean up this code, it's literally all chatgpt code
        for (int i = 0; i < BOARD_SIZE; i++) {
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