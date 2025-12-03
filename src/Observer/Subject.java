package Observer;

import model.Reservation;

public interface Subject {
    // Registers an observer to receive notifications
    void addObserver(Observer o);
    void removeObserver(Observer o);
    // Notifies all registered observers of a reservation event
    void notifyObservers(Reservation r, String eventType);
}
