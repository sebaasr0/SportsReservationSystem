package Decorator;

public abstract class AddOnDecorator implements ReservationCost {
    protected final ReservationCost component;
    protected AddOnDecorator(ReservationCost component) { this.component = component; }
    public String getDescription() { return component.getDescription(); }
}
