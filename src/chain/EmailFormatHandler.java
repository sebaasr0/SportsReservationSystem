package chain;
// Handler in the Chain of Responsibility that validates email format
public final class EmailFormatHandler extends BaseContactHandler {
    private static final String RX="^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$";
    public ValidationResult handle(ContactInput in) {
        // If email does not match the regex pattern, validation fails
        if (!in.email().matches(RX)) return ValidationResult.fail("Invalid email format.");
        // If valid, forward the input to the next handler in the chain
        return next(in);
    }
}
