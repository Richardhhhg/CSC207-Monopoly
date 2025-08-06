package main.view;

import main.entity.Game;
import main.entity.players.*;
import main.interface_adapter.CharacterSelectionScreen.CharacterSelectionScreenAdapter;
import main.interface_adapter.CharacterSelectionScreen.CharacterSelectionScreenController;
import main.interface_adapter.CharacterSelectionScreen.CharacterSelectionScreenPresenter;
import main.interface_adapter.CharacterSelectionScreen.GameLaunchOutputData;
import main.use_case.CharacterSelectionScreen.GameLauncher;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterSelectionScreen extends JFrame {
    private CharacterSelectionScreenController controller;
    private final List<PlayerSelection> selections = new ArrayList<>();
    private final CharacterSelectionScreenPresenter presenter;

    public CharacterSelectionScreen() {
        CharacterSelectionScreenAdapter adapter = CharacterSelectionScreenAdapter.inject();
        this.controller = adapter.getController();
        this.presenter = adapter.getPresenter();
        initializeScreen();
    }

    private void initializeScreen() {
        setTitle("Select Your Characters");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel playerPanel = new JPanel(new GridLayout(2, 2));

        for (int i = 0; i < 4; i++) {
            int index = i;

            JPanel playerSlot = new JPanel();
            playerSlot.setLayout(new BoxLayout(playerSlot, BoxLayout.Y_AXIS));
            playerSlot.setBorder(BorderFactory.createTitledBorder("Player " + (index + 1)));

            JTextField nameField = new JTextField("Player " + (index + 1));
            nameField.setMaximumSize(new Dimension(150, 25));
            nameField.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel portrait = new JLabel();
            portrait.setPreferredSize(new Dimension(100, 100));
            portrait.setAlignmentX(Component.CENTER_ALIGNMENT);

            JComboBox<String> characterDropdown = new JComboBox<>(new String[]{
                    "None", "Clerk", "Landlord", "Inheritor", "College Student", "Poor Man"
            });
            characterDropdown.setMaximumSize(new Dimension(150, 25));
            characterDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);

            Player initial = controller.selectPlayer(index, nameField.getText(), "None");
            portrait.setIcon(new ImageIcon(initial.getPortrait().getScaledInstance(180, 180, Image.SCALE_SMOOTH)));
            selections.add(new PlayerSelection("None", nameField, characterDropdown));

            characterDropdown.addActionListener(e -> {
                String selectedType = (String) characterDropdown.getSelectedItem();
                String name = nameField.getText();
                Player selected = controller.selectPlayer(index, name, selectedType);
                portrait.setIcon(new ImageIcon(selected.getPortrait().getScaledInstance(180, 180, Image.SCALE_SMOOTH)));
                selections.set(index, new PlayerSelection(selectedType, nameField, characterDropdown));
                controller.selectPlayer(index, selected);
            });

            playerSlot.add(Box.createVerticalStrut(10));
            playerSlot.add(portrait);
            playerSlot.add(Box.createVerticalStrut(10));
            playerSlot.add(characterDropdown);
            playerSlot.add(Box.createVerticalStrut(10));
            playerSlot.add(nameField);
            playerPanel.add(playerSlot);
        }

        JButton startGame = new JButton("Play!");
        startGame.addActionListener(e -> {
            List<Player> finalPlayers = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                PlayerSelection sel = selections.get(i);
                String name = sel.nameField.getText();
                String type = (String) sel.characterDropdown.getSelectedItem();
                Player player = controller.selectPlayer(i, name, type);
                finalPlayers.add(player);
            }

            for (int i = 0; i < 4; i++) {
                controller.selectPlayer(i, finalPlayers.get(i));
            }

            if (controller.canStartGame()) {
                controller.confirmSelection();
                GameLaunchOutputData data = presenter.getLaunchOutputData();
                GameLauncher launcher = new GameLauncher();
                Game game = launcher.launch(data);
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

    private static class PlayerSelection {
        String type;
        JTextField nameField;
        JComboBox<String> characterDropdown;

        PlayerSelection(String type, JTextField nameField, JComboBox<String> characterDropdown) {
            this.type = type;
            this.nameField = nameField;
            this.characterDropdown = characterDropdown;
        }
    }
}