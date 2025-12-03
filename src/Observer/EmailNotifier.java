//Sebastian Rodriguez
//Sends email notifications when reservation events occur


package Observer;

import model.Reservation;

public final class EmailNotifier implements Observer {
    public void update(Reservation r, String eventType) {
        // Simulates sending an email notification
        // Displays reservation info and event type (CREATED, CANCELED, etc.)
        System.out.println("[EMAIL] Event="+eventType+" To="+r.getUser().getEmail()+" -> "+r);
    }
}
