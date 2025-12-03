//Sebastian Rodriguez
//Creates field objects without exposing creation logic to the client

package factory;

import model.*;

public final class FieldFactory {
    private FieldFactory() {}
    
    public static Field createField(SportType type) {
        // Switch expression to determine the correct Field subclass
        return switch (type) {
            case SOCCER -> new SoccerField();
            case TENNIS -> new TennisField();
            case PADEL  -> new PadelField();
        };
    }
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
