package Decorator;

import model.Field;

public final class BaseReservationCost implements ReservationCost {
    private final Field field;
    public BaseReservationCost(Field field) { this.field = field; }
    public double getCost() { return field.getBasePrice(); }
    public String getDescription() { return "Base for " + field.getType(); }
}
