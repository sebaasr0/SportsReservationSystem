package model;

public enum FieldSubtype {
    // Soccer subtypes
    SOCCER_5V5("5v5", SportType.SOCCER, 35.0),
    SOCCER_7V7("7v7", SportType.SOCCER, 45.0),
    
    // Tennis subtypes
    TENNIS_SINGLES("Singles", SportType.TENNIS, 20.0),
    TENNIS_DOUBLES("Doubles", SportType.TENNIS, 30.0),
    
    // Padel subtypes
    PADEL_WITH_ROOF("With Roof", SportType.PADEL, 35.0),
    PADEL_WITHOUT_ROOF("Without Roof", SportType.PADEL, 25.0);
    
    private final String displayName;
    private final SportType sportType;
    private final double basePrice;
    
    FieldSubtype(String displayName, SportType sportType, double basePrice) {
        this.displayName = displayName;
        this.sportType = sportType;
        this.basePrice = basePrice;
    }
    
    public String getDisplayName() { return displayName; }
    public SportType getSportType() { return sportType; }
    public double getBasePrice() { return basePrice; }
    
    public static FieldSubtype[] getSubtypesFor(SportType sport) {
        return switch (sport) {
            case SOCCER -> new FieldSubtype[]{SOCCER_5V5, SOCCER_7V7};
            case TENNIS -> new FieldSubtype[]{TENNIS_SINGLES, TENNIS_DOUBLES};
            case PADEL -> new FieldSubtype[]{PADEL_WITH_ROOF, PADEL_WITHOUT_ROOF};
        };
    }
    
    @Override
    public String toString() {
        return sportType.name() + " - " + displayName;
    }
}
