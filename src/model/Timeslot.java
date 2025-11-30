package model;

import java.time.LocalDate;
import java.time.LocalTime;

public final class Timeslot {
    private final LocalDate date;
    private final LocalTime start;
    private final LocalTime end;

    public Timeslot(LocalDate date, LocalTime start, LocalTime end) {
        if (date == null || start == null || end == null || !end.isAfter(start))
            throw new IllegalArgumentException("Invalid timeslot");
        this.date = date; this.start = start; this.end = end;
    }
    public LocalDate getDate() { return date; }
    public LocalTime getStart() { return start; }
    public LocalTime getEnd() { return end; }

    public boolean overlaps(Timeslot other) {
        if (!date.equals(other.date)) return false;
        return start.isBefore(other.end) && other.start.isBefore(end);
    }
    @Override public String toString() { return date + " " + start + "-" + end; }
}
