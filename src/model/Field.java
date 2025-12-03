//Sebastian Rodriguez
//Contract that all field types must follow. Enables polymorphism throughout the application.
package model;

public interface Field {
    String getType();
    double getBasePrice();
    FieldSubtype getSubtype();
    String getFullDescription();
}
