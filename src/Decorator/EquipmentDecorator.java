//Jose Araya
//This is the Concrete Decorator for adding equipment to a reservation.
package Decorator;

public final class EquipmentDecorator extends AddOnDecorator {
    // Constructor initializes the wrapped reservation cost
    public EquipmentDecorator(ReservationCost c) {
        super(c);
    }
    public double getCost() {
        return component.getCost() + 8.0; // Fixed cost for equipment
    }
    public String getDescription() {
        return component.getDescription() + ", Equipment";
    }
}
