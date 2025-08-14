package main.interface_adapter.tile;

import main.entity.players.AbstractPlayer;
import main.entity.tiles.AbstractTile;
import main.use_case.tiles.OnLandingUseCase;

public class OnLandingController {
    private final OnLandingUseCase onLandingUseCase;

    public OnLandingController(OnLandingUseCase onLandingUseCase) {
        this.onLandingUseCase = onLandingUseCase;
    }

    /**
     * Handles the landing of a player on a tile.
     *
     * @param player the player who landed on the tile
     * @param tile   the tile that the player landed on
     */
    public void handleLanding(AbstractPlayer player, AbstractTile tile) {
        onLandingUseCase.execute(player, tile);
    }
}
