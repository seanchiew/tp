package seedu.address.model.opportunity;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Opportunity}'s name matches any of the given name keywords and, when provided,
 * its company matches all of the given company keywords.
 */
public class OpportunityContainsSubstringPredicate implements Predicate<Opportunity> {
    private final List<String> nameKeywords;
    private final List<String> companyKeywords;

    public OpportunityContainsSubstringPredicate(List<String> nameKeywords) {
        this(nameKeywords, List.of());
    }

    /**
     * Creates a predicate that matches opportunities whose names contain any of the given
     * {@code nameKeywords} and whose companies contain all of the given {@code companyKeywords}.
     *
     * @param nameKeywords name substrings to match against; may be empty for company-only searches
     * @param companyKeywords company substrings to match against; may be empty for name-only searches
     */
    public OpportunityContainsSubstringPredicate(List<String> nameKeywords, List<String> companyKeywords) {
        requireNonNull(nameKeywords);
        requireNonNull(companyKeywords);
        this.nameKeywords = List.copyOf(nameKeywords);
        this.companyKeywords = List.copyOf(companyKeywords);
    }

    @Override
    public boolean test(Opportunity opportunity) {
        if (opportunity.isArchived()) {
            return false;
        }

        if (nameKeywords.isEmpty() && companyKeywords.isEmpty()) {
            return false;
        }

        boolean matchesName = nameKeywords.isEmpty() || nameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsSubstringIgnoreCase(
                        opportunity.getName().fullName, keyword));
        boolean matchesCompany = companyKeywords.isEmpty() || companyKeywords.stream()
                .allMatch(keyword -> StringUtil.containsSubstringIgnoreCase(
                        opportunity.getCompany().companyName, keyword));

        return matchesName && matchesCompany;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OpportunityContainsSubstringPredicate)) {
            return false;
        }

        OpportunityContainsSubstringPredicate otherPredicate = (OpportunityContainsSubstringPredicate) other;
        return nameKeywords.equals(otherPredicate.nameKeywords)
                && companyKeywords.equals(otherPredicate.companyKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nameKeywords", nameKeywords)
                .add("companyKeywords", companyKeywords)
                .toString();
    }
}
