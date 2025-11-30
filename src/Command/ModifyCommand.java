package Command;

import model.Reservation;

public final class ModifyCommand implements Command {
    // Placeholder for future modifications (timeslot/cost adjustments)
    private final Reservation reservation;
    public ModifyCommand(Reservation reservation) { this.reservation = reservation; }
    public void execute() {
        // e.g., change timeslot or recalc cost; notify if needed
        System.out.println("[CMD] Modify (noop) -> " + reservation.getId());
    }
}
