package Observer;

import model.Reservation;

public interface Subject {
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers(Reservation r, String eventType);
}
