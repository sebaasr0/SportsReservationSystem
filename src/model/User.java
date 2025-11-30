package model;

import java.util.Objects;

public final class User {
    private final String name;
    private final String email;
    private final String phone;

    public User(String name, String email, String phone) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name required");
        if (email == null || !email.matches("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$"))
            throw new IllegalArgumentException("Invalid email");
        if (phone == null || !phone.matches("^\\+?[0-9\\- ]{7,15}$"))
            throw new IllegalArgumentException("Invalid phone");
        this.name = name.trim();
        this.email = email.trim().toLowerCase();
        this.phone = phone.trim();
    }
    public String getName()  { return name;  }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User u)) return false;
        return email.equals(u.email);
    }
    @Override public int hashCode() { return Objects.hash(email); }
}
