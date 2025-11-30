package Observer;

import model.Reservation;

public interface Observer {
    void update(Reservation r, String eventType);
}
