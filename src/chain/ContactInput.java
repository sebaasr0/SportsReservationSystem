//Jose Araya
// This class encapsulates user contact input data for validation.

package chain;

public final class ContactInput {

    // User's full name - cannot be null or blank
    private final String name;

    // User's email address - validated for format
    private final String email;

    // User's phone number - validated for format
    private final String phone;

    // Whether user accepted terms and conditions
    private final boolean terms;

    // Constructor initializes all fields
    public ContactInput(String name, String email, String phone, boolean terms) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.terms = terms;
    }

    // Getter for name
    public String name() {
        return name;
    }

    // Getter for email
    public String email() {
        return email;
    }

    // Getter for phone
    public String phone() {
        return phone;
    }

    // Getter for terms acceptance
    public boolean terms() {
        return terms;
    }
}
