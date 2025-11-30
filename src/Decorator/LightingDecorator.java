package Decorator;

public final class LightingDecorator extends AddOnDecorator {
    public LightingDecorator(ReservationCost c) { super(c); }
    public double getCost() { return component.getCost() + 10.0; }
    public String getDescription() { return component.getDescription() + ", Lighting"; }
}
