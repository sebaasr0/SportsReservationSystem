//Jose Araya
// This is a Concrete Command that encapsulates the action of canceling a reservation.
package Command;

import model.Reservation;
import singleton.ReservationManager;

public final class CancelCommand implements Command {

    // The reservation that will be canceled when execute() is called
    private final Reservation reservation;

    // Constructor stores the reservation to be canceled
    public CancelCommand(Reservation reservation) {
        this.reservation = reservation;
    }

    // Executes the cancellation action
    @Override
    public void execute() {
        ReservationManager.getInstance().cancelReservation(reservation);// Calls the ReservationManager singleton to cancel the reservation
        System.out.println("[COMMAND] Canceled -> " + reservation.getId());
    }

    // Returns the reservation that was canceled
    public Reservation getReservation() {
        return reservation;
    }
}

