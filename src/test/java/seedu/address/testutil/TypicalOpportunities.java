package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_ROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CYCLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CYCLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.opportunity.Opportunity;

/**
 * A utility class containing a list of {@code Opportunity} objects to be used in tests.
 */
public class TypicalOpportunities {

    public static final Opportunity ALICE = new OpportunityBuilder()
            .withName("Alice Tan")
            .withEmail("alice@stripe.com")
            .withContactRole("recruiter")
            .withCompany("Stripe")
            .withRole("SWE Intern")
            .withStatus("APPLIED")
            .withCycle("SUMMER 2025")
            .build();
    public static final Opportunity BENSON = new OpportunityBuilder()
            .withName("Benson Yeo")
            .withEmail("benson@tiktok.com")
            .withContactRole("hiring manager")
            .withCompany("Tiktok")
            .withRole("Backend Engineer")
            .withStatus("INTERVIEW")
            .withCycle("SUMMER 2025")
            .withPhone("91234567")
            .build();
    public static final Opportunity CARL = new OpportunityBuilder()
            .withName("Carl Ng")
            .withEmail("carl@apple.com")
            .withContactRole("recruiter")
            .withCompany("Apple")
            .withRole("iOS Engineer")
            .withStatus("OA")
            .withCycle("SUMMER 2025")
            .build();
    public static final Opportunity DANIEL = new OpportunityBuilder()
            .withName("Daniel Goh")
            .withEmail("daniel@google.com")
            .withContactRole("interviewer")
            .withCompany("Google")
            .withRole("Cloud Engineer")
            .withStatus("SAVED")
            .withCycle("SUMMER 2025")
            .build();
    public static final Opportunity ELLE = new OpportunityBuilder()
            .withName("Elle Wong")
            .withEmail("elle@netflix.com")
            .withContactRole("recruiter")
            .withCompany("Netflix")
            .withRole("Data Scientist")
            .withStatus("OFFER")
            .withCycle("SUMMER 2025")
            .build();
    public static final Opportunity FIONA = new OpportunityBuilder()
            .withName("Fiona Lim")
            .withEmail("fiona@amazon.com")
            .withContactRole("referrer")
            .withCompany("Amazon")
            .withRole("Network Engineer")
            .withStatus("REJECTED")
            .withCycle("SUMMER 2025")
            .build();
    public static final Opportunity GEORGE = new OpportunityBuilder()
            .withName("George Koh")
            .withEmail("george@meta.com")
            .withContactRole("recruiter")
            .withCompany("Meta")
            .withRole("Operations Intern")
            .withStatus("WITHDRAWN")
            .withCycle("SUMMER 2025")
            .build();

    // Manually added
    public static final Opportunity HOON = new OpportunityBuilder()
            .withName("Hoon Meier")
            .withEmail("hoon@grab.com")
            .withContactRole("recruiter")
            .withCompany("Grab")
            .withRole("Frontend Intern")
            .withStatus("APPLIED")
            .withCycle("WINTER 2025")
            .build();
    public static final Opportunity IDA = new OpportunityBuilder()
            .withName("Ida Mueller")
            .withEmail("ida@shopee.com")
            .withContactRole("hiring manager")
            .withCompany("Shopee")
            .withRole("Data Analyst")
            .withStatus("SAVED")
            .withCycle("S1 2025")
            .build();

    // Manually added - Opportunity's details found in {@code CommandTestUtil}
    public static final Opportunity AMY = new OpportunityBuilder()
            .withName(VALID_NAME_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withContactRole(VALID_CONTACT_ROLE_AMY)
            .withCompany(VALID_COMPANY_AMY)
            .withRole(VALID_ROLE_AMY)
            .withStatus(VALID_STATUS_AMY)
            .withCycle(VALID_CYCLE_AMY)
            .withPhone(VALID_PHONE_AMY)
            .build();
    public static final Opportunity BOB = new OpportunityBuilder()
            .withName(VALID_NAME_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withContactRole(VALID_CONTACT_ROLE_BOB)
            .withCompany(VALID_COMPANY_BOB)
            .withRole(VALID_ROLE_BOB)
            .withStatus(VALID_STATUS_BOB)
            .withCycle(VALID_CYCLE_BOB)
            .build();

    public static final String KEYWORD_MATCHING_STRIPE = "Stripe"; // A keyword that matches the company of ALICE

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
