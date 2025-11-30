package model;


public class PadelField implements Field {
    private final FieldSubtype subtype;
    
    public PadelField() {
        this.subtype = FieldSubtype.PADEL_WITHOUT_ROOF; // default
    }
    
    public PadelField(FieldSubtype subtype) {
        if (subtype.getSportType() != SportType.PADEL) {
            throw new IllegalArgumentException("Invalid subtype for Padel court");
        }
        this.subtype = subtype;
    }
    
    public String getType() { return "Padel"; }
    public double getBasePrice() { return subtype.getBasePrice(); }
    public FieldSubtype getSubtype() { return subtype; }
    
    public String getFullDescription() {
        return "Padel Court - " + subtype.getDisplayName();
    }
}
