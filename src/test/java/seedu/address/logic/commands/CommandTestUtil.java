package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CYCLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.model.opportunity.OpportunityContainsSubstringPredicate;
import seedu.address.testutil.EditOpportunityDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_EMAIL_AMY = "amy@stripe.com";
    public static final String VALID_EMAIL_BOB = "bob@google.com";
    public static final String VALID_CONTACT_ROLE_AMY = "recruiter";
    public static final String VALID_CONTACT_ROLE_BOB = "hiring manager";
    public static final String VALID_COMPANY_AMY = "Stripe";
    public static final String VALID_COMPANY_BOB = "Google";
    public static final String VALID_ROLE_AMY = "SWE Intern";
    public static final String VALID_ROLE_BOB = "Backend Intern";
    public static final String VALID_STATUS_AMY = "APPLIED";
    public static final String VALID_STATUS_BOB = "INTERVIEW";
    public static final String VALID_CYCLE_AMY = "SUMMER 2025";
    public static final String VALID_CYCLE_BOB = "WINTER 2025";
    public static final String VALID_PHONE_AMY = "91234567";


    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String CONTACT_ROLE_DESC_AMY = " " + PREFIX_CONTACT_ROLE + VALID_CONTACT_ROLE_AMY;
    public static final String CONTACT_ROLE_DESC_BOB = " " + PREFIX_CONTACT_ROLE + VALID_CONTACT_ROLE_BOB;
    public static final String COMPANY_DESC_AMY = " " + PREFIX_COMPANY + VALID_COMPANY_AMY;
    public static final String COMPANY_DESC_BOB = " " + PREFIX_COMPANY + VALID_COMPANY_BOB;
    public static final String ROLE_DESC_AMY = " " + PREFIX_ROLE + VALID_ROLE_AMY;
    public static final String ROLE_DESC_BOB = " " + PREFIX_ROLE + VALID_ROLE_BOB;
    public static final String STATUS_DESC_AMY = " " + PREFIX_STATUS + VALID_STATUS_AMY;
    public static final String STATUS_DESC_BOB = " " + PREFIX_STATUS + VALID_STATUS_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_CLEAR = " " + PREFIX_PHONE; // empty value clears the phone field

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + " "; // Blank
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "notanemail"; // Not valid email
    public static final String INVALID_CONTACT_ROLE_DESC = " " + PREFIX_CONTACT_ROLE + " "; // Blank
    public static final String INVALID_COMPANY_DESC = " " + PREFIX_COMPANY + " "; // Blank
    public static final String INVALID_ROLE_DESC = " " + PREFIX_ROLE + " "; // Blank
    public static final String INVALID_STATUS_DESC = " " + PREFIX_STATUS + "UNKNOWN"; // Not a valid status
    public static final String CYCLE_DESC_AMY = " " + PREFIX_CYCLE + VALID_CYCLE_AMY;
    public static final String CYCLE_DESC_BOB = " " + PREFIX_CYCLE + VALID_CYCLE_BOB;
    public static final String INVALID_CYCLE_DESC = " " + PREFIX_CYCLE + "Autumn 2025"; // Invalid format

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditOpportunityDescriptor DESC_AMY;
    public static final EditCommand.EditOpportunityDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditOpportunityDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .withContactRole(VALID_CONTACT_ROLE_AMY)
                .withCompany(VALID_COMPANY_AMY)
                .withRole(VALID_ROLE_AMY)
                .withStatus(VALID_STATUS_AMY)
                .withCycle(VALID_CYCLE_AMY)
                .withPhone(VALID_PHONE_AMY)
                .build();
        DESC_BOB = new EditOpportunityDescriptorBuilder()
                .withName(VALID_NAME_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .withContactRole(VALID_CONTACT_ROLE_BOB)
                .withCompany(VALID_COMPANY_BOB)
                .withRole(VALID_ROLE_BOB)
                .withStatus(VALID_STATUS_BOB)
                .withCycle(VALID_CYCLE_BOB)
                .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered opportunity list and selected opportunity in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Opportunity> expectedFilteredList = new ArrayList<>(actualModel.getFilteredOpportunityList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredOpportunityList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the opportunity at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showOpportunityAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredOpportunityList().size());

        Opportunity opportunity = model.getFilteredOpportunityList().get(targetIndex.getZeroBased());
        final String[] splitName = opportunity.getName().fullName.split("\\s+");
        model.updateFilteredOpportunityList(new OpportunityContainsSubstringPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredOpportunityList().size());
    }

}
