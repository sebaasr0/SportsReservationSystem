package factory;

import model.*;

public final class FieldFactory {
    private FieldFactory() {}
    
    public static Field createField(SportType type) {
        return switch (type) {
            case SOCCER -> new SoccerField();
            case TENNIS -> new TennisField();
            case PADEL  -> new PadelField();
        };
    }
    
    public static Field createField(FieldSubtype subtype) {
        return switch (subtype.getSportType()) {
            case SOCCER -> new SoccerField(subtype);
            case TENNIS -> new TennisField(subtype);
            case PADEL  -> new PadelField(subtype);
        };
    }
}
