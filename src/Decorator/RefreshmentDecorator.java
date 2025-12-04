//Jose Araya
// This is the Concrete Decorator for adding refreshments to a reservation.

package Decorator;

public final class RefreshmentDecorator extends AddOnDecorator {
    // Constructor initializes the wrapped reservation cost
    public RefreshmentDecorator(ReservationCost c) {
        super(c);
    }
    public double getCost() {
        return component.getCost() + 5.0; // Fixed cost for refreshments
    }
    public String getDescription() {
        return component.getDescription() + ", Refreshments";
    }
}
