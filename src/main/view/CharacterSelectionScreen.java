package main.view;

import main.app.GameHolder;
import main.entity.Game;
import main.interface_adapter.CharacterSelectionScreen.CharacterSelectionScreenAdapter;
import main.interface_adapter.CharacterSelectionScreen.CharacterSelectionScreenController;
import main.interface_adapter.CharacterSelectionScreen.CharacterSelectionScreenViewModel;
import main.interface_adapter.CharacterSelectionScreen.PlayerOutputData;

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
    private final List<PlayerSelection> selections = new ArrayList<>();

    public CharacterSelectionScreen() {
        var adapter = CharacterSelectionScreenAdapter.inject();
        this.controller = adapter.controller;
        this.viewModel = adapter.viewModel;
        initializeScreen();
    }

    private void initializeScreen() {
        setTitle("Select Your Characters");
        setSize(900, 700);
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

            Game game = new Game();
            game.setPlayersFromOutputData(validPlayers);
            game.initializeGame();
            GameHolder.setGame(game);
            dispose();
            new GameView().setVisible(true);
        });

        add(playerPanel, BorderLayout.CENTER);
        add(startGame, BorderLayout.SOUTH);
        setVisible(true);
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

