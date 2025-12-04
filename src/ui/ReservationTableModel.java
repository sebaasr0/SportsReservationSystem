//Yasir Pervaiz
//Table model for displaying reservation data in a JTable
package ui;

import model.Reservation;
import model.ReservationStatus;

import javax.swing.table.AbstractTableModel;
import java.time.LocalTime;
import java.util.List;

public class ReservationTableModel extends AbstractTableModel {
    private final String[] cols = {"ID", "User", "Email", "Field Type", "Date", "Start", "End", "Total", "Status"};
    private List<Reservation> data;

    public ReservationTableModel(List<Reservation> data) {
        this.data = data;
    }

    public void setData(List<Reservation> data) {
        this.data = data;
        fireTableDataChanged();
    }

    // Returns the Reservation object at the specified row index
    public Reservation getReservationAt(int row) {
        if (row >= 0 && row < data.size()) {
            return data.get(row);
        }
        return null;
    }

    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return cols.length;
    }

    public String getColumnName(int c) {
        return cols[c];
    }

    // Helper method to format time to AM/PM
    private String formatTimeAMPM(LocalTime time) {
        int hour = time.getHour();
        if (hour == 0 || hour == 24) return "12:00 AM";
        if (hour == 12) return "12:00 PM";
        if (hour < 12) return hour + ":00 AM";
        return (hour - 12) + ":00 PM";
    }

    public Object getValueAt(int r, int c) {
        Reservation x = data.get(r);
        return switch (c) {
            case 0 -> x.getId().toString().substring(0, 8) + "...";
            case 1 -> x.getUser().getName();
            case 2 -> x.getUser().getEmail();
            case 3 -> x.getField().getFullDescription();
            case 4 -> x.getTimeslot().getDate();
            case 5 -> formatTimeAMPM(x.getTimeslot().getStart());  // AM/PM format
            case 6 -> formatTimeAMPM(x.getTimeslot().getEnd());    // AM/PM format
            case 7 -> String.format("$%.2f", x.getTotalCost());
            case 8 -> x.getStatus() == ReservationStatus.CONFIRMED ? "CONFIRMED" : "CANCELED";
            default -> "";
        };
    }
}