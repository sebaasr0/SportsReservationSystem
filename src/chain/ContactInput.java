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


    // Constructor initializes all fields
    public ContactInput(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
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


}
