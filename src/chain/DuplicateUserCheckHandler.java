//Jose Araya
//checks for duplicate user registration.

package chain;

import singleton.ReservationManager;

public final class DuplicateUserCheckHandler extends BaseContactHandler {
    private final ReservationManager manager = ReservationManager.getInstance();

    // Handles validation request for checking duplicate users
    public ValidationResult handle(ContactInput in) {
        if (manager.userExists(in.email()))
            // If email already exists, return warning message
            return ValidationResult.fail("Email already exists. Just one reservation per user is allowed.");

        // If valid, forward the input to the next handler in the chain

        return next(in);
    }
}
