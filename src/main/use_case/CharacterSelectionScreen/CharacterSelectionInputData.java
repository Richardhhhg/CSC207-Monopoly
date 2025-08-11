package main.use_case.CharacterSelectionScreen;

/**
 * Input data object for the Character Selection use case.
 * This class is immutable and used to transfer data from the controller to the use case interactor.
 */
public class CharacterSelectionInputData {
    private final int index;
    private final String name;
    private final String type;

    /**
     * Constructs a new CharacterSelectionInputData object.
     * @param index the index of the player slot (0 to 3)
     * @param name  the name chosen by the player
     * @param type  the character type/class selected
     */
    public CharacterSelectionInputData(int index, String name, String type) {
        this.index = index;
        this.name = name;
        this.type = type;
    }

    /**
     * Get index.
     * @return the index of the player slot.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Return Name.
     * @return the name assigned to the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Return Type.
     * @return the selected character type.
     */
    public String getType() {
        return type;
    }
}
