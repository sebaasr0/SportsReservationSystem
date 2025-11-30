package Decorator;

public final class RefreshmentDecorator extends AddOnDecorator {
    public RefreshmentDecorator(ReservationCost c) { super(c); }
    public double getCost() { return component.getCost() + 5.0; }
    public String getDescription() { return component.getDescription() + ", Refreshments"; }
}
