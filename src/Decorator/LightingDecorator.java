//Jose Araya
//This is the Concrete Decorator class for adding lighting to a reservation.
package Decorator;

public final class LightingDecorator implements ReservationCost {
    private final ReservationCost component;

    // Constructor initializes the component
    public LightingDecorator(ReservationCost c) {
        this.component = c;
    }
    public double getCost() {
        return component.getCost() + 10.0; // Adds a fixed cost for lighting
    }
    public String getDescription() {
        return component.getDescription() + ", Lighting";
    }
}