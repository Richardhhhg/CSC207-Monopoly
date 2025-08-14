package main.use_case.player;

import main.entity.players.AbstractPlayer;
import main.entity.players.applyAfterEffects;

public class ApplyTurnEffects {
    public void execute(AbstractPlayer abstractPlayer) {
        if (abstractPlayer instanceof applyAfterEffects) {
            ((applyAfterEffects) abstractPlayer).applyTurnEffects();
        }
        if (abstractPlayer.isBankrupt()){
            DeclareBankruptcy declareBankruptcy = new DeclareBankruptcy();
            declareBankruptcy.execute(abstractPlayer);
        }
    }
}
