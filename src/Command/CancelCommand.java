package Command;

import model.Reservation;
import singleton.ReservationManager;
// Represents the action of canceling an existing reservation
public final class CancelCommand implements Command {
    private final Reservation reservation;
    public CancelCommand(Reservation reservation) { this.reservation = reservation; }
    public void execute() {
        // Calls the ReservationManager singleton to cancel the reservation
        ReservationManager.getInstance().cancelReservation(reservation);
        System.out.println("[CMD] Canceled -> " + reservation.getId());
    }
}
