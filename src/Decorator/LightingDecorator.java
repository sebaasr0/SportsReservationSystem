//Jose Araya
//This is the Concrete Decorator class for adding lighting to a reservation.
package Decorator;

public final class LightingDecorator extends AddOnDecorator {
    // Constructor initializes the wrapped reservation cost
    public LightingDecorator(ReservationCost c) {
        super(c);
    }
    public double getCost() {
        return component.getCost() + 10.0; // Fixed cost for lighting
    }
    // Appends lighting info to the reservation description
    public String getDescription() { return component.getDescription() + ", Lighting"; }
}
