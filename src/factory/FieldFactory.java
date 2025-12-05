//Sebastian Rodriguez
//Creates field objects without exposing creation logic to the client

package factory;

import model.*;

public final class FieldFactory {
    private FieldFactory() {}

    // Creates a Field using the exact FieldSubtype selected
    public static Field createField(FieldSubtype subtype) {
        // Determines correct Field subclass based on subtype's sport type
        return switch (subtype.getSportType()) {
            case SOCCER -> new SoccerField(subtype);
            case TENNIS -> new TennisField(subtype);
            case PADEL  -> new PadelField(subtype);
        };
    }
}
