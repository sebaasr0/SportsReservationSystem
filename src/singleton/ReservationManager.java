//Sebastian Rodriguez
//Single instance that manages all reservations in the system

package singleton;

import model.*;
import Observer.Observer;
import Observer.Subject;

import java.util.*;
import java.util.UUID;

public final class ReservationManager implements Subject {
    private static ReservationManager instance;

    private final List<Observer> observers = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();
    private final Map<String, User> usersByEmail = new HashMap<>();

    private ReservationManager() {}

    public static synchronized ReservationManager getInstance() {
        if (instance == null) instance = new ReservationManager();
        return instance;
    }

    // User management
    public boolean userExists(String email) {
        return email != null && usersByEmail.containsKey(email.toLowerCase());
    }

    // Checks if a user has any ACTIVE (CONFIRMED) reservations
    // Returns false if all their reservations are canceled
    public boolean userHasActiveReservation(String email) {
        if (email == null) return false;
        String lowerEmail = email.toLowerCase();
        return reservations.stream()
                .filter(r -> r.getStatus() == ReservationStatus.CONFIRMED)
                .anyMatch(r -> r.getUser().getEmail().toLowerCase().equals(lowerEmail));
    }

    public void saveUser(User user) {
        usersByEmail.put(user.getEmail().toLowerCase(), user);
    }

    // Reservation management
    public boolean isAvailable(Field field, Timeslot slot) {
        return reservations.stream()
                .filter(r -> r.getStatus() == ReservationStatus.CONFIRMED)
                .noneMatch(r -> {
                    // Check if same field type
                    boolean sameType = r.getField().getType().equals(field.getType());
                    // Check if same subtype (if both have subtypes)
                    boolean sameSubtype = true;
                    if (r.getField().getSubtype() != null && field.getSubtype() != null) {
                        sameSubtype = r.getField().getSubtype() == field.getSubtype();
                    }
                    return sameType && sameSubtype && r.getTimeslot().overlaps(slot);
                });
    }

    // Adds a new reservation if the timeslot is available
    public Reservation addReservation(User user, Field field, Timeslot slot, double cost) {
        if (!isAvailable(field, slot))
            throw new IllegalStateException("Timeslot not available for this field");
        saveUser(user);
        Reservation r = new Reservation(user, field, slot, cost);
        reservations.add(r);
        notifyObservers(r, "CREATED");
        return r;
    }

    public List<Reservation> listAll() {
        return new ArrayList<>(reservations);
    }

    // Cancels an existing reservation
    public void cancelReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null");
        }
        if (reservation.getStatus() == ReservationStatus.CANCELED) {
            throw new IllegalStateException("Reservation is already canceled");
        }
        reservation.cancel();
        notifyObservers(reservation, "CANCELED");
    }

    // Modifies an existing reservation, it updates the total cost
    public void modifyReservation(Reservation reservation, double newCost) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null");
        }
        if (reservation.getStatus() == ReservationStatus.CANCELED) {
            throw new IllegalStateException("Cannot modify a canceled reservation");
        }

        // Update cost
        reservation.setTotalCost(newCost);

        notifyObservers(reservation, "MODIFIED");
    }

    // Observer methods
    public void addObserver(Observer o) {
        observers.add(o);
    }
    public void removeObserver(Observer o) {
        observers.remove(o);
    }
    public void notifyObservers(Reservation r, String eventType) {
        for (Observer o : observers) o.update(r, eventType);
    }
}
