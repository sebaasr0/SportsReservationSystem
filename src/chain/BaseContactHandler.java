package chain;

public abstract class BaseContactHandler implements ContactHandler {
    protected ContactHandler next;
    public ContactHandler setNext(ContactHandler n) { this.next = n; return n; }
    protected ValidationResult next(ContactInput in) {
        return next == null ? ValidationResult.ok() : next.handle(in);
    }
}
