package main.use_case.Property;

import main.use_case.Property.RentPaymentUseCase.*;

/**
 * Output boundary interface for rent payment use case.
 * This interface defines what the use case expects from its presenter.
 */
public interface RentPaymentOutputBoundary {
    void presentRentPayment(RentPaymentData data);
}

