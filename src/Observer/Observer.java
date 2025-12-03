//Sebastian Rodriguez
//Defines the contract for all observers that want to be notified when reservation events occur

package Observer;

import model.Reservation;

public interface Observer {
    void update(Reservation r, String eventType);
}
