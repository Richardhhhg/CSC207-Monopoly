package main.view;

import main.app.GameHolder;
import main.entity.Game;
import main.interface_adapter.CharacterSelectionScreen.CharacterSelectionScreenAdapter;
import main.interface_adapter.CharacterSelectionScreen.CharacterSelectionScreenController;
import main.interface_adapter.CharacterSelectionScreen.CharacterSelectionScreenViewModel;
import main.interface_adapter.CharacterSelectionScreen.PlayerOutputData;
import main.interface_adapter.CharacterSelectionScreen.BoardSizeController;
import main.interface_adapter.CharacterSelectionScreen.BoardSizePresenter;
import main.interface_adapter.CharacterSelectionScreen.BoardSizeViewModel;
import main.interface_adapter.Game.GameCreationController;
import main.use_case.BoardSizeSelection.BoardSizeSelection;
import main.use_case.BoardSizeSelection.BoardSizeSelection.BoardSize;
import main.use_case.Game.GameInitializeTiles;
import main.infrastructure.JsonPropertyDataSource;
import main.infrastructure.FallbackPropertyDataSource;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CharacterSelectionScreen extends JFrame {

    private final CharacterSelectionScreenController controller;
    private final CharacterSelectionScreenViewModel viewModel;
    private final BoardSizeController boardSizeController;
    private final BoardSizeViewModel boardSizeViewModel;
    private final GameCreationController gameCreationController;
    private final List<PlayerSelection> selections = new ArrayList<>();

    // Board size selection buttons
    private JButton smallButton;
    private JButton mediumButton;
    private JButton largeButton;

    public CharacterSelectionScreen() {
        var adapter = CharacterSelectionScreenAdapter.inject();
        this.controller = adapter.getController();
        this.viewModel = adapter.getViewModel();

        // Composition root - wire up dependencies here
        BoardSizeSelection boardSizeSelection = new BoardSizeSelection();
        BoardSizePresenter boardSizePresenter = new BoardSizePresenter();

        // Create GameInitializeTiles with both primary and fallback data sources
        GameInitializeTiles gameInitializeTiles = new GameInitializeTiles(
                new JsonPropertyDataSource(),
                new FallbackPropertyDataSource()
        );

        // Separate controllers for separate responsibilities
        this.boardSizeController = new BoardSizeController(
                boardSizeSelection,
                boardSizePresenter
        );

        this.gameCreationController = new GameCreationController(gameInitializeTiles);

        this.boardSizeViewModel = boardSizePresenter.getViewModel();

        // Initialize with default board size
        boardSizeController.initializeDefaultBoardSize();

        initializeScreen();
    }

    private void initializeScreen() {
        setTitle("Select Your Characters");
        setSize(900, 800); // Increased height to accommodate board size selection
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel playerPanel = new JPanel(new GridLayout(2, 2));

        for (int i = 0; i < 4; i++) {
            final int idx = i;

            JPanel playerSlot = new JPanel();
            playerSlot.setLayout(new BoxLayout(playerSlot, BoxLayout.Y_AXIS));
            playerSlot.setBorder(BorderFactory.createTitledBorder("Player " + (idx + 1)));

            JTextField nameField = new JTextField("Player " + (idx + 1));
            nameField.setMaximumSize(new Dimension(150, 25));
            nameField.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel portraitLabel = new JLabel();
            portraitLabel.setPreferredSize(new Dimension(100, 100));
            portraitLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            portraitLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JComboBox<String> characterDropdown = new JComboBox<>(new String[]{
                    "None", "Clerk", "Landlord", "Inheritor", "College Student", "Poor Man"
            });
            characterDropdown.setMaximumSize(new Dimension(150, 25));
            characterDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);

            Image defaultPortrait = PortraitProvider.getDefaultPortrait();
            if (defaultPortrait != null) {
                portraitLabel.setIcon(new ImageIcon(defaultPortrait.getScaledInstance(180, 180, Image.SCALE_SMOOTH)));
            }

            selections.add(new PlayerSelection(nameField, characterDropdown, portraitLabel));

            characterDropdown.addActionListener(e -> updatePlayerSelection(idx, nameField, characterDropdown, portraitLabel));

            nameField.getDocument().addDocumentListener(new DocumentListener() {
                private void update() {
                    updatePlayerSelection(idx, nameField, characterDropdown, portraitLabel);
                }

                public void insertUpdate(DocumentEvent e) { update(); }
                public void removeUpdate(DocumentEvent e) { update(); }
                public void changedUpdate(DocumentEvent e) { update(); }
            });

            playerSlot.add(portraitLabel);
            playerSlot.add(Box.createVerticalStrut(10));
            playerSlot.add(characterDropdown);
            playerSlot.add(Box.createVerticalStrut(10));
            playerSlot.add(nameField);
            playerSlot.add(Box.createVerticalStrut(10));
            playerPanel.add(playerSlot);
        }

        // Create board size selection panel
        JPanel boardSizePanel = createBoardSizePanel();

        JButton startGame = new JButton("Play!");
        startGame.addActionListener(e -> {
            List<PlayerOutputData> validPlayers = viewModel.getAllPlayers()
                    .stream()
                    .filter(p -> p != null && !"None".equals(p.getType()))
                    .collect(Collectors.toList());

            if (!this.controller.canStartGame()){
                JOptionPane.showMessageDialog(this, "Please select at least 2 characters.");
                return;
            }

            controller.confirmSelection();

            // Use GameCreationController for game creation (proper separation of concerns)
            Game game = gameCreationController.createGameWithBoardSize(
                validPlayers,
                boardSizeViewModel.getSelectedBoardSize()
            );

            GameHolder.setGame(game);
            dispose();
            new GameView().setVisible(true);
        });

        // Layout components
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(playerPanel, BorderLayout.CENTER);
        centerPanel.add(boardSizePanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
        add(startGame, BorderLayout.SOUTH);
        setVisible(true);
    }

    private JPanel createBoardSizePanel() {
        JPanel boardSizePanel = new JPanel();
        boardSizePanel.setLayout(new BoxLayout(boardSizePanel, BoxLayout.Y_AXIS));
        boardSizePanel.setBorder(BorderFactory.createTitledBorder("Board Size"));

        JLabel instructionLabel = new JLabel("Select board size:");
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        boardSizePanel.add(instructionLabel);
        boardSizePanel.add(Box.createVerticalStrut(10));

        JPanel buttonPanel = new JPanel(new FlowLayout());

        smallButton = new JButton(boardSizeViewModel.getSmallButtonText());
        mediumButton = new JButton(boardSizeViewModel.getMediumButtonText());
        largeButton = new JButton(boardSizeViewModel.getLargeButtonText());

        // Set initial selection (medium is default)
        updateButtonSelection(BoardSize.MEDIUM);

        smallButton.addActionListener(e -> {
            boardSizeController.selectBoardSize(BoardSize.SMALL);
            updateButtonSelection(BoardSize.SMALL);
        });

        mediumButton.addActionListener(e -> {
            boardSizeController.selectBoardSize(BoardSize.MEDIUM);
            updateButtonSelection(BoardSize.MEDIUM);
        });

        largeButton.addActionListener(e -> {
            boardSizeController.selectBoardSize(BoardSize.LARGE);
            updateButtonSelection(BoardSize.LARGE);
        });

        buttonPanel.add(smallButton);
        buttonPanel.add(mediumButton);
        buttonPanel.add(largeButton);

        boardSizePanel.add(buttonPanel);
        return boardSizePanel;
    }

    private void updateButtonSelection(BoardSize selectedSize) {
        // Reset all buttons
        smallButton.setBackground(null);
        mediumButton.setBackground(null);
        largeButton.setBackground(null);

        // Highlight selected button
        Color selectedColor = new Color(173, 216, 230); // Light blue
        switch (selectedSize) {
            case SMALL:
                smallButton.setBackground(selectedColor);
                break;
            case MEDIUM:
                mediumButton.setBackground(selectedColor);
                break;
            case LARGE:
                largeButton.setBackground(selectedColor);
                break;
        }

        // Make buttons opaque so background color shows
        smallButton.setOpaque(true);
        mediumButton.setOpaque(true);
        largeButton.setOpaque(true);
    }

    private void updatePlayerSelection(int index, JTextField nameField, JComboBox<String> dropdown, JLabel portraitLabel) {
        String name = nameField.getText();
        String type = (String) dropdown.getSelectedItem();
        controller.selectPlayer(index, name, type);

        PlayerOutputData data = viewModel.getPlayerData(index);
        if (data != null) {
            Image portrait = PortraitProvider.getPortrait(data.getType());
            if (portrait != null) {
                portraitLabel.setIcon(new ImageIcon(portrait.getScaledInstance(180, 180, Image.SCALE_SMOOTH)));
            } else {
                portraitLabel.setIcon(null);
            }
        }
    }

    private static class PlayerSelection {
        JTextField nameField;
        JComboBox<String> characterDropdown;
        JLabel portraitLabel;

        PlayerSelection(JTextField nameField, JComboBox<String> characterDropdown, JLabel portraitLabel) {
            this.nameField = nameField;
            this.characterDropdown = characterDropdown;
            this.portraitLabel = portraitLabel;
        }
    }
}
