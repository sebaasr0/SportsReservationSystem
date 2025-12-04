//Sebastian Rodriguez
//Stores customer contact information.

package model;

import java.util.Objects;

public final class User {
    // User full name
    private final String name;
    // User email address (used as unique identifier)
    private final String email;
    // User phone number
    private final String phone;

    public User(String name, String email, String phone) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name required");
        if (email == null || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"))
            throw new IllegalArgumentException("Invalid email");
        if (phone == null || !phone.matches("\\d{10}"))
            throw new IllegalArgumentException("Phone number must be exactly 10 digits.");
        // Normalize data input
        this.name = name.trim();
        this.email = email.trim().toLowerCase();
        this.phone = phone.trim();
    }
    public String getName()  { return name;  }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    // Two users are considered equal if they share the same email
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User u)) return false;
        return email.equals(u.email);
    }
    @Override public int hashCode() { return Objects.hash(email); }
}
