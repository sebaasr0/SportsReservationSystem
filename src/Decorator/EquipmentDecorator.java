//Jose Araya
//This is the Concrete Decorator for adding equipment to a reservation.
package Decorator;

public final class EquipmentDecorator implements ReservationCost {
    private final ReservationCost component; // The component being decorated

    // Constructor initializes the component
    public EquipmentDecorator(ReservationCost c) {
        this.component = c;
    }
    public double getCost() {
        return component.getCost() + 8.0; // Adds a fixed cost for equipment
    }
    public String getDescription() {
        return component.getDescription() + ", Equipment";
    }
}