package chain;

public final class PhoneFormatHandler extends BaseContactHandler {
    public ValidationResult handle(ContactInput in) {
        if (!in.phone().matches("^\\+?[0-9\\- ]{7,15}$"))
            return ValidationResult.fail("Invalid phone number.");
        return next(in);
    }
}
