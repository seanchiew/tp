package seedu.address.model.opportunity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Opportunity's application cycle in the tracker. Guarantees:
 * immutable; is valid as declared in {@link #isValidCycle(String)}
 */
public class Cycle {

    public static final String MESSAGE_CONSTRAINTS =
        "A cycle must be one of (SUMMER, WINTER, S1, S2) followed by a space\n"
        + "and a 4-digit year (e.g. SUMMER 2025).";

    public static final String PARSER_MESSAGE_CONSTRAINTS =
        "A cycle must be one of (SUMMER, WINTER, S1, S2) followed by a space\n"
        + "and a 4-digit year (e.g. SUMMER 2025). CLI aliases like SEM 1, semester 2,\n"
        + "and SemESTer1 are also accepted and normalized to S1/S2.";

    /*
     * The cycle must strictly match the (SUMMER|WINTER|S1|S2) keyword,
     * followed by a single space, and any 4-digit year.
     * Note: We intentionally do not restrict the year to a "realistic" range
     * (e.g., 2000-2099) to avoid overzealous input validation, allowing users
     * the flexibility to track historical or far-future opportunities.
     */
    public static final String VALIDATION_REGEX = "^(SUMMER|WINTER|S1|S2)\\s\\d{4}$";

    private final String value;

    /**
     * Constructs a {@code Cycle}.
     *
     * @param cycle A valid cycle string.
     */
    public Cycle(String cycle) {
        requireNonNull(cycle);
        checkArgument(isValidCycle(cycle), MESSAGE_CONSTRAINTS);
        this.value = cycle;
    }

    public String getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid cycle
     */
    public static boolean isValidCycle(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX);
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

        //instanceof handles nulls
        if (!(other instanceof Cycle)) {
            return false;
        }

        Cycle otherCycle = (Cycle) other;
        return value.equals(otherCycle.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
