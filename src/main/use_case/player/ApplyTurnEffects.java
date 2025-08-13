package main.use_case.player;

import main.entity.players.AbstractPlayer;
import main.entity.players.ApplyAfterEffects;

public class ApplyTurnEffects {
    public void execute(AbstractPlayer player) {
        if (player instanceof ApplyAfterEffects) {
            ((ApplyAfterEffects) player).applyTurnEffects();
        }
        if (player.isBankrupt()){
            DeclareBankruptcy declareBankruptcy = new DeclareBankruptcy();
            declareBankruptcy.execute(player);
        }
    }
}
