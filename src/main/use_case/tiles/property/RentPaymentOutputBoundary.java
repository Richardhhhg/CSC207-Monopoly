package main.use_case.tiles.property;

import main.use_case.tiles.property.RentPaymentUseCase.RentPaymentData;

/**
 * Output boundary interface for rent payment use case.
 * This interface defines what the use case expects from its presenter.
 */
public interface RentPaymentOutputBoundary {
    /**
     * Presents the rent payment information to the user.
     *
     * @param data the rent payment data to display
     */
    void presentRentPayment(RentPaymentData data);
}

