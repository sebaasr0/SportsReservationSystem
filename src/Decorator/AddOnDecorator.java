//Jose Araya
// This is the Abstract Decorator class for reservation cost add-ons.

package Decorator;

public abstract class AddOnDecorator implements ReservationCost {
    // Constructor receives the object being wrapped
    protected final ReservationCost component;
    protected AddOnDecorator(ReservationCost component) {
        this.component = component;
    }
    public String getDescription() {
        return component.getDescription();
    }
}
