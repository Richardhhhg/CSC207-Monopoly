package main.use_case.player;

import main.entity.players.AbstractPlayer;
import main.entity.players.ApplyAfterEffects;

public class ApplyTurnEffects {
    /**
     * Applies the turn effects for the given player.
     * This includes applying any after effects and checking for bankruptcy.
     *
     * @param player the player whose turn effects are to be applied
     */
    public void execute(AbstractPlayer player) {
        if (player instanceof ApplyAfterEffects) {
            ((ApplyAfterEffects) player).applyTurnEffects();
        }
        if (player.isBankrupt()) {
            final DeclareBankruptcy declareBankruptcy = new DeclareBankruptcy();
            declareBankruptcy.execute(player);
        }
    }
}
