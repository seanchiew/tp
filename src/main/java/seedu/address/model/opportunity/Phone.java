package seedu.address.model.opportunity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an optional phone number in the tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {

    public static final String MESSAGE_CONSTRAINTS =
        "Phone numbers should contain 3 to 15 digits, may optionally start with '+', "
            + "and may use spaces, hyphens, or parentheses as separators "
            + "(e.g. +65 9123 4567, +1-800-555-0100, +1 (212) 555-0199)";

    public static final int MIN_DIGITS = 3;
    public static final int MAX_DIGITS = 15;

    public static final String VALIDATION_REGEX = "\\+?\\d{3,15}";

    private final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        checkArgument(isValidPhone(trimmedPhone), MESSAGE_CONSTRAINTS);
        this.value = normalize(trimmedPhone);
    }

    public String getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid phone number.
     * Spaces, hyphens, and parentheses are treated as separators and ignored during validation.
     */
    public static boolean isValidPhone(String test) {
        requireNonNull(test);
        return normalize(test.trim()).matches(VALIDATION_REGEX);
    }

    /**
     * Strips spaces, hyphens, and parentheses from a phone string, retaining only digits and a leading '+'.
     */
    private static String normalize(String phone) {
        return phone.replaceAll("[\\s()\\-]", "");
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
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
