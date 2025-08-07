package main.use_case.CharacterSelectionScreen;

public class CharacterSelectionInputData {
    private final int index;
    private final String name;
    private final String type;

    public CharacterSelectionInputData(int index, String name, String type) {
        this.index = index;
        this.name = name;
        this.type = type;
    }

    public int getIndex() { return index; }
    public String getName() { return name; }
    public String getType() { return type; }
}
