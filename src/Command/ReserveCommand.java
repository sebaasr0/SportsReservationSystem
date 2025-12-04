//Jose Araya
// This is a Concrete Command that encapsulates the action of creating a new reservation.
package Command;

import Decorator.ReservationCost;
import factory.FieldFactory;
import model.*;
import singleton.ReservationManager;

import java.util.HashSet;
import java.util.Set;

public final class ReserveCommand implements Command {
    private final User user;
    private final FieldSubtype subtype;
    private final Timeslot slot;
    private final ReservationCost cost;
    private final Set<AddOnType> addOns;
    private Reservation result;

    // Constructor for backward compatibility (no add-ons)
    public ReserveCommand(User user, FieldSubtype subtype, Timeslot slot, ReservationCost cost) {
        this(user, subtype, slot, cost, new HashSet<>());
    }

    // New constructor that accepts add-ons
    public ReserveCommand(User user, FieldSubtype subtype, Timeslot slot, ReservationCost cost, Set<AddOnType> addOns) {
        this.user = user;
        this.subtype = subtype;
        this.slot = slot;
        this.cost = cost;
        this.addOns = new HashSet<>(addOns);
    }

    // Executes the reservation creation logic
    public void execute() {
        Field field = FieldFactory.createField(subtype);
        result = ReservationManager.getInstance()
                .addReservation(user, field, slot, cost.getCost(), addOns);
        System.out.println("[COMMAND] Reserved -> " + result);
    }

    // Provides access to the result after execution
    public Reservation getResult() { return result; }
}