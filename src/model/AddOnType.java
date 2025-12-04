//Yasir Pervaiz
//Defines the available add-on types with their prices for the Decorator pattern

package model;

public enum AddOnType {
    LIGHTING("Lighting", 10.0),
    EQUIPMENT("Equipment", 8.0),
    REFRESHMENTS("Refreshments", 5.0);

    private final String displayName;
    private final double price;

    AddOnType(String displayName, double price) {
        this.displayName = displayName;
        this.price = price;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getPrice() {
        return price;
    }
}