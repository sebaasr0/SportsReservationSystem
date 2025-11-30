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

    public ReserveCommand(User user, SportType sport, Timeslot slot, ReservationCost cost) {
        this.user = user; this.sport = sport; this.slot = slot; this.cost = cost;
    }
    public void execute() {
        Field field = FieldFactory.createField(sport);
        result = ReservationManager.getInstance()
                .addReservation(user, field, slot, cost.getCost());
        System.out.println("[CMD] Reserved -> " + result);
    }
    public Reservation getResult() { return result; }
}
