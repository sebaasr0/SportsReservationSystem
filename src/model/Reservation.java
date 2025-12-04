//Sebastian Rodriguez
//The main object that ties everything together. Represents a complete booking with user, field, time, cost, and status.

package model;

import java.util.UUID;

public final class Reservation {
    private final UUID id = UUID.randomUUID();
    private final User user;
    private final Field field;
    private final Timeslot timeslot;
    private double totalCost;
    private ReservationStatus status = ReservationStatus.CONFIRMED;

    public Reservation(User user, Field field, Timeslot timeslot, double totalCost) {
        this.user = user; this.field = field; this.timeslot = timeslot; this.totalCost = totalCost;
    }
    public UUID getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    public Field getField() {
        return field;
    }
    public Timeslot getTimeslot() {
        return timeslot;
    }
    public double getTotalCost() {
        return totalCost;
    }
    public ReservationStatus getStatus() {
        return status;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    public void cancel() {
        this.status = ReservationStatus.CANCELED;
    }

    @Override public String toString() {
        return "%s | %s | %s | $%.2f | %s".formatted(
                user.getName(), field.getType(), timeslot, totalCost, status);
    }
}
