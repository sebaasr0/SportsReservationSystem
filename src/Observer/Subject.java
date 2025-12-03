//Sebastian Rodriguez
//Defines the contract for objects that maintain a list of observers and notify them of state changes.


package Observer;

import model.Reservation;

public interface Subject {
    // Registers an observer to receive notifications
    void addObserver(Observer o);
    void removeObserver(Observer o);
    // Notifies all registered observers of a reservation event
    void notifyObservers(Reservation r, String eventType);
}
