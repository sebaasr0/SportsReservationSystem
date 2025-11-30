package model;

public interface Field {
    String getType();
    double getBasePrice();
    FieldSubtype getSubtype();
    String getFullDescription();
}
