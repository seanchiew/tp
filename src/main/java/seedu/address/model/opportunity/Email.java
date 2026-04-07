package seedu.address.model.opportunity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a contact's email address in the tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    public static final String MESSAGE_CONSTRAINTS =
        "Emails should be of the format local-part@domain and must not be blank.\n"
            + "The local-part may contain alphanumeric characters and the special characters: + _ . -\n"
            + "The local-part must not start or end with + . or -\n"
            + "The local-part must not contain consecutive dots (..)\n"
            + "The domain must consist of at least two labels separated by periods,\n"
            + "and each label must be at least 1 character long.";

    public static final int MAX_LENGTH = 254;

    /*
     * The local-part should only contain alphanumeric characters and these special characters: +, _, ., -.
     * The local-part must not start or end with a special character.
     * This is followed by a '@' and then a domain name.
     * The domain name must:
     *     - consist of at least two labels separated by periods
     *     - each label must start and end with alphanumeric characters
     *     - each label can contain hyphens in between
     */
    public static final String VALIDATION_REGEX =
        "[\\w]((?:[\\w+\\-]|\\.(?!\\.))*[\\w])?@[\\w][\\w\\-]*(\\.[\\w][\\w\\-]*)+";

    private final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        checkArgument(isValidEmail(trimmedEmail), MESSAGE_CONSTRAINTS);
        this.value = trimmedEmail;
    }

    public String getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid email address.
     */
    public static boolean isValidEmail(String test) {
        requireNonNull(test);
        String trimmedTest = test.trim();
        return trimmedTest.length() <= MAX_LENGTH && trimmedTest.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Email)) {
            return false;
        }

        Email otherEmail = (Email) other;
        return value.equals(otherEmail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
