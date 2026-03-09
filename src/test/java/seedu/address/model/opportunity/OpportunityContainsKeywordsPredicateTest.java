package seedu.address.model.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.OpportunityBuilder;

public class OpportunityContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        OpportunityContainsKeywordsPredicate firstPredicate =
                new OpportunityContainsKeywordsPredicate(firstPredicateKeywordList);
        OpportunityContainsKeywordsPredicate secondPredicate =
                new OpportunityContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        OpportunityContainsKeywordsPredicate firstPredicateCopy =
                new OpportunityContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different opportunity -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_companyContainsKeywords_returnsTrue() {
        // One keyword
        OpportunityContainsKeywordsPredicate predicate = new OpportunityContainsKeywordsPredicate(
                                        Collections.singletonList("Stripe"));
        assertTrue(predicate.test(new OpportunityBuilder().withCompany("Stripe").build()));

        // Multiple keywords
        predicate = new OpportunityContainsKeywordsPredicate(Arrays.asList("Stripe", "Google"));
        assertTrue(predicate.test(new OpportunityBuilder().withCompany("Stripe Google").build()));

        // Only one matching keyword
        predicate = new OpportunityContainsKeywordsPredicate(Arrays.asList("Stripe", "Tiktok"));
        assertTrue(predicate.test(new OpportunityBuilder().withCompany("Stripe Google").build()));

        // Mixed-case keywords
        predicate = new OpportunityContainsKeywordsPredicate(Arrays.asList("sTrIpE", "gOoGlE"));
        assertTrue(predicate.test(new OpportunityBuilder().withCompany("Stripe Google").build()));
    }

    @Test
    public void test_companyDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        OpportunityContainsKeywordsPredicate predicate = new OpportunityContainsKeywordsPredicate(
                                        Collections.emptyList());
        assertFalse(predicate.test(new OpportunityBuilder().withCompany("Stripe").build()));

        // Non-matching keyword
        predicate = new OpportunityContainsKeywordsPredicate(Arrays.asList("Tiktok"));
        assertFalse(predicate.test(new OpportunityBuilder().withCompany("Stripe Google").build()));

        // Keywords match role, but does not match company
        predicate = new OpportunityContainsKeywordsPredicate(Arrays.asList("SWE"));
        assertFalse(predicate.test(new OpportunityBuilder().withCompany("Stripe").withRole("SWE Intern").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        OpportunityContainsKeywordsPredicate predicate = new OpportunityContainsKeywordsPredicate(keywords);

        String expected = OpportunityContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
