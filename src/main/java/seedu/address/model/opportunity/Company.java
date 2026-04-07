package seedu.address.model.opportunity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Company in the tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidCompany(String)}
 */
public class Company {

    public static final String MESSAGE_CONSTRAINTS =
        "Company names must be 1-60 characters, must not be blank, and can only contain "
            + "letters, numbers, spaces, and the special characters: & . - , ( ) '";

    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 60;

    /*
     * The first character of the company name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} &.,()'\\-]*";

    private final String companyName;

    /**
     * Constructs a {@code Company}.
     *
     * @param company A valid company name.
     */
    public Company(String company) {
        requireNonNull(company);
        String trimmedCompany = company.trim();
        checkArgument(isValidCompany(trimmedCompany), MESSAGE_CONSTRAINTS);
        this.companyName = trimmedCompany;
    }

    public String getCompanyName() {
        return companyName;
    }

    /**
     * Returns true if a given string is a valid company name.
     * @param test The string to be tested.
     * @return true if the string is a valid company name, false otherwise.
     */
    public static boolean isValidCompany(String test) {
        requireNonNull(test);
        String trimmedTest = test.trim();
        return trimmedTest.length() >= MIN_LENGTH
            && trimmedTest.length() <= MAX_LENGTH
            && trimmedTest.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return companyName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Company)) {
            return false;
        }

        Company otherCompany = (Company) other;
        return companyName.equals(otherCompany.companyName);
    }

    @Override
    public int hashCode() {
        return companyName.hashCode();
    }

}
