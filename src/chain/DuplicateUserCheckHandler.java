package chain;

import singleton.ReservationManager;

public final class DuplicateUserCheckHandler extends BaseContactHandler {
    private final ReservationManager manager = ReservationManager.getInstance();

    public ValidationResult handle(ContactInput in) {
        if (manager.userExists(in.email()))
            return ValidationResult.fail("Email already exists. Continue or use another email.");
        return next(in);
    }
}
