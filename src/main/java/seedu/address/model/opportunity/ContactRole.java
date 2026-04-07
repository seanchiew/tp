package seedu.address.model.opportunity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the contact's role in the application process (e.g. recruiter, interviewer).
 * Guarantees: immutable; is valid as declared in {@link #isValidContactRole(String)}
 */
public class ContactRole {

    public static final String MESSAGE_CONSTRAINTS =
        "Contact roles must be 1-50 characters and can\n"
            + "only contain alphanumeric characters, spaces, and hyphens, and must not be blank.\n"
            + "Examples: recruiter, interviewer, referrer, hiring manager";

    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 50;

    /*
     * The first character must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} \\-]*";

    private final String contactRoleName;

    /**
     * Constructs a {@code ContactRole}.
     *
     * @param contactRole A valid contact role.
     */
    public ContactRole(String contactRole) {
        requireNonNull(contactRole);
        String trimmedContactRole = contactRole.trim();
        checkArgument(isValidContactRole(trimmedContactRole), MESSAGE_CONSTRAINTS);
        this.contactRoleName = trimmedContactRole;
    }

    public String getContactRoleName() {
        return contactRoleName;
    }

    /**
     * Returns true if a given string is a valid contact role.
     */
    public static boolean isValidContactRole(String test) {
        requireNonNull(test);
        String trimmedTest = test.trim();
        return trimmedTest.length() >= MIN_LENGTH
            && trimmedTest.length() <= MAX_LENGTH
            && trimmedTest.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return contactRoleName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContactRole)) {
            return false;
        }

        ContactRole otherContactRole = (ContactRole) other;
        return contactRoleName.equals(otherContactRole.contactRoleName);
    }

    @Override
    public int hashCode() {
        return contactRoleName.hashCode();
    }

}
