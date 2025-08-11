package main.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
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
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main.app.GameHolder;
import main.entity.Game;
import main.interface_adapter.characterSelectionScreen.CharacterSelectionPlayerViewModel;
import main.interface_adapter.characterSelectionScreen.CharacterSelectionScreenAdapter;
import main.interface_adapter.characterSelectionScreen.CharacterSelectionScreenController;
import main.interface_adapter.characterSelectionScreen.CharacterSelectionScreenViewModel;

/**
 * The CharacterSelectionScreen class provides a GUI for players to select their
 * name and character type before starting the Monopoly game.
 * This class interacts with the CharacterSelectionScreenController and ViewModel
 * to update player selections and validate readiness to start the game.
 */
public class CharacterSelectionScreen extends JFrame {

    private static final int SCREEN_WIDTH = 900;
    private static final int SCREEN_HEIGHT = 700;
    private static final int MAX_PLAYERS = 4;
    private static final int NAME_FIELD_WIDTH = 150;
    private static final int NAME_FIELD_HEIGHT = 25;
    private static final int PORTRAIT_LABEL_SIZE = 100;
    private static final int DROPDOWN_WIDTH = 150;
    private static final int DROPDOWN_HEIGHT = 25;
    private static final int PORTRAIT_IMAGE_SIZE = 180;
    private static final int VERTICAL_SPACING = 10;

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
        final var adapter = CharacterSelectionScreenAdapter.inject();
        this.controller = adapter.getController();
        this.viewModel = adapter.getViewModel();

        // Composition root - wire up dependencies here
        BoardSizeSelection boardSizeSelection = new BoardSizeSelection();
        BoardSizePresenter boardSizePresenter = new BoardSizePresenter();

        // Separate controllers for separate responsibilities
        this.boardSizeController = new BoardSizeController(
                boardSizeSelection,
                boardSizePresenter
        );

        this.gameCreationController = new GameCreationController();

        this.boardSizeViewModel = boardSizePresenter.getViewModel();

        // Initialize with default board size
        boardSizeController.initializeDefaultBoardSize();

        initializeScreen();
    }

    private void initializeScreen() {
        setTitle("Select Your Characters");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        final JPanel playerPanel = new JPanel(new GridLayout(2, 2));
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        for (int i = 0; i < MAX_PLAYERS; i++) {
            final int idx = i;

            final JPanel playerSlot = new JPanel();
            playerSlot.setLayout(new BoxLayout(playerSlot, BoxLayout.Y_AXIS));
            playerSlot.setBorder(BorderFactory.createTitledBorder("Player " + (idx + 1)));

            final JTextField nameField = new JTextField("Player " + (idx + 1));
            nameField.setMaximumSize(new Dimension(NAME_FIELD_WIDTH, NAME_FIELD_HEIGHT));
            nameField.setAlignmentX(Component.CENTER_ALIGNMENT);

            final JLabel portraitLabel = new JLabel();
            portraitLabel.setPreferredSize(new Dimension(PORTRAIT_LABEL_SIZE, PORTRAIT_LABEL_SIZE));
            portraitLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            portraitLabel.setHorizontalAlignment(SwingConstants.CENTER);

            final JComboBox<String> characterDropdown = new JComboBox<>(new String[]{
                    "None",
                    "Clerk",
                    "Landlord",
                    "Inheritor",
                    "College Student",
                    "Poor Man"});
            characterDropdown.setMaximumSize(new Dimension(DROPDOWN_WIDTH, DROPDOWN_HEIGHT));
            characterDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);

            final Image defaultPortrait = PortraitProvider.getDefaultPortrait();
            if (defaultPortrait != null) {
                portraitLabel.setIcon(new ImageIcon(defaultPortrait.getScaledInstance(PORTRAIT_IMAGE_SIZE, PORTRAIT_IMAGE_SIZE,
                        Image.SCALE_SMOOTH)));
            }

            selections.add(new PlayerSelection(nameField, characterDropdown, portraitLabel));

            characterDropdown.addActionListener(e -> updatePlayerSelection(idx, nameField,
                    characterDropdown, portraitLabel));

            nameField.getDocument().addDocumentListener(new DocumentListener() {
                private void update() {
                    updatePlayerSelection(idx, nameField, characterDropdown, portraitLabel);
                }

                public void insertUpdate(DocumentEvent e) {
                    update();
                }

                public void removeUpdate(DocumentEvent e) {
                    update();
                }

                public void changedUpdate(DocumentEvent e) {
                    update();
                }
            });

            playerSlot.add(portraitLabel);
            playerSlot.add(Box.createVerticalStrut(VERTICAL_SPACING));
            playerSlot.add(characterDropdown);
            playerSlot.add(Box.createVerticalStrut(VERTICAL_SPACING));
            playerSlot.add(nameField);
            playerSlot.add(Box.createVerticalStrut(VERTICAL_SPACING));
            playerPanel.add(playerSlot);
        }

        // Create board size selection panel
        JPanel boardSizePanel = createBoardSizePanel();

        JButton startGame = new JButton("Play!");
        startGame.addActionListener(e -> {
            final List<CharacterSelectionPlayerViewModel> validPlayers = viewModel.getAllPlayers()
                    .stream()
                    .filter(p -> p != null && !"None".equals(p.getType()))
                    .collect(Collectors.toList());
            if (!this.controller.canStartGame()) {
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

        final JButton charLore = new JButton("Character Traits");
        charLore.addActionListener(e -> {
            final String text = controller.getCharacterTraitsText();
            new CharacterTraitsDialog(this, text).setVisible(true);
        });

        add(playerPanel, BorderLayout.CENTER);
        buttonPanel.add(startGame);
        buttonPanel.add(charLore);
        add(buttonPanel, BorderLayout.SOUTH);
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

        final CharacterSelectionPlayerViewModel data = viewModel.getPlayervm(index);
        if (data != null) {
            final Image portrait = data.getPortrait();
            if (portrait != null) {
                portraitLabel.setIcon(new ImageIcon(
                        portrait.getScaledInstance(PORTRAIT_IMAGE_SIZE, PORTRAIT_IMAGE_SIZE, Image.SCALE_SMOOTH)));
            }
            else {
                portraitLabel.setIcon(null);
            }
        }
    }

}
