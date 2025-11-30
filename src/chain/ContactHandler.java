package chain;

public interface ContactHandler {
    ValidationResult handle(ContactInput input);
    ContactHandler setNext(ContactHandler next);
}
