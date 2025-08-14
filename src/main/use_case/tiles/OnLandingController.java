package main.use_case.tiles;

import main.entity.players.AbstractPlayer;
import main.entity.tiles.AbstractTile;

public class OnLandingController {
    private final OnLandingUseCase onLandingUseCase;

    public OnLandingController(OnLandingUseCase onLandingUseCase) {
        this.onLandingUseCase = onLandingUseCase;
    }

    /**
     * Handles the landing of a player on a tile.
     *
     * @param abstractPlayer the player who landed on the tile
     * @param tile   the tile that the player landed on
     */
    public void handleLanding(AbstractPlayer abstractPlayer, AbstractTile tile) {
        onLandingUseCase.execute(abstractPlayer, tile);
    }
}
