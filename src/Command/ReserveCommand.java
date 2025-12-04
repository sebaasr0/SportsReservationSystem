//Jose Araya
// This is a Concrete Command that encapsulates the action of creating a new reservation.
package Command;

import Decorator.ReservationCost;
import factory.FieldFactory;
import model.*;
import singleton.ReservationManager;

public final class ReserveCommand implements Command {
    private final User user;
    private final SportType sport;
    private final Timeslot slot;
    private final ReservationCost cost;
    private Reservation result;
    // Constructor initializes all necessary reservation data
    public ReserveCommand(User user, SportType sport, Timeslot slot, ReservationCost cost) {
        this.user = user;
        this.sport = sport;
        this.slot = slot;
        this.cost = cost;
    }
    // Executes the reservation creation logic
    public void execute() {
        Field field = FieldFactory.createField(sport); //calls factory
        result = ReservationManager.getInstance() //calls singleton
                .addReservation(user, field, slot, cost.getCost());
        System.out.println("[COMMAND] Reserved -> " + result);
    }

    // Provides access to the result after execution
    public Reservation getResult() { return result; }
}
