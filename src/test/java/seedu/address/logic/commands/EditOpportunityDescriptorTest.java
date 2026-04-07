package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CYCLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditOpportunityDescriptor;
import seedu.address.testutil.EditOpportunityDescriptorBuilder;

public class EditOpportunityDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditOpportunityDescriptor descriptorWithSameValues = new EditOpportunityDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different company -> returns false
        EditOpportunityDescriptor editedAmy = new EditOpportunityDescriptorBuilder(DESC_AMY)
                                        .withCompany(VALID_COMPANY_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different role -> returns false
        editedAmy = new EditOpportunityDescriptorBuilder(DESC_AMY).withRole(VALID_ROLE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different cycle -> returns false
        editedAmy = new EditOpportunityDescriptorBuilder(DESC_AMY).withCycle(VALID_CYCLE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditOpportunityDescriptor editOpportunityDescriptor = new EditOpportunityDescriptor();
        String expected = EditOpportunityDescriptor.class.getCanonicalName()
                + "{name=" + editOpportunityDescriptor.getName().orElse(null)
                + ", email=" + editOpportunityDescriptor.getEmail().orElse(null)
                + ", contactRole=" + editOpportunityDescriptor.getContactRole().orElse(null)
                + ", company=" + editOpportunityDescriptor.getCompany().orElse(null)
                + ", role=" + editOpportunityDescriptor.getRole().orElse(null)
                + ", status=" + editOpportunityDescriptor.getStatus().orElse(null)
                + ", cycle=" + editOpportunityDescriptor.getCycle().orElse(null)
                + ", phone=" + editOpportunityDescriptor.getPhone().orElse(null)
                + ", clearPhone=" + editOpportunityDescriptor.shouldClearPhone()
                + "}";
        assertEquals(expected, editOpportunityDescriptor.toString());
    }
}
