//Jose Araya
// This is a Concrete Handler that validates email format.

package chain;

// Validates that the email matches a standard email regex pattern
public final class EmailFormatHandler extends BaseContactHandler {
    private static final String RX="^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"; // Standard email regex pattern
    public ValidationResult handle(ContactInput in) {
        // If email does not match the regex pattern, validation fails
        if (!in.email().matches(RX)) return ValidationResult.fail("Invalid email format.");
        // If valid, forward the input to the next handler in the chain
        return next(in);
    }
}
