package Command;

import Decorator.ReservationCost;
import factory.FieldFactory;
import model.*;
import singleton.ReservationManager;
// Encapsulates the action of creating a new reservation
public final class ReserveCommand implements Command {
    private final User user;
    private final SportType sport;
    private final Timeslot slot;
    private final ReservationCost cost;
    private Reservation result;
    // Constructor initializes all necessary reservation data
    public ReserveCommand(User user, SportType sport, Timeslot slot, ReservationCost cost) {
        this.user = user; this.sport = sport; this.slot = slot; this.cost = cost;
    }
    public void execute() {
        Field field = FieldFactory.createField(sport);
        result = ReservationManager.getInstance()
                .addReservation(user, field, slot, cost.getCost());
        // Logs the reservation creation
        System.out.println("[CMD] Reserved -> " + result);
    }

    // Provides access to the result after execution
    public Reservation getResult() { return result; }
}
