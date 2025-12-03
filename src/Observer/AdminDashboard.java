//Sebastian Rodriguez
//Updates the admin dashboard when reservation events occur.


package Observer;

import model.Reservation;

public final class AdminDashboard implements Observer {
    public void update(Reservation r, String eventType) {
        System.out.println("[DASH]  "+eventType+" -> "+r);
    }
}
