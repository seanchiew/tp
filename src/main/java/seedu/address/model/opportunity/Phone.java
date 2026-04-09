package seedu.address.model.opportunity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an optional phone number in the tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {

    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should contain 3 to 15 digits, "
            + "start with a digit or with '+' immediately followed by a digit, "
            + "and end with a digit.\n"
            + "Spaces, hyphens, and parentheses are allowed within the phone number "
            + "(e.g. +65 9123 4567, +1-800-555-0100, +1 (212) 555-0199).";

    public static final int MIN_DIGITS = 3;
    public static final int MAX_DIGITS = 15;

    // Structural regex: must start (after optional '+') and end with a digit; separators only in between
    private static final String STRUCTURAL_REGEX = "\\+?\\d[\\d\\s()\\-]*\\d|\\+?\\d";

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
        this.value = trimmedPhone;
    }

    public String getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid phone number.
     * The raw input must start and end with a digit (with optional leading '+'),
     * and the digit count (after stripping separators) must be between MIN_DIGITS and MAX_DIGITS.
     */
    public static boolean isValidPhone(String test) {
        requireNonNull(test);
        String trimmed = test.trim();
        if (!trimmed.matches(STRUCTURAL_REGEX)) {
            return false;
        }
        String digits = normalize(trimmed).replaceAll("\\+", "");
        return digits.length() >= MIN_DIGITS && digits.length() <= MAX_DIGITS;
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
