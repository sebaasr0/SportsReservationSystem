package chain;

public final class RequiredFieldsHandler extends BaseContactHandler {
    public ValidationResult handle(ContactInput in) {
        if (in.name()==null || in.name().isBlank() ||
                in.email()==null || in.email().isBlank() ||
                in.phone()==null || in.phone().isBlank())
            return ValidationResult.fail("Name, email and phone are required.");
        return next(in);
    }
}
