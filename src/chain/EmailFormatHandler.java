package chain;

public final class EmailFormatHandler extends BaseContactHandler {
    private static final String RX="^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$";
    public ValidationResult handle(ContactInput in) {
        if (!in.email().matches(RX)) return ValidationResult.fail("Invalid email format.");
        return next(in);
    }
}
