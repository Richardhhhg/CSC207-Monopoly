package main.interface_adapter.Tile;

public abstract class TileViewModel {
    protected String name;

    public TileViewModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
