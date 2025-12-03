package chain;
// Represents the result of a validation step in the Chain of Responsibility
public final class ValidationResult {
    private final boolean ok;
    private final String message;
    // Private constructor to force the use of factory methods
    private ValidationResult(boolean ok, String message) { this.ok = ok; this.message = message; }
    public static ValidationResult ok() { return new ValidationResult(true, null); }
    public static ValidationResult fail(String msg) { return new ValidationResult(false, msg); }
    // Returns true if validation passed
    public boolean isOk() { return ok; }
    public String getMessage() { return message; }
}
