package main.use_case.tiles.property;

import main.use_case.tiles.property.RentPaymentUseCase.*;

/**
 * Output boundary interface for rent payment use case.
 * This interface defines what the use case expects from its presenter.
 */
public interface RentPaymentOutputBoundary {
    void presentRentPayment(RentPaymentData data);
}

