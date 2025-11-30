package Decorator;

public final class EquipmentDecorator extends AddOnDecorator {
    public EquipmentDecorator(ReservationCost c) { super(c); }
    public double getCost() { return component.getCost() + 8.0; }
    public String getDescription() { return component.getDescription() + ", Equipment"; }
}
