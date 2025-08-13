package main.interface_adapter.tile;

public abstract class AbstractTileViewModel {
    private final String name;

    public AbstractTileViewModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
