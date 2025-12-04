//Jose Araya
// This is the Concrete Decorator for adding refreshments to a reservation.

package Decorator;

public final class RefreshmentDecorator implements ReservationCost {
    private final ReservationCost component;

    // Constructor initializes the component
    public RefreshmentDecorator(ReservationCost c) {
        this.component = c;
    }
    public double getCost() {
        return component.getCost() + 5.0; // Adds a fixed cost for refreshments
    }
    public String getDescription() {
        return component.getDescription() + ", Refreshments";
    }
}
