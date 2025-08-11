package main.use_case.player;

import main.entity.players.Player;
import main.entity.players.applyAfterEffects;

public class ApplyTurnEffects {
    public void execute(Player player) {
        if (player instanceof applyAfterEffects) {
            ((applyAfterEffects) player).applyTurnEffects();
        }
        if (player.isBankrupt()){
            DeclareBankruptcy declareBankruptcy = new DeclareBankruptcy();
            declareBankruptcy.execute(player);
        }
    }
}
