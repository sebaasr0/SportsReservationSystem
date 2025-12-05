//Sebastian Rodriguez
//The main object that ties everything together. Represents a complete booking with user, field, time, cost, and status.

package model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class Reservation {
    private final UUID id = UUID.randomUUID();
    private final User user;
    private final Field field;
    private final Timeslot timeslot;
    private double totalCost;
    private ReservationStatus status = ReservationStatus.CONFIRMED;

    // Store the actual add-ons selected (proper use of data, decorators handle cost calculation)
    private final Set<AddOnType> addOns;

    public Reservation(User user, Field field, Timeslot timeslot, double totalCost) {
        this(user, field, timeslot, totalCost, new HashSet<>());
    }

    public Reservation(User user, Field field, Timeslot timeslot, double totalCost, Set<AddOnType> addOns) {
        this.user = user;
        this.field = field;
        this.timeslot = timeslot;
        this.totalCost = totalCost;
        this.addOns = new HashSet<>(addOns);
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

    // Returns an unmodifiable view of the add-ons
    public Set<AddOnType> getAddOns() {
        return Collections.unmodifiableSet(addOns);
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    // Update add-ons when modifying reservation
    public void setAddOns(Set<AddOnType> newAddOns) {
        this.addOns.clear();
        this.addOns.addAll(newAddOns);
    }

    public void cancel() {
        this.status = ReservationStatus.CANCELED;
    }

    @Override public String toString() {
        return "%s | %s | %s | $%.2f | %s".formatted(
                user.getName(), field.getType(), timeslot, totalCost, status);
    }
}