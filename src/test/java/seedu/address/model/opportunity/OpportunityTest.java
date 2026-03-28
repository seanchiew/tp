package seedu.address.model.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CYCLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.testutil.TypicalOpportunities.ALICE;
import static seedu.address.testutil.TypicalOpportunities.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.OpportunityBuilder;

public class OpportunityTest {

    @Test
    public void isSameOpportunity() {
        // same object -> returns true
        assertTrue(ALICE.isSameOpportunity(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameOpportunity(null));

        // same email, company, role, cycle -> returns true (identity check)
        Opportunity editedAlice = new OpportunityBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSameOpportunity(editedAlice));

        // different email -> returns false
        editedAlice = new OpportunityBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameOpportunity(editedAlice));

        // different company -> returns false
        editedAlice = new OpportunityBuilder(ALICE).withCompany(VALID_COMPANY_BOB).build();
        assertFalse(ALICE.isSameOpportunity(editedAlice));

        // different role -> returns false
        editedAlice = new OpportunityBuilder(ALICE).withRole(VALID_ROLE_BOB).build();
        assertFalse(ALICE.isSameOpportunity(editedAlice));

        // different cycle -> returns false (Proves identity relies on Cycle!)
        editedAlice = new OpportunityBuilder(ALICE).withCycle(VALID_CYCLE_BOB).build();
        assertFalse(ALICE.isSameOpportunity(editedAlice));

        // email differs only in casing -> returns true (case-insensitive comparison)
        editedAlice = new OpportunityBuilder(ALICE).withEmail("ALICE@STRIPE.COM").build();
        assertTrue(ALICE.isSameOpportunity(editedAlice));

        // company differs only in casing -> returns true (case-insensitive comparison)
        editedAlice = new OpportunityBuilder(ALICE).withCompany("stripe").build();
        assertTrue(ALICE.isSameOpportunity(editedAlice));

        // role differs only in casing -> returns true (case-insensitive comparison)
        editedAlice = new OpportunityBuilder(ALICE).withRole("swe intern").build();
        assertTrue(ALICE.isSameOpportunity(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Opportunity aliceCopy = new OpportunityBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different opportunity -> returns false
        assertFalse(ALICE.equals(BOB));

        // different company -> returns false
        Opportunity editedAlice = new OpportunityBuilder(ALICE).withCompany(VALID_COMPANY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different role -> returns false
        editedAlice = new OpportunityBuilder(ALICE).withRole(VALID_ROLE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different name -> returns false
        editedAlice = new OpportunityBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new OpportunityBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different archived status -> returns false
        editedAlice = new OpportunityBuilder(ALICE).withArchived(!ALICE.isArchived()).build();
        assertFalse(ALICE.equals(editedAlice));

        // different archived status -> returns false
        editedAlice = new OpportunityBuilder(ALICE).withArchived(!ALICE.isArchived()).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Opportunity.class.getCanonicalName()
                + "{name=" + ALICE.getName()
                + ", email=" + ALICE.getEmail()
                + ", contactRole=" + ALICE.getContactRole()
                + ", company=" + ALICE.getCompany()
                + ", role=" + ALICE.getRole()
                + ", status=" + ALICE.getStatus()
                + ", cycle=" + ALICE.getCycle()
                + ", phone=" + ALICE.getPhone().orElse(null)
                + "}";
        assertEquals(expected, ALICE.toString());
    }
}
