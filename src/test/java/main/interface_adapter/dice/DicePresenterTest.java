package main.interface_adapter.dice;

import main.use_case.dice.DiceOutputData;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class DicePresenterTest {

    @Test
    void presentDiceResult_buildsViewModelAndForwardsToCallback() {
        // Arrange: capture callback arguments and call count
        AtomicReference<DiceViewModel> lastVm = new AtomicReference<>();
        AtomicInteger callCount = new AtomicInteger(0);

        DicePresenter presenter = new DicePresenter(vm -> {
            lastVm.set(vm);
            callCount.incrementAndGet();
        });

        // Act: feed a known output
        DiceOutputData out1 = new DiceOutputData(2, 5, 7); // adjust ctor if your class differs
        presenter.presentDiceResult(out1);

        // Assert: callback invoked once with expected values
        assertEquals(1, callCount.get(), "Callback should be invoked exactly once");
        assertNotNull(lastVm.get(), "A DiceViewModel should be passed to the callback");
        assertEquals(2, lastVm.get().getDice1());
        assertEquals(5, lastVm.get().getDice2());
        assertEquals(7, lastVm.get().getSum());

        // — Optional extra check: subsequent calls still work and pass fresh data —
        DiceOutputData out2 = new DiceOutputData(6, 1, 7);
        presenter.presentDiceResult(out2);
        assertEquals(2, callCount.get(), "Callback should be invoked twice after second call");
        assertEquals(6, lastVm.get().getDice1());
        assertEquals(1, lastVm.get().getDice2());
        assertEquals(7, lastVm.get().getSum());
    }
}
