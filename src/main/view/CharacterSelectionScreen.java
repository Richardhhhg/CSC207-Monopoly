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
import main.interface_adapter.CharacterSelectionScreen.CharacterSelectionPlayerViewModel;
import main.interface_adapter.CharacterSelectionScreen.CharacterSelectionScreenAdapter;
import main.interface_adapter.CharacterSelectionScreen.CharacterSelectionScreenController;
import main.interface_adapter.CharacterSelectionScreen.CharacterSelectionScreenViewModel;

/**
 * The CharacterSelectionScreen class provides a GUI for players to select their
 * name and character type before starting the Monopoly game.
 * This class interacts with the CharacterSelectionScreenController and ViewModel
 * to update player selections and validate readiness to start the game.
 */
public class CharacterSelectionScreen extends JFrame {

    private static final int CSCW = 900;
    private static final int CSCL = 700;
    private static final int MAXPLY = 4;
    private static final int NFW = 150;
    private static final int NFH = 25;
    private static final int PORTRALAB = 100;
    private static final int CDDSW = 150;
    private static final int CDDSH = 25;
    private static final int PORTRAITLAB = 180;
    private static final int TEN = 10;

    private final CharacterSelectionScreenController controller;
    private final CharacterSelectionScreenViewModel viewModel;
    private final List<PlayerSelection> selections = new ArrayList<>();

    public CharacterSelectionScreen() {
        final var adapter = CharacterSelectionScreenAdapter.inject();
        this.controller = adapter.getController();
        this.viewModel = adapter.getViewModel();
        initializeScreen();
    }

    private void initializeScreen() {
        setTitle("Select Your Characters");
        setSize(CSCW, CSCL);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        final JPanel playerPanel = new JPanel(new GridLayout(2, 2));
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        for (int i = 0; i < MAXPLY; i++) {
            final int idx = i;

            final JPanel playerSlot = new JPanel();
            playerSlot.setLayout(new BoxLayout(playerSlot, BoxLayout.Y_AXIS));
            playerSlot.setBorder(BorderFactory.createTitledBorder("Player " + (idx + 1)));

            final JTextField nameField = new JTextField("Player " + (idx + 1));
            nameField.setMaximumSize(new Dimension(NFW, NFH));
            nameField.setAlignmentX(Component.CENTER_ALIGNMENT);

            final JLabel portraitLabel = new JLabel();
            portraitLabel.setPreferredSize(new Dimension(PORTRALAB, PORTRALAB));
            portraitLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            portraitLabel.setHorizontalAlignment(SwingConstants.CENTER);

            final JComboBox<String> characterDropdown = new JComboBox<>(new String[]{"None",
                    "Clerk",
                    "Landlord",
                    "Inheritor",
                    "College Student",
                    "Poor Man"});
            characterDropdown.setMaximumSize(new Dimension(CDDSW, CDDSH));
            characterDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);

            final Image defaultPortrait = PortraitProvider.getDefaultPortrait();
            if (defaultPortrait != null) {
                portraitLabel.setIcon(new ImageIcon(defaultPortrait.getScaledInstance(PORTRAITLAB, PORTRAITLAB,
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
            playerSlot.add(Box.createVerticalStrut(TEN));
            playerSlot.add(characterDropdown);
            playerSlot.add(Box.createVerticalStrut(TEN));
            playerSlot.add(nameField);
            playerSlot.add(Box.createVerticalStrut(TEN));
            playerPanel.add(playerSlot);
        }

        final JButton startGame = new JButton("Play!");
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
            final Game game = new Game();
            game.setPlayersFromOutputData(validPlayers);
            game.initializeGame();
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

    private void updatePlayerSelection(int index, JTextField nameField,
                                       JComboBox<String> dropdown, JLabel portraitLabel) {
        final String name = nameField.getText();
        final String type = (String) dropdown.getSelectedItem();
        controller.selectPlayer(index, name, type);

        final CharacterSelectionPlayerViewModel data = viewModel.getPlayervm(index);
        if (data != null) {
            final Image portrait = data.getPortrait();
            if (portrait != null) {
                portraitLabel.setIcon(new ImageIcon(
                        portrait.getScaledInstance(PORTRAITLAB, PORTRAITLAB, Image.SCALE_SMOOTH)));
            }
            else {
                portraitLabel.setIcon(null);
            }
        }
    }

}
