package main.entity.players;

import static main.constants.Constants.NP_POR;

public class NullPlayer extends Player {
    public NullPlayer() {
        super();
        this.loadPortrait(NP_POR);
    }
}

