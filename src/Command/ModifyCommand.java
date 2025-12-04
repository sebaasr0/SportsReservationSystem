//Jose Araya
// This is a Concrete Command that encapsulates the action of modifying a reservation.
package Command;

import model.AddOnType;
import model.Reservation;
import singleton.ReservationManager;

import java.util.Set;

public final class ModifyCommand implements Command {

    // The reservation that will be modified
    private final Reservation reservation;

    // New cost value for the modification
    private final double newCost;

    // New add-ons for the modification
    private final Set<AddOnType> newAddOns;

    // Constructor for modifying cost only (backward compatible)
    public ModifyCommand(Reservation reservation, double newCost) {
        this(reservation, newCost, reservation.getAddOns());
    }

    // Constructor for modifying cost and add-ons
    public ModifyCommand(Reservation reservation, double newCost, Set<AddOnType> newAddOns) {
        this.reservation = reservation;
        this.newCost = newCost;
        this.newAddOns = newAddOns;
    }

    // Executes the modification action
    @Override
    public void execute() {
        ReservationManager.getInstance().modifyReservation(reservation, newCost, newAddOns);
        System.out.println("[COMMAND] Modified -> " + reservation.getId());
    }

    // Returns the reservation that was modified
    public Reservation getReservation() {
        return reservation;
    }
}