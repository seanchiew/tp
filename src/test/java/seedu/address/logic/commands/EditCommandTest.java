package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CYCLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showOpportunityAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OPPORTUNITY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OPPORTUNITY;
import static seedu.address.testutil.TypicalOpportunities.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditOpportunityDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.model.opportunity.OpportunityContainsSubstringPredicate;
import seedu.address.testutil.EditOpportunityDescriptorBuilder;
import seedu.address.testutil.OpportunityBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        // Explicitly set the cycle to SUMMER 2025 to match ALICE's preserved cycle
        Opportunity editedOpportunity = new OpportunityBuilder().withCycle("SUMMER 2025").build();
        EditOpportunityDescriptor descriptor = new EditOpportunityDescriptorBuilder(editedOpportunity).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_OPPORTUNITY, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_OPPORTUNITY_SUCCESS,
                Messages.format(editedOpportunity));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setOpportunity(model.getFilteredOpportunityList().get(0), editedOpportunity);

        expectedModel.commitAddressBook();
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastOpportunity = Index.fromOneBased(model.getFilteredOpportunityList().size());
        Opportunity lastOpportunity = model.getFilteredOpportunityList().get(indexLastOpportunity.getZeroBased());

        OpportunityBuilder opportunityInList = new OpportunityBuilder(lastOpportunity);
        Opportunity editedOpportunity = opportunityInList.withCompany(VALID_COMPANY_BOB).withRole(VALID_ROLE_BOB)
                                        .build();

        EditOpportunityDescriptor descriptor = new EditOpportunityDescriptorBuilder().withCompany(VALID_COMPANY_BOB)
                                        .withRole(VALID_ROLE_BOB).build();
        EditCommand editCommand = new EditCommand(indexLastOpportunity, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_OPPORTUNITY_SUCCESS,
                                        Messages.format(editedOpportunity));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setOpportunity(lastOpportunity, editedOpportunity);

        expectedModel.commitAddressBook();
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_cycleOnlySpecifiedUnfilteredList_success() {
        Opportunity opportunityToEdit = model.getFilteredOpportunityList().get(INDEX_FIRST_OPPORTUNITY.getZeroBased());
        Opportunity editedOpportunity = new OpportunityBuilder(opportunityToEdit).withCycle(VALID_CYCLE_BOB).build();

        EditOpportunityDescriptor descriptor = new EditOpportunityDescriptorBuilder()
                .withCycle(VALID_CYCLE_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_OPPORTUNITY, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_OPPORTUNITY_SUCCESS,
                Messages.format(editedOpportunity));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setOpportunity(opportunityToEdit, editedOpportunity);

        expectedModel.commitAddressBook();
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_OPPORTUNITY, new EditOpportunityDescriptor());
        Opportunity editedOpportunity = model.getFilteredOpportunityList().get(INDEX_FIRST_OPPORTUNITY.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_OPPORTUNITY_SUCCESS,
                Messages.format(editedOpportunity));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.commitAddressBook();
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showOpportunityAtIndex(model, INDEX_FIRST_OPPORTUNITY);

        Opportunity opportunityInFilteredList = model.getFilteredOpportunityList()
                                        .get(INDEX_FIRST_OPPORTUNITY.getZeroBased());
        Opportunity editedOpportunity = new OpportunityBuilder(opportunityInFilteredList).withCompany(VALID_COMPANY_BOB)
                                        .build();

        EditCommand editCommand = new EditCommand(INDEX_FIRST_OPPORTUNITY,
                                        new EditOpportunityDescriptorBuilder().withCompany(VALID_COMPANY_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_OPPORTUNITY_SUCCESS,
                                        Messages.format(editedOpportunity));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setOpportunity(model.getFilteredOpportunityList().get(0), editedOpportunity);
        // Filter is preserved after edit — apply the same name-based predicate to the expected model
        showOpportunityAtIndex(expectedModel, INDEX_FIRST_OPPORTUNITY);

        expectedModel.commitAddressBook();
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_itemDisappearsWhenNoLongerMatchingFilter() {
        // Filter to show only the first opportunity (by the first word of its name)
        showOpportunityAtIndex(model, INDEX_FIRST_OPPORTUNITY);
        assertEquals(1, model.getFilteredOpportunityList().size());

        int firstIndex = INDEX_FIRST_OPPORTUNITY.getZeroBased();
        Opportunity originalOpportunity = model.getFilteredOpportunityList().get(firstIndex);
        // Capture the keyword that showOpportunityAtIndex used, so the expected predicate stays in sync
        String filterKeyword = originalOpportunity.getName().getFullName().split("\\s+")[0];

        // Edit the name so it no longer matches the current filter keyword
        Opportunity renamedOpportunity = new OpportunityBuilder(originalOpportunity).withName("Zebra Tan").build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_OPPORTUNITY,
                new EditOpportunityDescriptorBuilder().withName("Zebra Tan").build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_OPPORTUNITY_SUCCESS,
                Messages.format(renamedOpportunity));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setOpportunity(originalOpportunity, renamedOpportunity);
        // Filter keyword no longer matches "Zebra Tan" — apply same predicate so expected list is also empty
        expectedModel.updateFilteredOpportunityList(
                new OpportunityContainsSubstringPredicate(Arrays.asList(filterKeyword)));
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        assertEquals(0, model.getFilteredOpportunityList().size());
    }

    @Test
    public void execute_archiveViewPreservesArchiveFilter() {
        // Archive ALICE so there is at least one archived entry to edit
        Opportunity alice = model.getFilteredOpportunityList().get(INDEX_FIRST_OPPORTUNITY.getZeroBased());
        Opportunity archivedAlice = new OpportunityBuilder(alice).withArchived(true).build();
        model.setOpportunity(alice, archivedAlice);

        // Switch model to archive view
        model.updateFilteredOpportunityList(PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES);
        model.setArchiveView(true);
        assertEquals(1, model.getFilteredOpportunityList().size());

        // Edit a non-identity field (status) — opportunity stays archived, still matches archive filter
        Opportunity editedArchivedAlice = new OpportunityBuilder(archivedAlice).withStatus("OFFER").build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_OPPORTUNITY,
                new EditOpportunityDescriptorBuilder().withStatus("OFFER").build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_OPPORTUNITY_SUCCESS,
                Messages.format(editedArchivedAlice));

        // expectedModel starts from the same initial state as model. Both the archive setup and the
        // edit are applied without an intermediate commit so that a single commitAddressBook() here
        // mirrors the single commit inside execute(), keeping undo histories identical.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setOpportunity(alice, archivedAlice);
        expectedModel.setOpportunity(archivedAlice, editedArchivedAlice);
        expectedModel.updateFilteredOpportunityList(PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES);
        expectedModel.setArchiveView(true);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        // Archive filter is preserved — the edited archived opportunity is still visible
        assertEquals(1, model.getFilteredOpportunityList().size());
        assertEquals(editedArchivedAlice, model.getFilteredOpportunityList().get(0));
    }

    @Test
    public void execute_searchFilteredList_unchangedWhenItemStillMatches() {
        // Simulate a FindCommand active search for company "Stripe": scoped to unarchived + keyword filter.
        // Only ALICE (Stripe / SWE Intern) should match.
        OpportunityContainsSubstringPredicate stripeSearch =
                new OpportunityContainsSubstringPredicate(Arrays.asList(), Arrays.asList("Stripe"));
        model.updateFilteredOpportunityList(PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES.and(stripeSearch));
        assertEquals(1, model.getFilteredOpportunityList().size());

        Opportunity alice = model.getFilteredOpportunityList().get(INDEX_FIRST_OPPORTUNITY.getZeroBased());

        // Edit status — company unchanged, so ALICE still matches the "Stripe" filter after edit
        Opportunity editedAlice = new OpportunityBuilder(alice).withStatus("OFFER").build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_OPPORTUNITY,
                new EditOpportunityDescriptorBuilder().withStatus("OFFER").build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_OPPORTUNITY_SUCCESS,
                Messages.format(editedAlice));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setOpportunity(alice, editedAlice);
        expectedModel.updateFilteredOpportunityList(PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES.and(stripeSearch));
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        // Search filter is preserved — edited ALICE still appears (company still "Stripe")
        assertEquals(1, model.getFilteredOpportunityList().size());
        assertEquals(editedAlice, model.getFilteredOpportunityList().get(0));
    }

    @Test
    public void execute_duplicateOpportunityUnfilteredList_failure() {
        Opportunity firstOpportunity = model.getFilteredOpportunityList().get(INDEX_FIRST_OPPORTUNITY.getZeroBased());
        EditOpportunityDescriptor descriptor = new EditOpportunityDescriptorBuilder(firstOpportunity).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_OPPORTUNITY, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_IN_ACTIVE_LIST);
    }

    @Test
    public void execute_duplicateOpportunityFilteredList_failure() {
        showOpportunityAtIndex(model, INDEX_FIRST_OPPORTUNITY);

        // edit opportunity in filtered list into a duplicate in address book
        Opportunity opportunityInList =
                model.getAddressBook().getOpportunityList().get(INDEX_SECOND_OPPORTUNITY.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_OPPORTUNITY,
                new EditOpportunityDescriptorBuilder(opportunityInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_IN_ACTIVE_LIST);
    }

    @Test
    public void execute_duplicateArchivedOpportunityUnfilteredList_failure() {
        // Archive the first opportunity, then try to edit the second to match it
        Opportunity firstOpportunity = model.getFilteredOpportunityList().get(INDEX_FIRST_OPPORTUNITY.getZeroBased());
        Opportunity archivedVersion = new OpportunityBuilder(firstOpportunity).withArchived(true).build();
        model.setOpportunity(firstOpportunity, archivedVersion);
        model.updateFilteredOpportunityList(seedu.address.model.Model.PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES);

        // Edit the (now first visible) opportunity to match the archived one
        Opportunity secondOpportunity = model.getFilteredOpportunityList().get(INDEX_FIRST_OPPORTUNITY.getZeroBased());
        EditOpportunityDescriptor descriptor = new EditOpportunityDescriptorBuilder(archivedVersion).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_OPPORTUNITY, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_IN_ARCHIVE);
    }

    @Test
    public void execute_invalidOpportunityIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOpportunityList().size() + 1);

        EditOpportunityDescriptor descriptor = new EditOpportunityDescriptorBuilder().withCompany(VALID_COMPANY_BOB)
                                        .build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidOpportunityIndexFilteredList_failure() {
        showOpportunityAtIndex(model, INDEX_FIRST_OPPORTUNITY);
        Index outOfBoundIndex = INDEX_SECOND_OPPORTUNITY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getOpportunityList().size());

        // Changed .withName(VALID_NAME_BOB) to .withCompany(VALID_COMPANY_BOB)
        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                                        new EditOpportunityDescriptorBuilder().withCompany(VALID_COMPANY_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_clearPhoneField_success() {
        // BENSON (index 2) has a phone set; clearing it should produce a BENSON without phone
        Index indexBenson = Index.fromOneBased(2);
        Opportunity bensonInList = model.getFilteredOpportunityList().get(indexBenson.getZeroBased());
        assertTrue(bensonInList.getPhone().isPresent(), "Precondition: BENSON must have a phone set");

        EditOpportunityDescriptor descriptor = new EditOpportunityDescriptorBuilder().withClearPhone().build();
        EditCommand editCommand = new EditCommand(indexBenson, descriptor);

        Opportunity expectedOpportunity = new OpportunityBuilder(bensonInList).withoutPhone().build();
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_OPPORTUNITY_SUCCESS,
                Messages.format(expectedOpportunity));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setOpportunity(bensonInList, expectedOpportunity);

        expectedModel.commitAddressBook();
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        assertFalse(model.getFilteredOpportunityList().get(indexBenson.getZeroBased()).getPhone().isPresent());
    }

    @Test
    public void execute_omitPhonePrefixPreservesExistingPhone_success() {
        // BENSON (index 2) has a phone; editing another field without p/ must keep the phone intact
        Index indexBenson = Index.fromOneBased(2);
        Opportunity bensonInList = model.getFilteredOpportunityList().get(indexBenson.getZeroBased());
        assertTrue(bensonInList.getPhone().isPresent(), "Precondition: BENSON must have a phone set");

        EditOpportunityDescriptor descriptor = new EditOpportunityDescriptorBuilder()
                .withCompany(VALID_COMPANY_BOB).build();
        EditCommand editCommand = new EditCommand(indexBenson, descriptor);

        Opportunity expectedOpportunity = new OpportunityBuilder(bensonInList)
                .withCompany(VALID_COMPANY_BOB).build();
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_OPPORTUNITY_SUCCESS,
                Messages.format(expectedOpportunity));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setOpportunity(bensonInList, expectedOpportunity);

        expectedModel.commitAddressBook();
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        assertTrue(model.getFilteredOpportunityList().get(indexBenson.getZeroBased()).getPhone().isPresent());
    }

    @Test
    public void execute_updatePhoneToNewValue_success() {
        // BENSON (index 2) has a phone; replacing it with a new value should work
        Index indexBenson = Index.fromOneBased(2);
        Opportunity bensonInList = model.getFilteredOpportunityList().get(indexBenson.getZeroBased());

        String newPhone = "81234567";
        EditOpportunityDescriptor descriptor = new EditOpportunityDescriptorBuilder().withPhone(newPhone).build();
        EditCommand editCommand = new EditCommand(indexBenson, descriptor);

        Opportunity expectedOpportunity = new OpportunityBuilder(bensonInList).withPhone(newPhone).build();
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_OPPORTUNITY_SUCCESS,
                Messages.format(expectedOpportunity));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setOpportunity(bensonInList, expectedOpportunity);

        expectedModel.commitAddressBook();
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        assertEquals(newPhone,
                model.getFilteredOpportunityList().get(indexBenson.getZeroBased()).getPhone().get().getValue());
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_OPPORTUNITY, DESC_AMY);

        // same values -> returns true
        EditOpportunityDescriptor copyDescriptor = new EditOpportunityDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_OPPORTUNITY, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_OPPORTUNITY, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_OPPORTUNITY, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditOpportunityDescriptor editOpportunityDescriptor = new EditOpportunityDescriptor();
        EditCommand editCommand = new EditCommand(index, editOpportunityDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editOpportunityDescriptor="
                + editOpportunityDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
