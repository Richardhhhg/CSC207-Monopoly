package main.entity.players;

import static main.constants.Constants.NP_POR;

public class NullAbstractPlayer extends AbstractPlayer {
    public NullAbstractPlayer() {
        super();
        this.loadPortrait(NP_POR);
    }
}

