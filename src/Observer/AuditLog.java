package Observer;

import model.Reservation;

public final class AuditLog implements Observer {
    public void update(Reservation r, String eventType) {
        System.out.println("[AUDIT] "+eventType+" -> "+r);
    }
}
