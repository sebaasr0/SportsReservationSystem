package model;

public class SoccerField implements Field {
    private final FieldSubtype subtype;
    
    public SoccerField() {
        this.subtype = FieldSubtype.SOCCER_5V5; // default
    }
    
    public SoccerField(FieldSubtype subtype) {
        if (subtype.getSportType() != SportType.SOCCER) {
            throw new IllegalArgumentException("Invalid subtype for Soccer field");
        }
        this.subtype = subtype;
    }
    
    public String getType() { return "Soccer"; }
    public double getBasePrice() { return subtype.getBasePrice(); }
    public FieldSubtype getSubtype() { return subtype; }
    
    public String getFullDescription() {
        return "Soccer Field - " + subtype.getDisplayName();
    }
}

