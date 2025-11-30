package Command;

import model.Reservation;
import singleton.ReservationManager;

public final class CancelCommand implements Command {
    private final Reservation reservation;
    public CancelCommand(Reservation reservation) { this.reservation = reservation; }
    public void execute() {
        ReservationManager.getInstance().cancelReservation(reservation);
        System.out.println("[CMD] Canceled -> " + reservation.getId());
    }
}
