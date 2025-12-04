//Jose Araya
// This is the Concrete Component in the Decorator Pattern

package Decorator;

import model.Field;

public final class BaseReservationCost implements ReservationCost {

    private final Field field;
    // Constructor initializes the field
    public BaseReservationCost(Field field) {
        this.field = field;
    }
    // Returns the base price of the field
    public double getCost() {
        return field.getBasePrice();
    }
    public String getDescription() { return "Base for " + field.getType(); }
}
