//Jose Araya
// This is a Concrete Command that encapsulates the action of modifying a reservation.
package Command;

import model.Reservation;
import singleton.ReservationManager;

public final class ModifyCommand implements Command {

    // The reservation that will be modified
    private final Reservation reservation;

    // New cost value for the modification
    private final double newCost;

    // Constructor for modifying the cost
    public ModifyCommand(Reservation reservation, double newCost) {
        this.reservation = reservation;
        this.newCost = newCost;
    }

    // Executes the modification action
    @Override
    public void execute() {
        ReservationManager.getInstance().modifyReservation(reservation, newCost); // Calls the ReservationManager singleton to modify the reservation
        System.out.println("[COMMAND] Modified -> " + reservation.getId());
    }

    // Returns the reservation that was modified
    public Reservation getReservation() {
        return reservation;
    }
}
