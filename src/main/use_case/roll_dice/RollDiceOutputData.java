// Output Data for Roll Dice Use Case
package main.use_case.roll_dice;

import main.entity.DicePair;

public class RollDiceOutputData {
    private final DicePair dicePair;
    private final long timestamp;

    public RollDiceOutputData(DicePair dicePair) {
        this.dicePair = dicePair;
        this.timestamp = System.currentTimeMillis();
    }

    public DicePair getDicePair() {
        return dicePair;
    }

    public int getDice1Value() {
        return dicePair.getDice1().getValue();
    }

    public int getDice2Value() {
        return dicePair.getDice2().getValue();
    }

    public int getSum() {
        return dicePair.getSum();
    }

    public boolean isDouble() {
        return dicePair.isDouble();
    }

    public long getTimestamp() {
        return timestamp;
    }
}
