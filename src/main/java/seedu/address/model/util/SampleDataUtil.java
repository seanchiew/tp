package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.opportunity.Company;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.model.opportunity.Role;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Opportunity[] getSampleOpportunities() {
        return new Opportunity[] {
            new Opportunity(new Company("Stripe"), new Role("SWE Intern")),
            new Opportunity(new Company("Tiktok"), new Role("Backend Engineer")),
            new Opportunity(new Company("Accenture"), new Role("Tech Consultant")),
            new Opportunity(new Company("NUS"), new Role("Research Intern")),
            new Opportunity(new Company("SP Group"), new Role("Analyst Intern"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Opportunity sampleOpportunity : getSampleOpportunities()) {
            sampleAb.addOpportunity(sampleOpportunity);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
