//Sebastian Rodriguez
//Concrete implementations of Field interface. Represents a tennis court with a specific subtype

package model;

public class TennisField implements Field {
    private final FieldSubtype subtype;
    
    public TennisField() {
        this.subtype = FieldSubtype.TENNIS_SINGLES; // default
    }
    
    public TennisField(FieldSubtype subtype) {
        if (subtype.getSportType() != SportType.TENNIS) {
            throw new IllegalArgumentException("Invalid subtype for Tennis court");
        }
        this.subtype = subtype;
    }
    
    public String getType() {
        return "Tennis";
    }
    public double getBasePrice() {
        return subtype.getBasePrice();
    }
    public FieldSubtype getSubtype() {
        return subtype;
    }
    
    public String getFullDescription() {
        return "Tennis Court - " + subtype.getDisplayName();
    }
}
