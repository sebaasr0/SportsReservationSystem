//Jose Araya
//checks for duplicate user registration.

package chain;

import singleton.ReservationManager;

public final class DuplicateUserCheckHandler extends BaseContactHandler {
    private final ReservationManager manager = ReservationManager.getInstance();

    // Handles validation request for checking duplicate users
    public ValidationResult handle(ContactInput in) {
        // Only block if user has an ACTIVE (CONFIRMED) reservation
        // Users with canceled reservations can make new reservations
        if (manager.userHasActiveReservation(in.email()))
            // If email has active reservation, return warning message
            return ValidationResult.fail("Email already has an active reservation. Just one reservation per user is allowed.");

        // If valid, forward the input to the next handler in the chain

        return next(in);
    }
}
