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
        List<String> secondPredicateCompanyKeywordList = Collections.singletonList("second");

        OpportunityContainsSubstringPredicate firstPredicate =
                new OpportunityContainsSubstringPredicate(firstPredicateKeywordList);
        OpportunityContainsSubstringPredicate secondPredicate =
                new OpportunityContainsSubstringPredicate(firstPredicateKeywordList, secondPredicateCompanyKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        OpportunityContainsSubstringPredicate firstPredicateCopy =
                new OpportunityContainsSubstringPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different opportunity -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        OpportunityContainsSubstringPredicate predicate = new OpportunityContainsSubstringPredicate(
                Collections.singletonList("Ali"));
        assertTrue(predicate.test(new OpportunityBuilder().withName("Alice Tan").build()));

        predicate = new OpportunityContainsSubstringPredicate(Arrays.asList("Ali", "Bob"));
        assertTrue(predicate.test(new OpportunityBuilder().withName("Alice Tan").build()));
    }

    @Test
    public void test_companyContainsKeywords_returnsTrue() {
        OpportunityContainsSubstringPredicate predicate =
                new OpportunityContainsSubstringPredicate(List.of(), Collections.singletonList("Str"));
        assertTrue(predicate.test(new OpportunityBuilder().withCompany("Stripe").build()));

        predicate = new OpportunityContainsSubstringPredicate(List.of(), Arrays.asList("Str", "ipe"));
        assertTrue(predicate.test(new OpportunityBuilder().withCompany("Stripe").build()));
    }

    @Test
    public void test_nameAndCompanyContainKeywords_returnsTrue() {
        OpportunityContainsSubstringPredicate predicate = new OpportunityContainsSubstringPredicate(
                Collections.singletonList("Ali"), Collections.singletonList("Str"));
        assertTrue(predicate.test(new OpportunityBuilder().withName("Alice Tan").withCompany("Stripe").build()));
    }

    @Test
    public void test_keywordsDoNotMatch_returnsFalse() {
        OpportunityContainsSubstringPredicate predicate = new OpportunityContainsSubstringPredicate(
                Collections.emptyList());
        assertFalse(predicate.test(new OpportunityBuilder().withName("Alice Tan").withCompany("Stripe").build()));

        predicate = new OpportunityContainsSubstringPredicate(Collections.singletonList("Bob"));
        assertFalse(predicate.test(new OpportunityBuilder().withName("Alice Tan").build()));

        predicate = new OpportunityContainsSubstringPredicate(List.of(), Collections.singletonList("Tik"));
        assertFalse(predicate.test(new OpportunityBuilder().withCompany("Stripe").build()));

        predicate = new OpportunityContainsSubstringPredicate(
                Collections.singletonList("Ali"), Collections.singletonList("Tik"));
        assertFalse(predicate.test(new OpportunityBuilder().withName("Alice Tan").withCompany("Stripe").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> nameKeywords = List.of("keyword1", "keyword2");
        List<String> companyKeywords = List.of("keyword3");
        OpportunityContainsSubstringPredicate predicate =
                new OpportunityContainsSubstringPredicate(nameKeywords, companyKeywords);

        String expected = OpportunityContainsSubstringPredicate.class.getCanonicalName()
                + "{nameKeywords=" + nameKeywords + ", companyKeywords=" + companyKeywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
