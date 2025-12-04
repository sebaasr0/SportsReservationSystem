//Jose Araya
// This is the Handler interface for the Chain of Responsibility Pattern.

package chain;
// Defines the method to handle the request and set the next handler
public interface ContactHandler {
    ValidationResult handle(ContactInput input);
    ContactHandler setNext(ContactHandler next);
}
