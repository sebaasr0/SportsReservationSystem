//Jose Araya
// This is a Concrete Handler that validates phone number format.

package chain;

// Validates that the phone number is exactly 10 digits
public final class PhoneFormatHandler extends BaseContactHandler {
    public ValidationResult handle(ContactInput in) {
        if (!in.phone().matches("\\d{10}")) // If phone number is not exactly 10 digits, validation fails
            return ValidationResult.fail("Phone number must be exactly 10 digits.");
        return next(in);
    }
}
