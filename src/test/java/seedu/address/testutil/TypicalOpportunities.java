package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.opportunity.Opportunity;

/**
 * A utility class containing a list of {@code Opportunity} objects to be used in tests.
 */
public class TypicalOpportunities {

    public static final Opportunity ALICE = new OpportunityBuilder().withCompany("Stripe")
                .withRole("SWE Intern").build();
    public static final Opportunity BENSON = new OpportunityBuilder().withCompany("Tiktok")
                .withRole("Backend Engineer").build();
    public static final Opportunity CARL = new OpportunityBuilder().withCompany("Apple")
                .withRole("iOS Engineer").build();
    public static final Opportunity DANIEL = new OpportunityBuilder().withCompany("Google")
                .withRole("Cloud Engineer").build();
    public static final Opportunity ELLE = new OpportunityBuilder().withCompany("Netflix")
                .withRole("Data Scientist").build();
    public static final Opportunity FIONA = new OpportunityBuilder().withCompany("Amazon")
                .withRole("Network Engineer").build();
    public static final Opportunity GEORGE = new OpportunityBuilder().withCompany("Meta")
                .withRole("Operations Intern").build();

    // Manually added
    public static final Opportunity HOON = new OpportunityBuilder().withCompany("Grab")
                .withRole("Frontend Intern").build();
    public static final Opportunity IDA = new OpportunityBuilder().withCompany("Shopee")
                .withRole("Data Analyst").build();

    // Manually added - Opportunity's details found in {@code CommandTestUtil}
    public static final Opportunity AMY = new OpportunityBuilder().withCompany(VALID_COMPANY_AMY)
                .withRole(VALID_ROLE_AMY).build();
    public static final Opportunity BOB = new OpportunityBuilder().withCompany(VALID_COMPANY_BOB)
                .withRole(VALID_ROLE_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Stripe"; // Update matching keyword

    private TypicalOpportunities() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical opportunities.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Opportunity opportunity : getTypicalOpportunities()) {
            ab.addOpportunity(opportunity);
        }
        return ab;
    }

    public static List<Opportunity> getTypicalOpportunities() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
