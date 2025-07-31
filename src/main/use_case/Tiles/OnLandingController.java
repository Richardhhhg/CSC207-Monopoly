package main.use_case.Tiles;

import main.entity.players.Player;
import main.use_case.Tile;

public class OnLandingController {
    private final OnLandingUseCase onLandingUseCase;

    public OnLandingController(OnLandingUseCase onLandingUseCase) {
        this.onLandingUseCase = onLandingUseCase;
    }

    public void handleLanding(Player player, Tile tile) {
        onLandingUseCase.execute(player, tile);
    }
}
