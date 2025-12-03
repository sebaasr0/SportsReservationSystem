package chain;

import singleton.ReservationManager;

public final class DuplicateUserCheckHandler extends BaseContactHandler {
    private final ReservationManager manager = ReservationManager.getInstance();

    // Handles validation request for checking duplicate users
    public ValidationResult handle(ContactInput in) {
        if (manager.userExists(in.email()))
            return ValidationResult.fail("Email already exists. Continue or use another email.");

        // If valid, forward the input to the next handler in the chain

        return next(in);
    }
}
