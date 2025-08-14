package main.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
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
import main.interface_adapter.boardSizeSelection.BoardSizeController;
import main.interface_adapter.boardSizeSelection.BoardSizePresenter;
import main.interface_adapter.boardSizeSelection.BoardSizeViewModel;
import main.interface_adapter.characterSelectionScreen.CharacterSelectionPlayerViewModel;
import main.interface_adapter.characterSelectionScreen.CharacterSelectionScreenAdapter;
import main.interface_adapter.characterSelectionScreen.CharacterSelectionScreenController;
import main.interface_adapter.characterSelectionScreen.CharacterSelectionScreenViewModel;
import main.interface_adapter.game.GameCreationController;
import main.use_case.boardSizeSelection.BoardSizeSelection;
import main.use_case.boardSizeSelection.BoardSizeSelection.BoardSize;

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
    private final BoardSizeSelectionPanelView boardSizePanel;
    private GameCreationController gameCreationController;
    private final List<PlayerSelection> selections = new ArrayList<>();

    public CharacterSelectionScreen() {
        final var adapter = CharacterSelectionScreenAdapter.inject();
        this.controller = adapter.getController();
        this.viewModel = adapter.getViewModel();

        // Set up board size selection dependencies
        final BoardSizePresenter boardSizePresenter = new BoardSizePresenter();
        final BoardSizeSelection boardSizeSelection = new BoardSizeSelection(boardSizePresenter);
        final BoardSizeController boardSizeController = new BoardSizeController(boardSizeSelection);
        final BoardSizeViewModel boardSizeViewModel = boardSizePresenter.getViewModel();

        // Initialize with default board size
        boardSizeViewModel.setSelectedBoardSize(BoardSize.MEDIUM);

        // Create the board size panel as a separate component
        this.boardSizePanel = new BoardSizeSelectionPanelView(boardSizeController, boardSizeViewModel);

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
                portraitLabel.setIcon(new ImageIcon(defaultPortrait.getScaledInstance(PORTRAIT_IMAGE_SIZE,
                        PORTRAIT_IMAGE_SIZE, Image.SCALE_SMOOTH)));
            }

            selections.add(new PlayerSelection(nameField, characterDropdown, portraitLabel));

            characterDropdown.addActionListener(event -> {
                updatePlayerSelection(idx, nameField,
                        characterDropdown, portraitLabel);
            });

            nameField.getDocument().addDocumentListener(new DocumentListener() {
                private void update() {
                    updatePlayerSelection(idx, nameField, characterDropdown, portraitLabel);
                }

                public void insertUpdate(DocumentEvent event) {
                    update();
                }

                public void removeUpdate(DocumentEvent event) {
                    update();
                }

                public void changedUpdate(DocumentEvent event) {
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

        final JButton startGame = new JButton("Play!");
        startGame.addActionListener(event -> {
            final List<CharacterSelectionPlayerViewModel> validPlayers = viewModel.getAllPlayers()
                    .stream()
                    .filter(player -> player != null && !"None".equals(player.getType()))
                    .collect(Collectors.toList());
            if (!this.controller.canStartGame()) {
                JOptionPane.showMessageDialog(this, "Please select at least 2 characters.");
                return;
            }
            controller.confirmSelection();

            // Lazy initialization of gameCreationController
            if (gameCreationController == null) {
                gameCreationController = new GameCreationController();
            }

            // Use the board size panel to get the selected board size
            final Game game = gameCreationController.createGameWithBoardSize(
                validPlayers,
                boardSizePanel.getSelectedBoardSize()
            );

            GameHolder.setGame(game);
            dispose();
            new GameView().setVisible(true);
        });

        final JButton charLore = new JButton("Character Traits");
        charLore.addActionListener(event -> {
            final String text = controller.getCharacterTraitsText();
            new CharacterTraitsDialog(this, text).setVisible(true);
        });

        add(playerPanel, BorderLayout.CENTER);

        // Create a panel to hold both board size selection and buttons
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(boardSizePanel);
        buttonPanel.add(startGame);
        buttonPanel.add(charLore);
        bottomPanel.add(buttonPanel);

        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void updatePlayerSelection(int index, JTextField nameField, JComboBox<String> dropdown,
                                       JLabel portraitLabel) {
        final String name = nameField.getText();
        final String type = (String) dropdown.getSelectedItem();
        controller.selectPlayer(index, name, type);

        final CharacterSelectionPlayerViewModel data = viewModel.getPlayerVm(index);
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
