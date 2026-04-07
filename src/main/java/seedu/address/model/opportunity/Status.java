package seedu.address.model.opportunity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Set;

/**
 * Represents an opportunity's status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static final String MESSAGE_CONSTRAINTS =
        "Status must be one of: SAVED, APPLIED, OA, INTERVIEW, OFFER, REJECTED, WITHDRAWN.";

    /**
     * The set of allowed statuses.
     */
    public static final Set<String> ALLOWED_STATUSES = Set.of(
        "SAVED",
        "APPLIED",
        "OA",
        "INTERVIEW",
        "OFFER",
        "REJECTED",
        "WITHDRAWN"
    );

    private final String statusName;

    /**
     * Constructs a {@code Status}.
     *
     * @param status A valid status.
     */
    public Status(String status) {
        requireNonNull(status);
        String upperCasedStatus = status.trim().toUpperCase();
        checkArgument(isValidStatus(upperCasedStatus), MESSAGE_CONSTRAINTS);
        this.statusName = upperCasedStatus;
    }

    public String getStatusName() {
        return statusName;
    }

    /**
     * Returns true if a given string is a valid status.
     * @param test The string to be tested.
     * @return true if the string is a valid status, false otherwise.
     */
    public static boolean isValidStatus(String test) {
        requireNonNull(test);
        String upperCasedTest = test.trim().toUpperCase();
        return ALLOWED_STATUSES.contains(upperCasedTest);
    }

    @Override
    public String toString() {
        return statusName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Status)) {
            return false;
        }

        Status otherStatus = (Status) other;
        return statusName.equals(otherStatus.statusName);
    }

    @Override
    public int hashCode() {
        return statusName.hashCode();
    }

}
