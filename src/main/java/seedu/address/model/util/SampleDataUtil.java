package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.opportunity.Company;
import seedu.address.model.opportunity.ContactRole;
import seedu.address.model.opportunity.Cycle;
import seedu.address.model.opportunity.Email;
import seedu.address.model.opportunity.Name;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.model.opportunity.Phone;
import seedu.address.model.opportunity.Role;
import seedu.address.model.opportunity.Status;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Opportunity[] getSampleOpportunities() {
        return new Opportunity[] {
            new Opportunity(
                new Name("Jane Lim"),
                new Email("jane.lim@stripe.com"),
                new ContactRole("recruiter"),
                new Company("Stripe"),
                new Role("SWE Intern"),
                new Status("APPLIED"),
                new Cycle("SUMMER 2026"),
                false,
                new Phone("91234567")),
            new Opportunity(
                new Name("David Tan"),
                new Email("david.tan@tiktok.com"),
                new ContactRole("hiring manager"),
                new Company("Tiktok"),
                new Role("Backend Engineer"),
                new Status("INTERVIEW"),
                new Cycle("S1 2026"),
                false,
                null),
            new Opportunity(
                new Name("Sarah Chen"),
                new Email("sarah.chen@accenture.com"),
                new ContactRole("recruiter"),
                new Company("Accenture"),
                new Role("Tech Consultant"),
                new Status("OA"),
                new Cycle("S2 2026"),
                false,
                new Phone("87654321")),
            new Opportunity(
                new Name("John Lee"),
                new Email("john.lee@nus.edu.sg"),
                new ContactRole("referrer"),
                new Company("NUS"),
                new Role("Research Intern"),
                new Status("SAVED"),
                new Cycle("WINTER 2026"),
                false,
                null),
            new Opportunity(
                new Name("Amy Wong"),
                new Email("amy.wong@spgroup.com.sg"),
                new ContactRole("interviewer"),
                new Company("SP Group"),
                new Role("Analyst Intern"),
                new Status("OFFER"),
                new Cycle("SUMMER 2027"),
                false,
                new Phone("98887777"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Opportunity sampleOpportunity : getSampleOpportunities()) {
            sampleAb.addOpportunity(sampleOpportunity);
        }
        return sampleAb;
    }
}
