package chain;

public abstract class BaseContactHandler implements ContactHandler {
    // Reference to the next handler in the chain
    protected ContactHandler next;
    public ContactHandler setNext(ContactHandler n) { this.next = n; return n; }
    protected ValidationResult next(ContactInput in) {
        // If this is the last handler, return OK
        // Otherwise, pass the request forward in the chain
        return next == null ? ValidationResult.ok() : next.handle(in);
    }
}
