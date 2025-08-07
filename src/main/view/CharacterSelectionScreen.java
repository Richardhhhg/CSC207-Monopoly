package main.view;

import main.app.GameHolder;
import main.entity.Game;
import main.entity.players.CharacterFactory;
import main.entity.players.Player;
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

            controller.selectPlayer(idx, "Player " + (idx + 1), "None");
            PlayerOutputData data = viewModel.getPlayerData(idx);
            if (data != null && data.getPortrait() != null) {
                portraitLabel.setIcon(new ImageIcon(data.getPortrait().getScaledInstance(180, 180, Image.SCALE_SMOOTH)));
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
            List<Player> finalPlayers = new ArrayList<>();
            for (int i = 0; i < selections.size(); i++) {
                PlayerOutputData data = viewModel.getPlayerData(i);
                if (data != null && !"None".equals(data.getType())) {
                    Player player = CharacterFactory.createPlayer(data.getName(), data.getType(), data.getColor());
                    finalPlayers.add(player);
                }
            }

            if (controller.canStartGame()) {
                controller.confirmSelection();
                Game game = new Game();
                game.setPlayersFromOutputData(viewModel.getAllPlayers());
                game.initializeGame();
                GameHolder.setGame(game);
                dispose();
                new GameView().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please select at least 2 characters.");
            }
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
        if (data != null && data.getPortrait() != null) {
            portraitLabel.setIcon(new ImageIcon(data.getPortrait().getScaledInstance(180, 180, Image.SCALE_SMOOTH)));
        } else {
            portraitLabel.setIcon(null);
        }
    }

    private Color getDefaultColor(int index) {
        Color[] defaultColors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
        return defaultColors[index % defaultColors.length];
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

