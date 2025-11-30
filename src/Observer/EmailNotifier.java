package Observer;

import model.Reservation;

public final class EmailNotifier implements Observer {
    public void update(Reservation r, String eventType) {
        System.out.println("[EMAIL] Event="+eventType+" To="+r.getUser().getEmail()+" -> "+r);
    }
}
