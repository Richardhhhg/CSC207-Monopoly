package main.entity.players;

import static main.Constants.Constants.NP_POR;

public class NullPlayer extends Player {
    public NullPlayer() {
        super();
        this.loadPortrait(NP_POR);
    }
}

