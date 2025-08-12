package main.view;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Helper class to group the UI components (name field, character dropdown, and portrait label)
 * associated with a single player slot in the character selection screen.
 */
public class PlayerSelection {
    private JTextField nameField;
    private JComboBox<String> characterDropdown;
    private JLabel portraitLabel;

    /**
     * Constructs a PlayerSelection with the specified UI components.
     *
     * @param nameField         The JTextField for player name input.
     * @param characterDropdown The JComboBox for character type selection.
     * @param portraitLabel     The JLabel to display the player's portrait.
     */
    PlayerSelection(JTextField nameField, JComboBox<String> characterDropdown, JLabel portraitLabel) {
        this.nameField = nameField;
        this.characterDropdown = characterDropdown;
        this.portraitLabel = portraitLabel;
    }
}
